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
import io.reactivex.Observable


@ActivityScope
class ChapterCommentModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ChapterCommentContract.Model {
    override fun getChapterComment(params: HashMap<String, Any?>): Observable<BaseResponse<Any>> {


        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getChapterComment(params)
    }


}
