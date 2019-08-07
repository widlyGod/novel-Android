package com.novel.cn.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.novel.cn.mvp.contract.CircleCommentContract
import com.novel.cn.mvp.model.api.service.BookService
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.Circle
import com.novel.cn.mvp.model.entity.CircleCommentBean
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/29/2019 11:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class CircleCommentModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), CircleCommentContract.Model {

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun getMomentDetail(momentId: String): Observable<BaseResponse<Circle>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getMomentDetail(momentId)
    }

    override fun getComments(params: HashMap<String, String>): Observable<BaseResponse<CircleCommentBean>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getComments(params)
    }

    override fun agree(momentId: String): Observable<BaseResponse<Any>> {
        val params = HashMap<String, String>()
        params["momentId"] = momentId
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).circleAgree(params)
    }

    override fun chapterComment(params: HashMap<String, Any?>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).circleChapterComment(params)
    }

    override fun agreeReply(momentId: String): Observable<BaseResponse<Any>> {
        val params = HashMap<String, String>()
        params["commentId"] = momentId
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).agreeReply(params)
    }

    override fun chapterCommentReply(params: HashMap<String, Any?>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).chapterCommentReply(params)
    }

    override fun deleteCircleComment(params: HashMap<String, Any?>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).deleteCircleComment(params)
    }

    override fun disAgree(params: HashMap<String, Any?>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).disAgree(params)
    }

}
