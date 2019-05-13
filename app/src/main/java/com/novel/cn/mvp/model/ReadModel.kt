package com.novel.cn.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.novel.cn.mvp.contract.ReadContract
import com.novel.cn.mvp.model.api.service.BookService
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.ChapterBean
import com.novel.cn.mvp.model.entity.ChapterInfoBean
import com.novel.cn.mvp.model.entity.Volume
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/03/2019 15:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class ReadModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ReadContract.Model {
    override fun cancelCollection(params: HashMap<String, Any>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).cancelCollection(params)
    }

    override fun addConllection(params: HashMap<String, Any>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).addConllcetion(params)
    }

    override fun readNovel(params: HashMap<String, Any?>): Observable<BaseResponse<ChapterInfoBean>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).readNovel(params)
    }

    override fun getChapterList(params: HashMap<String, Any?>): Observable<BaseResponse<ChapterBean>> {
         return mRepositoryManager.obtainRetrofitService(BookService::class.java).getChapterList(params)
    }

    override fun getVolumeList(bookId: String?): Observable<BaseResponse<MutableList<Volume>?>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getVolumeList(bookId)
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}