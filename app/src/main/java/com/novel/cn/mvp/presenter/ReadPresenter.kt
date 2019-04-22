package com.novel.cn.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.ReadContract
import com.novel.cn.mvp.model.entity.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


@ActivityScope
class ReadPresenter
@Inject
constructor(model: ReadContract.Model, rootView: ReadContract.View) :
        BasePresenter<ReadContract.Model, ReadContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler


    fun getVolumeList(bookId: String?) {
        mModel.getVolumeList(bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<MutableList<Volume>?>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<MutableList<Volume>?>) {
                        mRootView.showVolume(t.data)
                        getChapterList(bookId, t.data?.get(0)?.volume)
                    }
                })
    }

    fun getChapterList(bookId: String?, volume: String?) {
        val params = HashMap<String, Any?>()
        params["novelId"] = bookId
        params["volume"] = volume
        params["sort"] = "ASC"

        mModel.getChapterList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<ChapterBean>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<ChapterBean>) {
                        mRootView.showChapterList(t.data)
                    }
                })
    }
}
