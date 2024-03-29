package com.novel.cn.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.novel.cn.mvp.contract.PublishContract
import com.novel.cn.mvp.model.api.service.BookService
import com.novel.cn.mvp.model.api.service.UserService
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.BookInfo
import com.novel.cn.mvp.model.entity.Novel
import com.novel.cn.mvp.model.entity.User
import io.reactivex.Observable
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/18/2019 15:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class PublishModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), PublishContract.Model {

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;


    override fun public(fliesPath: List<MultipartBody.Part>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).publishMoment(fliesPath)
    }

    override fun getHotSearch(): Observable<BaseResponse<List<BookInfo>>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getHotSearch()
    }

    override fun getMomentNovel(novelId: String): Observable<BaseResponse<Novel>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getMomentNovel(novelId)
    }

    override fun getUserInfo(): Observable<BaseResponse<User>> {
        return mRepositoryManager.obtainRetrofitService(UserService::class.java).getUserInfo()
    }
}
