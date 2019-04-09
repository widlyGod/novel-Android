package com.novel.cn.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.app.Constant
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.CommentContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.Comment
import com.novel.cn.mvp.ui.adapter.BookCommentAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import me.jessyan.rxerrorhandler.handler.ErrorHandlerFactory


@ActivityScope
class CommentPresenter
@Inject
constructor(model: CommentContract.Model, rootView: CommentContract.View) :
        BasePresenter<CommentContract.Model, CommentContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler

    private var mPageIndex = 1

    @Inject
    lateinit var mAdapter: BookCommentAdapter

    fun getCommentList(bookId: String?, pullToRefresh: Boolean) {

        if (pullToRefresh) mPageIndex = 1

        val params = HashMap<String, String>()
        params["pageNum"] = mPageIndex.toString()
        params["pageSize"] = Constant.PAGE_SIZE.toString()
        params["novelId"] = bookId.orEmpty()
        params["sort"] = "0"

        mModel.getCommentList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<MutableList<Comment>>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<MutableList<Comment>>) {

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

                        mRootView.showCommentCount(t.basePage.counts)
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)

                    }
                })
    }
}
