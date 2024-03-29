package com.novel.cn.mvp.model

import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.novel.cn.mvp.contract.BookManagerContract
import com.novel.cn.mvp.model.api.service.BookService
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.Book
import com.novel.cn.mvp.model.entity.Pagination
import io.reactivex.Observable
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet


@ActivityScope
class BookManagerModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), BookManagerContract.Model {
    override fun deleteBook(params: LinkedList<String>, type: Int): Observable<BaseResponse<Any>> {
        return if (type == 0)
            mRepositoryManager.obtainRetrofitService(BookService::class.java).deleteBook(params)
        else
            mRepositoryManager.obtainRetrofitService(BookService::class.java).cancelSubscribeList(params)
    }

    override fun getBookList(params: HashMap<String, String>, type: Int): Observable<BaseResponse<Pagination<Book>>> {
        return if (type == 0)
            mRepositoryManager.obtainRetrofitService(BookService::class.java).getBookshelf(params)
        else
            mRepositoryManager.obtainRetrofitService(BookService::class.java).getBookSubcribe(params)
    }

    override fun moveBook(params: HashMap<String, HashMap<String, Int>>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).moveBook(params)
    }
}
