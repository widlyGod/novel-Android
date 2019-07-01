package com.novel.cn.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.app.Constant
import com.novel.cn.app.isNullOrEmpty
import com.novel.cn.ext.applySchedulers
import com.novel.cn.ext.toast
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.CategoryListContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.CategoryBean
import com.novel.cn.mvp.model.entity.RankBean
import com.novel.cn.mvp.ui.adapter.CategoryBookAdapter
import com.novel.cn.view.MultiStateView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import org.jetbrains.anko.toast


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/23/2019 17:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class CategoryListPresenter
@Inject
constructor(model: CategoryListContract.Model, rootView: CategoryListContract.View) :
        BasePresenter<CategoryListContract.Model, CategoryListContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    @Inject
    lateinit var mAdapter: CategoryBookAdapter

    private var mPageIndex = 1

    fun getCategoryList(novelTypeId: Int, parentId: Int, pullToRefresh: Boolean) {
        if (pullToRefresh) mPageIndex = 1

        val params = HashMap<String, String>()
        params.put("pageNum", mPageIndex.toString())
        params.put("pageSize", Constant.PAGE_SIZE.toString())
        params.put("novelTypeId", novelTypeId.toString())
        params.put("parentId", parentId.toString())

        mModel.getCategoryList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<CategoryBean>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<CategoryBean>) {
                        val list = t.data.list
                        if (t.data.total == 0) {
                            mRootView.showState(MultiStateView.VIEW_STATE_EMPTY)
                        } else {
                            mRootView.showState(MultiStateView.VIEW_STATE_CONTENT)
                            //判断是否还有下一页
                            val noMore = mPageIndex * Constant.PAGE_SIZE >= t.data.total
                            if (pullToRefresh) {
                                mAdapter.setNewData(list)
                                mRootView.refreshComplete()
                            } else {
                                mAdapter.addData(list!!)
                                if (!noMore)
                                    mAdapter.loadMoreComplete()
                            }
                            if (noMore)
                                mAdapter.loadMoreEnd()

                            //请求成功后，当前页改变
                            mPageIndex++
                        }
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        mRootView.refreshComplete()
                        if (pullToRefresh)
                            mRootView.showState(MultiStateView.VIEW_STATE_ERROR)
                    }
                })

    }

    fun addConllection(novelId: String, it: Int) {
        val params = HashMap<String, Any>()
        params["novel_id"] = novelId

        mModel.addConllection(params)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        toast("加入书架成功")
                        mRootView.conllectionSuccess(it)
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        toast(t.message)
                        mRootView.conllectionSuccess(it)
                    }
                })
    }

}
