package com.novel.cn.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import com.novel.cn.app.Constant
import javax.inject.Inject

import com.novel.cn.mvp.contract.SearchContract
import com.novel.cn.mvp.model.api.service.BookService
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.HotNovelBean
import com.novel.cn.mvp.model.entity.SearchResultBean
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/25/2019 17:55
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class SearchModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), SearchContract.Model {

    override fun getSearchResult(param: String, pageIndex: Int): Observable<BaseResponse<SearchResultBean>> {
        val params = HashMap<String, Any>()
        params.put("pageNum", pageIndex)
        params.put("pageSize", Constant.PAGE_SIZE)
        params.put("param", param)
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).search(params)

    }

    override fun getHotWords(): Observable<BaseResponse<HotNovelBean>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getHotWords()
    }


}
