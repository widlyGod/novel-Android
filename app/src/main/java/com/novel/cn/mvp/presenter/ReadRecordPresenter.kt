package com.novel.cn.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.app.Constant
import com.novel.cn.app.JumpManager
import com.novel.cn.db.DbManager
import com.novel.cn.ext.applySchedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.ReadRecordContract
import com.novel.cn.mvp.model.entity.*
import com.novel.cn.view.MultiStateView
import com.zchu.rxcache.data.CacheResult
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
        else
            mPageIndex++

        mModel.getReadRecordList(mPageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Pagination<Book>>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Pagination<Book>>) {
                        if (t.data.total == 0) {
                            mRootView.showState(MultiStateView.VIEW_STATE_EMPTY)
                        } else {
                            mRootView.showState(MultiStateView.VIEW_STATE_CONTENT)
                            //判断是否还有下一页
                            val noMore = mPageIndex * Constant.PAGE_SIZE >= t.data.total
                            mRootView.showReadRecordList(pullToRefresh, t.data.book)
                            mRootView.complete(pullToRefresh)
                            if (noMore)
                                mRootView.noMore()
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

    fun getBookDetail(bookId: String, readChapterId: String) {
        mModel.getBookDetail(bookId)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<NovelInfoBean>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<NovelInfoBean>) {
                        getCatalogue(bookId, readChapterId, t.data)
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                    }
                })
    }

    fun getCatalogue(novelId: String, readChapterId: String, novelInfoBean: NovelInfoBean) {
        mModel.getCalalogue(novelId)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<CacheResult<CalalogueVo>>(mErrorHandler) {
                    override fun onNext(t: CacheResult<CalalogueVo>) {
                        val list = ArrayList<VolumeBean>()
                        var mCurChapterPos = 0
                        var volumePos = 0
                        t.data.catalogue.groupBy { it.volumeId }
                                .forEach {
                                    val value = it.value
                                    for (calalogue in value) {
                                        if (calalogue.chapterId == readChapterId) {
                                            val mBookRecord = DbManager.getReadcord(novelId)!!
                                            mBookRecord.bookId = novelId
                                            mBookRecord.chapter = mCurChapterPos
                                            mBookRecord.volumePos = volumePos
                                            mBookRecord.pagePos = 0
                                            DbManager.saveRecord(mBookRecord)
                                            mRootView.goRead(novelInfoBean)
                                        }
                                        mCurChapterPos++
                                    }
                                    volumePos++

                                }

                    }
                })
    }
}
