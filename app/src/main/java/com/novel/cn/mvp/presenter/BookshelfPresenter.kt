package com.novel.cn.mvp.presenter

import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.LogUtils
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.app.Constant
import com.novel.cn.app.Preference
import com.novel.cn.db.DbManager
import com.novel.cn.ext.applySchedulers
import com.novel.cn.mvp.contract.BookshelfContract
import com.novel.cn.mvp.model.entity.*
import com.novel.cn.mvp.ui.activity.LoginActivity
import com.novel.cn.view.MultiStateView
import com.zchu.rxcache.data.CacheResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import org.jetbrains.anko.startActivity
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
        val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
        if (user!!.userId.isBlank()) {
            mRootView.showState(MultiStateView.VIEW_STATE_ERROR)
            return
        }
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

    fun signIn() {
        mModel.signIn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Signinbean>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Signinbean>) {
                        mRootView.signInSuccess(t.data.readCoupon)
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

    fun getBookDetail(bookId: String, readChapterId: String, isRead: Boolean) {
        mModel.getBookDetail(bookId)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<NovelInfoBean>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<NovelInfoBean>) {
                        if (isRead) {
                            mRootView.goRead(t.data)
                        } else
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
