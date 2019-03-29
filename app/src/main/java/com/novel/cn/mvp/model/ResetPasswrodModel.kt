package com.novel.cn.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.novel.cn.mvp.contract.ResetPasswrodContract
import com.novel.cn.mvp.model.api.service.UserService
import com.novel.cn.mvp.model.entity.BaseResponse
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/20/2019 14:43
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class ResetPasswrodModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ResetPasswrodContract.Model {
    override fun resetPasswrod(params: HashMap<String, String>): Observable<BaseResponse<Any>> {
         return mRepositoryManager.obtainRetrofitService(UserService::class.java).resetPassword(params)
    }

    override fun sendCode(params: HashMap<String, String>): Observable<BaseResponse<Any>> {
         return  mRepositoryManager.obtainRetrofitService(UserService::class.java).sendCode("novelUserService/user/valiAccount",params)
    }


}
