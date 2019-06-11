package com.novel.cn.mvp.presenter

import android.app.Application
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.AppManager
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.app.ApiException
import com.novel.cn.ext.applySchedulers
import com.novel.cn.ext.toast
import com.novel.cn.mvp.contract.UserInfoContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


@ActivityScope
class UserInfoPresenter
@Inject
constructor(model: UserInfoContract.Model, rootView: UserInfoContract.View) :
        BasePresenter<UserInfoContract.Model, UserInfoContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager


    fun modifyUserInfo(params: HashMap<String, Any?>) {
        mModel.modifyUserInfo(params)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<User>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<User>) {
                        mRootView.modifySuccess(t.data)
                    }
                })
    }

    fun checkNickName(nickname: String, param: HashMap<String, Any?>) {

        val params = HashMap<String, String>()
        params.put("nickName", nickname)

        mModel.checkNickName(params)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        modifyUserInfo(param)
                    }


                    override fun onError(t: Throwable) {
                        //全局处理异常 super.onError(t)默认实现，和下面代码同理
                        toast(t.message)
//                        super.onError(t)
                    }
                })

    }
}
