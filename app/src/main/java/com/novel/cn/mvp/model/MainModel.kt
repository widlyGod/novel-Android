package com.novel.cn.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.ShareprefUtils
import com.novel.cn.app.Constant
import com.novel.cn.app.Preference
import javax.inject.Inject

import com.novel.cn.mvp.contract.MainContract
import com.novel.cn.mvp.model.api.service.UserService
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.LoginInfo
import com.novel.cn.mvp.model.entity.NoBodyEntity
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/18/2019 10:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class MainModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), MainContract.Model {

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)

    override fun uploadUseTime(): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(UserService::class.java).updateLoginTime(user!!.userId,ShareprefUtils.getLong(mApplication, "APP_USE_TIME", 0))
    }

    override fun uploadReadTime(): Observable<BaseResponse<Any>> {
//        val map = HashMap<String, Any?>()
//        map.put("userId", user!!.userId)
//        map.put("loginTime", ShareprefUtils.getLong(mApplication, "APP_USE_TIME", 0))
        return mRepositoryManager.obtainRetrofitService(UserService::class.java).updateLoginTime(user!!.userId,ShareprefUtils.getLong(mApplication, "APP_USE_TIME", 0))
    }

}
