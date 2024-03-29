package com.novel.cn.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.novel.cn.mvp.contract.UserInfoContract
import com.novel.cn.mvp.model.api.service.BookService
import com.novel.cn.mvp.model.api.service.UserService
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.User
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/11/2019 17:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class UserInfoModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), UserInfoContract.Model {

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun modifyUserInfo(params: HashMap<String, Any?>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(UserService::class.java).modifyUserInfo(params)
    }
    override fun checkNickName(params: HashMap<String, String>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(UserService::class.java).checkNickName(params)
    }

}
