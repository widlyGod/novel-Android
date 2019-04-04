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
import com.novel.cn.mvp.model.entity.RankResult
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/26/2019 11:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
class RankingModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), RankingContract.Model {
    override fun getRank(): Observable<BaseResponse<MutableList<RankResult>>> {
        val params = HashMap<String, String>()
        params.put("pageNum", "1")
        params.put("pageSize", "10")
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getRank(params)
    }


}
