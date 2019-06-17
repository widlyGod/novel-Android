package com.novel.cn.mvp.presenter

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.app.Constant
import com.novel.cn.ext.applySchedulers
import com.novel.cn.ext.toast
import com.novel.cn.mvp.contract.BookDetailContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.Comment
import com.novel.cn.mvp.model.entity.NovelInfoBean
import com.novel.cn.mvp.ui.adapter.BookCommentAdapter
import com.novel.cn.view.MultiStateView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


@ActivityScope
class BookDetailPresenter
@Inject
constructor(model: BookDetailContract.Model, rootView: BookDetailContract.View) :
        BasePresenter<BookDetailContract.Model, BookDetailContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler

    @Inject
    lateinit var mAdapter: BookCommentAdapter

    private var mPageIndex = 1

    fun getBookDetail(bookId: String?) {
        mModel.getBookDetail(bookId)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<NovelInfoBean>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<NovelInfoBean>) {
                        mRootView.showBookDetail(t.data)
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        toast(t.message)
                    }
                })
    }

    fun addCollection(novelId: String) {
        val params = HashMap<String, Any>()
        params["novel_id"] = novelId

        mModel.addConllection(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mRootView.conllectionSuccess()
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                    }
                })
    }

    fun agree(position: Int) {
        val item = mAdapter.getItem(position) as Comment
        mModel.agree(item.commentId,0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        item.isThumbed = true
                        item.thumbUpNumber++
                        mAdapter.notifyItemChanged(position)
                    }
                })
    }

    fun deleteComment(position: Int) {
        val item = mAdapter.getItem(position) as Comment
        mModel.deleteComment(item.commentId)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
//                        mAdapter.notifyItemRemoved(position)
//                        mAdapter.notifyItemRangeChanged(position, mAdapter.data.size - position)
//                        mAdapter.data.removeAt(position)
                        mRootView.deleteCommentSuccess()
                    }
                })
    }

    fun comment(bookId: String?, bookName: String?, authorId: String?, authorName: String?, isAuthor: String?, content: String) {
        val params = HashMap<String, String?>()
        params["authorId"] = authorId
        params["novelId"] = bookId
        params["novelName"] = bookName
        params["penName"] = authorName
        params["isAuthor"] = isAuthor
        params["content"] = content
        mModel.comment(params)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mRootView.commentSuccess(t.message)
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
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mRootView.replySuccess(t.message)
                    }
                })
    }

    fun getCommentList(bookId: String?, pullToRefresh: Boolean) {

        if (pullToRefresh) mPageIndex = 1

        val params = HashMap<String, String>()
        params["pageNum"] = mPageIndex.toString()
        params["pageSize"] = Constant.PAGE_SIZE.toString()
        params["novelId"] = bookId.orEmpty()
        params["sort"] = "1"

        mModel.getCommentList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<MutableList<Comment>>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<MutableList<Comment>>) {
                        if (t.basePage.counts == 0) {
                            mRootView.showState(MultiStateView.VIEW_STATE_EMPTY)
                        } else {
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
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        mRootView.showState(MultiStateView.VIEW_STATE_ERROR)
                    }
                })
    }
}
