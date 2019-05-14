package com.novel.cn.mvp.presenter

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.mvp.contract.BookDetailContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.Comment
import com.novel.cn.mvp.model.entity.NovelInfoBean
import com.novel.cn.mvp.ui.adapter.BookCommentAdapter
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

    fun getBookDetail(bookId: String?) {
        mModel.getBookDetail(bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<NovelInfoBean>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<NovelInfoBean>) {
                        mRootView.showBookDetail(t.data)
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                    }
                })
    }

    fun addCollection(novelId: String) {
        val params = HashMap<String,Any>()
        params["novel_id"] = novelId

        mModel.addConllection(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mRootView.conllectionSuccess()
                    }
                })
    }

    fun agree(position: Int) {
        val item = mAdapter.getItem(position) as Comment
        mModel.agree(item.commentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        item.thumbUp = true
                        item.thumbUpNumber++
                        mAdapter.notifyItemChanged(position)
                    }
                })
    }

    fun deleteComment(position: Int) {
        val item = mAdapter.getItem(position) as Comment
        mModel.deleteComment(item.commentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mAdapter.notifyItemRemoved(position)
                    }
                })
    }
}
