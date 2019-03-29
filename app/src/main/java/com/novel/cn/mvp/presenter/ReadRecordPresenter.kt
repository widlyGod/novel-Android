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

import com.novel.cn.mvp.contract.ReadRecordContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.Book
import com.novel.cn.mvp.model.entity.Pagination
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


@ActivityScope
class ReadRecordPresenter
@Inject
constructor(model: ReadRecordContract.Model, rootView: ReadRecordContract.View) :
        BasePresenter<ReadRecordContract.Model, ReadRecordContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler


    private var mPageIndex = 1

    fun getReadRecordList(pullToRefresh: Boolean) {

        if (pullToRefresh) mPageIndex = 1

        mModel.getReadRecordList(mPageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Pagination<Book>>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Pagination<Book>>) {
                        //判断是否还有下一页
                        val noMore = mPageIndex * Constant.PAGE_SIZE >= t.data.total
                        mRootView.showReadRecordList(pullToRefresh, t.data.book)
                        mRootView.complete(pullToRefresh)
                        if (noMore)
                            mRootView.noMore()
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        mRootView.complete(pullToRefresh)
                    }
                })
    }

    fun cleanRecord() {
        mModel.cleanRecord()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mRootView.cleanRecordSuccess()
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                    }
                })
    }
}
