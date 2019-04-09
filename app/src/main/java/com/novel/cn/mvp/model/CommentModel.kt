package com.novel.cn.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.novel.cn.mvp.contract.CommentContract
import com.novel.cn.mvp.model.api.service.BookService
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.Comment
import io.reactivex.Observable



@ActivityScope
class CommentModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), CommentContract.Model {
    override fun getCommentList(params: HashMap<String, String>): Observable<BaseResponse<MutableList<Comment>>> {
       return mRepositoryManager.obtainRetrofitService(BookService::class.java).getCommentList(params)
    }


}
