package com.novel.cn.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import com.novel.cn.app.Constant
import javax.inject.Inject

import com.novel.cn.mvp.contract.ReadRecordContract
import com.novel.cn.mvp.model.api.service.BookService
import com.novel.cn.mvp.model.entity.*
import com.zchu.rxcache.data.CacheResult
import com.zchu.rxcache.kotlin.rxCache
import com.zchu.rxcache.stategy.CacheStrategy
import io.reactivex.Observable
import java.util.concurrent.TimeUnit


@ActivityScope
class ReadRecordModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ReadRecordContract.Model {
    companion object {
        private val CACHE_TIMEOUT = TimeUnit.DAYS.toMillis(1)
    }

    override fun cleanRecord(): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).cleanRecord()
    }

    override fun getReadRecordList(pageIndex: Int): Observable<BaseResponse<Pagination<Book>>> {
        val params = HashMap<String, String>()
        params.put("pageNum", pageIndex.toString())
        params.put("pageSize", Constant.PAGE_SIZE.toString())
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getReadRecordList(params)
    }

    override fun getBookDetail(bookId: String?): Observable<BaseResponse<NovelInfoBean>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getBookDetail(bookId)
    }

    override fun getCalalogue(novelId: String): Observable<CacheResult<CalalogueVo>> {

        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getCalalogue(novelId)
                .map { it.data }
                .rxCache("catalogue$novelId", CacheStrategy.firstCacheTimeout(CACHE_TIMEOUT))
    }

}
