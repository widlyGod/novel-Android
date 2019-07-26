package com.novel.cn.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.app.Constant
import com.novel.cn.ext.applySchedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.CircleContract
import com.novel.cn.mvp.model.entity.*
import com.novel.cn.mvp.ui.adapter.CircleAdapter
import com.novel.cn.view.MultiStateView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/19/2019 16:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
class CirclePresenter
@Inject
constructor(model: CircleContract.Model, rootView: CircleContract.View) :
        BasePresenter<CircleContract.Model, CircleContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application

    @Inject
    lateinit var mCircleAdapter: CircleAdapter

    private var mPageIndex = 1

    fun getAllMoments(pullToRefresh: Boolean = true){
        if (pullToRefresh) mPageIndex = 1

        val params = HashMap<String, String>()
        params["pageNum"] = mPageIndex.toString()
        params["pageSize"] = Constant.PAGE_SIZE.toString()
        mModel.getAllMoments(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<CircleBean>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<CircleBean>) {
                        mRootView.refreshComplete()
                        if (t.data.total == 0) {
                            mRootView.showState(MultiStateView.VIEW_STATE_EMPTY)
                        } else {
                            mRootView.showState(MultiStateView.VIEW_STATE_CONTENT)
                            val noMore = t.data.pages <= mPageIndex

                            if (pullToRefresh) {
                                mCircleAdapter.setNewData(t.data.list)
                            } else {
                                mCircleAdapter.addData(t.data.list)
                                mCircleAdapter.loadMoreComplete()
                            }
                            if (noMore)
                                mCircleAdapter.loadMoreEnd()

                            mPageIndex++
                        }
                    }

                    override fun onError(t: Throwable) {
                        mRootView.refreshComplete()
                        super.onError(t)
                    }
                })
    }

}
