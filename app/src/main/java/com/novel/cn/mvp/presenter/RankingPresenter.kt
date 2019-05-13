package com.novel.cn.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.RankingContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.RankBean
import com.novel.cn.mvp.model.entity.RankResult
import com.novel.cn.mvp.ui.adapter.RankAdapter
import com.novel.cn.view.MultiStateView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


@FragmentScope
class RankingPresenter
@Inject
constructor(model: RankingContract.Model, rootView: RankingContract.View) :
        BasePresenter<RankingContract.Model, RankingContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mAdapter: RankAdapter


    fun getRankList() {
        mModel.getRank()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<MutableList<RankBean>>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<MutableList<RankBean>>) {
                        if (t.data.isEmpty()) {
                            mRootView.showState(MultiStateView.VIEW_STATE_EMPTY)
                        }else{
                            mRootView.showState(MultiStateView.VIEW_STATE_CONTENT)
                            mAdapter.setNewData(t.data)
                        }

                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        mRootView.showState(MultiStateView.VIEW_STATE_ERROR)
                    }
                })
    }
}
