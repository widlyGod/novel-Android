package com.novel.cn.mvp.model

import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.novel.cn.mvp.contract.RegistContract
import com.novel.cn.mvp.model.api.service.UserService
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.LoginInfo
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/19/2019 18:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class RegistModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), RegistContract.Model {
    override fun checkNickName(params: HashMap<String, String>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(UserService::class.java).checkNickName(params)
    }

    override fun sendCode(params: HashMap<String,String>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(UserService::class.java).sendCode("novelUserService/user/sendCode", params)
    }


    override fun regist(params: HashMap<String, String>): Observable<BaseResponse<LoginInfo>> {
        return mRepositoryManager.obtainRetrofitService(UserService::class.java).regist(params)
    }
}
