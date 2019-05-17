package com.novel.cn.mvp.presenter

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.app.Constant
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.ChapterCommentContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.ChapterComment
import com.novel.cn.mvp.ui.adapter.ChapterCommentAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


@ActivityScope
class ChapterCommentPresenter
@Inject
constructor(model: ChapterCommentContract.Model, rootView: ChapterCommentContract.View) :
        BasePresenter<ChapterCommentContract.Model, ChapterCommentContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mAdapter: ChapterCommentAdapter
    private var mPageIndex = 1

    fun getChapterComment(mBookId: String?, mChapterId: String?, pullToRefresh: Boolean) {
        if (pullToRefresh) mPageIndex = 1
        val params = HashMap<String, Any?>()
        params["novelId"] = mBookId
        params["chapterId"] = mChapterId
        params["pageNum"] = mPageIndex
        params["pageSize"] = Constant.PAGE_SIZE
        mModel.getChapterComment(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<MutableList<ChapterComment>>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<MutableList<ChapterComment>>) {
                        mRootView.showCount(t.basePage.counts)
                        if (t.basePage.counts == 0) {
                            return
                        }
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
                    }
                })
    }

    fun chapterComment(mBookId: String, mChapterId: String, mVolumeId: String, content: String, isAuthor: String) {
        val params = HashMap<String, Any?>()
        params["novelId"] = mBookId
        params["chapterId"] = mChapterId
        params["volumeId"] = mVolumeId
        params["content"] = content
        params["remindUid"] = "1"
        params["replyType"] = "0"
        params["isAuthor"] = isAuthor
        mModel.chapterComment(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mRootView.releaseCommentSuccess()
                    }
                })
    }
}
