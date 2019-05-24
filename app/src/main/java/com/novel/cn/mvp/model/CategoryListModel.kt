package com.novel.cn.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.novel.cn.mvp.contract.CategoryListContract
import com.novel.cn.mvp.model.api.service.BookService
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.CategoryBean
import com.novel.cn.mvp.model.entity.RankBean
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/23/2019 17:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class CategoryListModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), CategoryListContract.Model {


    override fun getCategoryList(params: HashMap<String, String>): Observable<BaseResponse<CategoryBean>> {
        return  mRepositoryManager.obtainRetrofitService(BookService::class.java).getNovels(params)
    }

    override fun addConllection(params: HashMap<String, Any>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).addConllcetion(params)
    }
}
