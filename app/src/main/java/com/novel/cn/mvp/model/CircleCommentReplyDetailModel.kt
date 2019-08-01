package com.novel.cn.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.novel.cn.mvp.contract.CircleCommentReplyDetailContract
import com.novel.cn.mvp.model.api.service.BookService
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.CircleCommentRaeplyAllBean
import com.novel.cn.mvp.model.entity.Content
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/01/2019 14:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class CircleCommentReplyDetailModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), CircleCommentReplyDetailContract.Model {
    override fun agreeReplyReply(replyId: String): Observable<BaseResponse<Any>> {
        val params = HashMap<String, String>()
        params["replyId"] = replyId
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).agreeReplyReply(params)
    }

    override fun agreeReply(momentId: String): Observable<BaseResponse<Any>> {
        val params = HashMap<String, String>()
        params["commentId"] = momentId
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).agreeReply(params)
    }

    override fun chapterCommentReply(params: HashMap<String, Any?>): Observable<BaseResponse<Any>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).chapterCommentReply(params)
    }

    override fun getReplyDetail(replyId: String): Observable<BaseResponse<Content>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getReplyDetail(replyId)
    }

    override fun getReplys(params: HashMap<String, String>): Observable<BaseResponse<CircleCommentRaeplyAllBean>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).getReplys(params)
    }

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

}
