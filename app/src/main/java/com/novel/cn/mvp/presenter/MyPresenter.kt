package com.novel.cn.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.MyContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


@FragmentScope
class MyPresenter
@Inject
constructor(model: MyContract.Model, rootView: MyContract.View) :
        BasePresenter<MyContract.Model, MyContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler


    fun getUserInfo() {
        mModel.getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<User>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<User>) {
                        mRootView.showUserInfo(t.data)
                    }
                })
    }
}
