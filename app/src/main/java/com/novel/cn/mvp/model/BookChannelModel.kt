package com.novel.cn.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.FragmentScope
import javax.inject.Inject

import com.novel.cn.mvp.contract.BookChannelContract
import com.novel.cn.mvp.model.api.service.BookService
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.ChannelData
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/25/2019 09:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
class BookChannelModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), BookChannelContract.Model {
    override fun getChannelData(type: Int): Observable<BaseResponse<Map<String,Any>>> {
        val params = HashMap<String,Int>()
        params.put("labelType",type)
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getChannelData(params)
    }

}
