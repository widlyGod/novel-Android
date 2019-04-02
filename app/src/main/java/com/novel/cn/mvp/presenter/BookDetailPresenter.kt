package com.novel.cn.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.BookDetailContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.Book
import com.novel.cn.mvp.model.entity.NovelInfoBean
import com.novel.cn.mvp.model.entity.Pagination
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


@ActivityScope
class BookDetailPresenter
@Inject
constructor(model: BookDetailContract.Model, rootView: BookDetailContract.View) :
        BasePresenter<BookDetailContract.Model, BookDetailContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager


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
}
