package com.novel.cn.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.app.Constant
import com.novel.cn.ext.applySchedulers
import com.novel.cn.ext.toast
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.CircleCommentReplyDetailContract
import com.novel.cn.mvp.model.entity.*
import com.novel.cn.mvp.ui.adapter.CircleCommentReplyAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


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
class CircleCommentReplyDetailPresenter
@Inject
constructor(model: CircleCommentReplyDetailContract.Model, rootView: CircleCommentReplyDetailContract.View) :
        BasePresenter<CircleCommentReplyDetailContract.Model, CircleCommentReplyDetailContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    private var mPageIndex = 1

    @Inject
    lateinit var mAdapter: CircleCommentReplyAdapter

    fun getReplys(commentId: String, pullToRefresh: Boolean = true) {
        if (pullToRefresh) mPageIndex = 1

        val params = HashMap<String, String>()
        params["pageNum"] = mPageIndex.toString()
        params["pageSize"] = Constant.PAGE_SIZE.toString()
        params["commentId"] = commentId
        mModel.getReplys(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<CircleCommentRaeplyAllBean>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<CircleCommentRaeplyAllBean>) {
                        if (t.data.totalElements == 0) {
                        } else {
                            val noMore = t.data.totalPages <= mPageIndex

                            if (pullToRefresh) {
                                mAdapter.setNewData(t.data.content)
                            } else {
                                mAdapter.addData(t.data.content)
                                mAdapter.loadMoreComplete()
                            }
                            if (noMore)
                                mAdapter.loadMoreEnd()

                            mPageIndex++
                        }
                        mRootView.RefreshFinsh()
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        mRootView.RefreshFinsh()
                    }
                })
    }

    fun getReplyDetail(commentId: String) {
        mModel.getReplyDetail(commentId)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Content>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Content>) {
                        mRootView.getReplyDetaillSuccess(t.data)
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        toast(t.message)
                    }
                })
    }

    fun agreeReply(commentId: String) {
        mModel.agreeReply(commentId)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mRootView.agreeSuccess()
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        toast(t.message)
                    }
                })
    }

    fun chapterComment(commentId: String, commentContent: String, toReplyUserId: String, replyType: String) {
        val params = HashMap<String, Any?>()
        params["commentId"] = commentId
        params["replyContent"] = commentContent
        params["toReplyUserId"] = toReplyUserId
        params["replyType"] = replyType
        mModel.chapterCommentReply(params)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mRootView.chapterCommentSuccess()
                        toast("评论成功")
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        toast(t.message)
                    }
                })
    }

    fun agreeReplyReply(position: Int) {
        val item = mAdapter.getItem(position)
        mModel.agreeReplyReply(item?.replyId!!)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        item.hadThumbed = true
                        item.thumbNum++
                        mAdapter.notifyDataSetChanged()
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        toast(t.message)
                    }
                })
    }

}
