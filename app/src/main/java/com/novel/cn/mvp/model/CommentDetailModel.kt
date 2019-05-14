package com.novel.cn.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.novel.cn.mvp.contract.CommentDetailContract
import com.novel.cn.mvp.model.api.service.BookService
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.Reply
import io.reactivex.Observable


@ActivityScope
class CommentDetailModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), CommentDetailContract.Model {
    override fun reply(params: HashMap<String, Any?>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).reply(params)
    }

    override fun getReplyList(params: HashMap<String, Any>): Observable<BaseResponse<MutableList<Reply>>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getReplyList(params)
    }


}
