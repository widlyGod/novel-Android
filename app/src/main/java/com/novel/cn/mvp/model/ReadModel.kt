package com.novel.cn.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.novel.cn.mvp.contract.ReadContract
import com.novel.cn.mvp.model.api.service.BookService
import com.novel.cn.mvp.model.api.service.UserService
import com.novel.cn.mvp.model.entity.*
import com.zchu.rxcache.data.CacheResult
import com.zchu.rxcache.kotlin.rxCache
import com.zchu.rxcache.stategy.CacheStrategy
import io.reactivex.Observable
import okhttp3.ResponseBody
import java.util.concurrent.TimeUnit


@ActivityScope
class ReadModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ReadContract.Model {

    companion object {
        private val CACHE_TIMEOUT = TimeUnit.DAYS.toMillis(1)
    }

    override fun preDownload(url: String?): Observable<ResponseBody> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).preDownload(url)
    }

    override fun getCalalogue(novelId: String): Observable<CacheResult<CalalogueVo>> {

        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getCalalogue(novelId)
                .map { it.data }
                .rxCache("catalogue$novelId", CacheStrategy.firstCacheTimeout(CACHE_TIMEOUT))
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

    override fun cancelCollection(bookId: String): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).cancelCollection(bookId)
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

    override fun updateRead(params: ArrayList<HashMap<String, Any>>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).updateRead(params)
    }

    override fun getUserAccountInfo(): Observable<BaseResponse<UserAccountBean>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getUserAccountInfo()
    }

    override fun reward(params: HashMap<String, Any?>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).reward(params)
    }

    override fun addAutoSubscribe(params: ArrayList<String>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).addAutoSubscribe(params)
    }

    override fun clickNum(params: HashMap<String, Any?>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).clickNum(params)
    }

    override fun getUserInfo(): Observable<BaseResponse<User>> {
        return mRepositoryManager.obtainRetrofitService(UserService::class.java).getUserInfo()
    }


    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}
