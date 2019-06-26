package com.novel.cn.mvp.presenter

import android.app.Application
import com.google.gson.Gson

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.ext.applySchedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.RechargeContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.PayInfoBean
import com.novel.cn.mvp.model.entity.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


@ActivityScope
class RechargePresenter
@Inject
constructor(model: RechargeContract.Model, rootView: RechargeContract.View) :
        BasePresenter<RechargeContract.Model, RechargeContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mGson: Gson



    override fun onDestroy() {
        super.onDestroy();
    }

    fun recharge(code: String, money: String) {
        mModel.recharge(code,money)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<PayInfoBean>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<PayInfoBean>) {
                        val data = mGson.toJson(t.data.payCode)
                        if(code == "1"){
                            mRootView.showRechargeInfo(t.data.payCode.toString(),code)
                        }else{
                            mRootView.showRechargeInfo(data,code)
                        }
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                    }
                })
    }

    fun getUserInfo() {
        mModel.getUserInfo()
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<User>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<User>) {
                        mRootView.showUserInfo(t.data)
                    }
                })
    }
}
