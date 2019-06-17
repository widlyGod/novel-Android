package com.novel.cn.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.integration.EventBusManager
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.app.Constant
import com.novel.cn.eventbus.BookCommentEvent
import com.novel.cn.ext.applySchedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.CommentDetailContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.Comment
import com.novel.cn.mvp.model.entity.Reply
import com.novel.cn.mvp.ui.adapter.BookReplyAdapter
import com.novel.cn.view.MultiStateView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


@ActivityScope
class CommentDetailPresenter
@Inject
constructor(model: CommentDetailContract.Model, rootView: CommentDetailContract.View) :
        BasePresenter<CommentDetailContract.Model, CommentDetailContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler

    @Inject
    lateinit var mAdapter: BookReplyAdapter

    private var mPageIndex = 1


    fun getReplyList(commentId: String, pullToRefresh: Boolean) {
        if (pullToRefresh) mPageIndex = 1

        val params = HashMap<String, Any>()
        params["pageNum"] = mPageIndex
        params["pageSize"] = Constant.PAGE_SIZE
        params["commentId"] = commentId
        mModel.getReplyList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<MutableList<Reply>>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<MutableList<Reply>>) {

                        if (t.basePage.counts == 0) {
                            mRootView.showState(MultiStateView.VIEW_STATE_EMPTY)
                            return
                        }
                        mRootView.showState(MultiStateView.VIEW_STATE_CONTENT)

                        val noMore = t.basePage.pages <= mPageIndex
                        if (pullToRefresh) {
                            mAdapter.setNewData(t.data)
                        } else {
                            mAdapter.addData(t.data)
                            mAdapter.loadMoreComplete()
                        }
                        if (noMore)
                            mAdapter.loadMoreEnd()

                        mPageIndex++
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        mAdapter.loadMoreComplete()
                        if (mAdapter.itemCount == 0) {
                            mRootView.showState(MultiStateView.VIEW_STATE_CONTENT)
                        }
                    }

                })
    }

    fun reply(commentId: String, content: String, userId: String, type: Int, isAuthor: String) {
        val params = HashMap<String, Any?>()
        params["commentId"] = commentId
        params["content"] = content
        params["remindUid"] = userId
        params["replyType"] = type
        params["isAuthor"] = isAuthor
        mModel.reply(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mRootView.replySuccess(t.message)
                    }
                })
    }

    fun deleteComment(position: Int) {
        val item = mAdapter.getItem(position) as Reply
        val params = HashMap<String, Any?>()
        params["replyId"] = item.replyId
        params["commentId"] = item.commentId
        mModel.deleteComment(params)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mRootView.deleteCommentSuccess()
                        EventBusManager.getInstance().post(BookCommentEvent())
                    }
                })
    }
}
