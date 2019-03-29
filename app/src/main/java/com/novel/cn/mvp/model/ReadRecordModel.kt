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
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.Book
import com.novel.cn.mvp.model.entity.Pagination
import io.reactivex.Observable



@ActivityScope
class ReadRecordModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), ReadRecordContract.Model {
    override fun cleanRecord(): Observable<BaseResponse<Any>> {
         return mRepositoryManager.obtainRetrofitService(BookService::class.java).cleanRecord()
    }

    override fun getReadRecordList(pageIndex: Int): Observable<BaseResponse<Pagination<Book>>> {
        val params = HashMap<String,String>()
        params.put("pageNum",pageIndex.toString())
        params.put("pageSize", Constant.PAGE_SIZE.toString())
         return mRepositoryManager.obtainRetrofitService(BookService::class.java).getReadRecordList(params)
    }


}
