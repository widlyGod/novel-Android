package com.novel.cn.mvp.presenter

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.app.Constant
import com.novel.cn.app.isNullOrEmpty
import com.novel.cn.ext.applySchedulers
import com.novel.cn.ext.toast
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.RankListContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.RankBean
import com.novel.cn.mvp.ui.adapter.RankListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import org.jetbrains.anko.toast


@ActivityScope
class RankListPresenter
@Inject
constructor(model: RankListContract.Model, rootView: RankListContract.View) :
        BasePresenter<RankListContract.Model, RankListContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler

    private var mPageIndex = 1

    @Inject
    lateinit var mAdapter: RankListAdapter

    fun getRank(code: String?, pullToRefresh: Boolean) {
        if (pullToRefresh) mPageIndex = 1

        val params = HashMap<String, String>()
        params.put("pageNum", mPageIndex.toString())
        params.put("pageSize", Constant.PAGE_SIZE.toString())
        params.put("boardsEnumCode", code.orEmpty())

        mModel.getRank(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<MutableList<RankBean>>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<MutableList<RankBean>>) {
                        val list = t.data.getOrNull(0)?.result?.week
                        val noMore = list.isNullOrEmpty()
                        if (pullToRefresh) {
                            mAdapter.setNewData(list)
                            mRootView.refreshComplete()
                        } else {
                            if (!noMore)
                                mAdapter.addData(list!!)
                            mAdapter.loadMoreComplete()
                        }
                        if (noMore)
                            mAdapter.loadMoreEnd()

                        //请求成功后，当前页改变
                        mPageIndex++
                    }
                })

    }

    fun addConllection(novelId: String, it: Int) {
        val params = HashMap<String,Any>()
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
