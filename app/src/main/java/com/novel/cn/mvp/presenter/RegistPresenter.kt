package com.novel.cn.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.app.ApiException
import com.novel.cn.ext.applySchedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.RegistContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.LoginInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


@ActivityScope
class RegistPresenter
@Inject
constructor(model: RegistContract.Model, rootView: RegistContract.View) :
        BasePresenter<RegistContract.Model, RegistContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager


    fun toRegist(nickname: String, email: String, password: String, emailCode: String) {

        val params = HashMap<String, String>()
        params.put("nickName", nickname)
        params.put("keyword", email)
        params.put("userPassword", ArmsUtils.encodeToMD5(password))
        params.put("code", emailCode)

        mModel.regist(params)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<LoginInfo>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<LoginInfo>) {
                        mRootView.registSuccess(t.data)
                    }

                    override fun onError(t: Throwable) {
                        //全局处理异常 super.onError(t)默认实现，和下面代码同理
                        mErrorHandler.handlerFactory.handleError(t)
//                        super.onError(t)
                    }
                })

    }

    fun checkNickName(nickname: String) {

        val params = HashMap<String, String>()
        params.put("nickName", nickname)

        mModel.checkNickName(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mRootView.checkNickNameSuccess(t.code)
                    }


                    override fun onError(t: Throwable) {
                        //全局处理异常 super.onError(t)默认实现，和下面代码同理
                        mRootView.checkNickNameFail(t is ApiException)
//                        super.onError(t)
                    }
                })

    }

    fun sendCode(email: String) {
        val params = HashMap<String, String>()
        params.put("keyword", email)
        mModel.sendCode(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mRootView.sendSuccess()
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                    }
                })
    }
}
