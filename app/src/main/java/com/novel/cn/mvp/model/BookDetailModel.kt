package com.novel.cn.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.novel.cn.mvp.contract.BookDetailContract
import com.novel.cn.mvp.model.api.service.BookService
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.NovelInfoBean
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/01/2019 10:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class BookDetailModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), BookDetailContract.Model {
    override fun agree(commentId: String): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).agree(commentId)
    }

    override fun addConllection(params: HashMap<String, Any>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).addConllcetion(params)
    }

    override fun getBookDetail(bookId: String?): Observable<BaseResponse<NovelInfoBean>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getBookDetail(bookId)
    }


}
