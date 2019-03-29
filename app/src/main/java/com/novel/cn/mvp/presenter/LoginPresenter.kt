package com.novel.cn.mvp.presenter

import android.app.Application
import cn.sharesdk.framework.Platform
import cn.sharesdk.framework.PlatformActionListener
import cn.sharesdk.framework.ShareSDK
import cn.sharesdk.sina.weibo.SinaWeibo
import cn.sharesdk.tencent.qq.QQ
import cn.sharesdk.wechat.friends.Wechat

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.LogUtils
import com.jess.arms.utils.RxLifecycleUtils
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.LoginContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.UserInfo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/18/2019 17:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class LoginPresenter
@Inject
constructor(model: LoginContract.Model, rootView: LoginContract.View) :
        BasePresenter<LoginContract.Model, LoginContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager


    fun login(userName: String, password: String) {

        mModel.login(userName, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<UserInfo>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<UserInfo>) {
                        mRootView.loginSuccess(t.data)
                    }

                    override fun onError(t: Throwable) {
                        //全局处理异常 super.onError(t)默认实现，和下面代码同理
                        mErrorHandler.handlerFactory.handleError(t)
//                        super.onError(t)
                    }
                })
    }

    /**
     * 调用第三方授权
     */
     fun authorize(type: String) {
        val platform = ShareSDK.getPlatform(type)
        platform.SSOSetting(false)
        platform.removeAccount(true)

        platform.platformActionListener = object : PlatformActionListener {
            override fun onComplete(p0: Platform?, p1: Int, p2: java.util.HashMap<String, Any>?) {
                val params = HashMap<String, String>()

                val db = platform.db
                params.put("openId", db.userId)
                params.put("nickName", db.userName)
                // 1或0   男,女
                val gender = if (db.userGender == "m") "1" else "0"
                params.put("gender", gender)
                params.put("channelId", "-1")
                params.put("photo", db.userIcon)
                loginThrid(type, params)

            }

            override fun onCancel(p0: Platform?, p1: Int) {

            }

            override fun onError(p0: Platform?, p1: Int, t: Throwable?) {
                LogUtils.warnInfo("==============第三方登陆回调错误信息:${t?.message}")
            }
        }
        platform.showUser(null)

    }

    fun loginThrid(type: String, params: HashMap<String, String>) {
        var url = ""
        when (type) {
            QQ.NAME -> url = "novelUserService/login/qqLogin"
            Wechat.NAME -> url = "novelUserService/login/wxLogin"
            SinaWeibo.NAME -> url = "novelUserService/login/wbLogin"
        }
        mModel.loginThrid(url, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<UserInfo>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<UserInfo>) {
                        mRootView.loginSuccess(t.data)
                    }

                    override fun onError(t: Throwable) {
                        //全局处理异常 super.onError(t)默认实现，和下面代码同理
                        mErrorHandler.handlerFactory.handleError(t)
//                        super.onError(t)
                    }
                })
    }
}
