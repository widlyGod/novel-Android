package com.novel.cn.mvp.presenter

import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.LogUtils
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.app.Constant
import com.novel.cn.mvp.contract.BookshelfContract
import com.novel.cn.mvp.model.entity.*
import com.novel.cn.view.MultiStateView
import com.zchu.rxcache.data.CacheResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import javax.inject.Inject


@FragmentScope
class BookshelfPresenter
@Inject
constructor(model: BookshelfContract.Model, rootView: BookshelfContract.View) :
        BasePresenter<BookshelfContract.Model, BookshelfContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler

    private var mPageIndex = 1

    fun getBookshelfList(pullToRefresh: Boolean) {
        if (pullToRefresh) mPageIndex = 1

        mModel.getBookshelf(mPageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<CacheResult<Pagination<Book>>>(mErrorHandler) {
                    override fun onNext(t: CacheResult<Pagination<Book>>) {
                        if (t.data.total == 0) {
                            mRootView.showState(MultiStateView.VIEW_STATE_EMPTY)
                        } else {
                            mRootView.showState(MultiStateView.VIEW_STATE_CONTENT)
                            //判断是否还有下一页
                            val noMore = mPageIndex * Constant.PAGE_SIZE >= t.data.total
                            mRootView.showBookshelfList(pullToRefresh, t.data.book)
                            mRootView.complete(pullToRefresh)
                            if (noMore)
                                mRootView.noMore()
                            //请求成功后，当前页改变
                            mPageIndex++
                        }
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        mRootView.complete(pullToRefresh)
                        if (pullToRefresh)
                            mRootView.showState(MultiStateView.VIEW_STATE_ERROR)
                    }
                })


    }

    fun signIn(userId: String) {
        val params = HashMap<String, String>()
        params.put("userId", userId)
        mModel.signIn(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mRootView.signInSuccess()
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                    }

                })
    }

    fun validateSignIn() {
        mModel.validateSignIn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<SignIn>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<SignIn>) {
                        mRootView.changeSignInInfo(t.data)
                    }
                })
    }

    fun getReadTime() {
        mModel.getReadTime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<ReadTimeBean>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<ReadTimeBean>) {
                        mRootView.getReadTimeSuccess(t.data.readTime)
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                    }
                })
    }
}
