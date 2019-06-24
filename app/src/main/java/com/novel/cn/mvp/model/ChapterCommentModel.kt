package com.novel.cn.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import com.novel.cn.app.Constant
import javax.inject.Inject

import com.novel.cn.mvp.contract.ChapterCommentContract
import com.novel.cn.mvp.model.api.service.BookService
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.BookInfo
import com.novel.cn.mvp.model.entity.ChapterComment
import io.reactivex.Observable


@ActivityScope
class ChapterCommentModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ChapterCommentContract.Model {
    override fun deleteChapterComment(params: HashMap<String, Any?>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).deleteChapterComment(params)
    }

    override fun chapterComment(params: HashMap<String, Any?>):Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).chapterComment(params)
    }

    override fun getChapterComment(params: HashMap<String, Any?>):  Observable<BaseResponse<MutableList<ChapterComment>>>{
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getChapterComment(params)
    }

    override fun agree(commentId: String,type:Int): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).agree(commentId,type)
    }

    override fun getHotSearch(): Observable<BaseResponse<List<BookInfo>>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getHotSearch()
    }
}
