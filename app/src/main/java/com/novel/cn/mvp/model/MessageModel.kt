package com.novel.cn.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.novel.cn.mvp.contract.MessageContract
import com.novel.cn.mvp.model.api.service.UserService
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.MessageBean
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/28/2019 16:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class MessageModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), MessageContract.Model {

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun getMessageList(params: HashMap<String, String>): Observable<BaseResponse<MessageBean>> {
        return mRepositoryManager.obtainRetrofitService(UserService::class.java).getMessasgeList(params)
    }

    override fun messageRead(): Observable<BaseResponse<Any>> {
        val params = HashMap<String, String>()
        params["keyWord"] = ""
        return mRepositoryManager.obtainRetrofitService(UserService::class.java).messageRead(params)
    }


}
