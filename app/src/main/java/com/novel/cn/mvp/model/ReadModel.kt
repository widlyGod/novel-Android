package com.novel.cn.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.novel.cn.mvp.contract.ReadContract
import com.novel.cn.mvp.model.api.service.BookService
import com.novel.cn.mvp.model.entity.*
import io.reactivex.Observable
import okhttp3.ResponseBody


@ActivityScope
class ReadModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ReadContract.Model {
    override fun preDownload(url: String?):Observable<ResponseBody> {
         return mRepositoryManager.obtainRetrofitService(BookService::class.java).preDownload(url)
    }

    override fun getCalalogue(novelId: String): Observable<BaseResponse<MutableList<Calalogue>>> {

        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getCalalogue(novelId)
    }

    override fun getChapterInfo(link: String?): Observable<BaseResponse<ChapterInfoBean>> {
         return mRepositoryManager.obtainRetrofitService(BookService::class.java).getChapterInfo(link)
    }

    override fun isChargeChapter(param: HashMap<String, Any?>): Observable<BaseResponse<ChargeChapter>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).isChargeChapter(param)
    }

    override fun subscribeBook(params: HashMap<String, Any?>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).subcribeBook(params)
    }

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
