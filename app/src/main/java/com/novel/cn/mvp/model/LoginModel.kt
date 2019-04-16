package com.novel.cn.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.novel.cn.mvp.contract.LoginContract
import com.novel.cn.mvp.model.api.service.UserService
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.LoginInfo
import io.reactivex.Observable


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
class LoginModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), LoginContract.Model {


    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun login(userName: String, password: String): Observable<BaseResponse<LoginInfo>> {

        val map = HashMap<String,String>()
        map.put("keyword",userName)
        map.put("userPassword",password)
        return mRepositoryManager.obtainRetrofitService(UserService::class.java).login(map)
    }

    override fun loginThrid(url: String, params: HashMap<String, String>): Observable<BaseResponse<LoginInfo>> {
        return mRepositoryManager.obtainRetrofitService(UserService::class.java).loginThrid(url,params)
    }

}
