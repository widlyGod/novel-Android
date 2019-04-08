package com.novel.cn.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.FragmentScope
import javax.inject.Inject

import com.novel.cn.mvp.contract.RankingContract
import com.novel.cn.mvp.model.api.service.BookService
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.RankBean
import com.novel.cn.mvp.model.entity.RankResult
import io.reactivex.Observable


@FragmentScope
class RankingModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), RankingContract.Model {
    override fun getRank(): Observable<BaseResponse<MutableList<RankBean>>> {
        val params = HashMap<String, String>()
        params.put("pageNum", "1")
        params.put("pageSize", "10")
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getRank(params)
    }


}
