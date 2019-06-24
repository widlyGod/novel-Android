package com.novel.cn.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.ContentsContract
import com.novel.cn.mvp.model.entity.*
import com.novel.cn.view.MultiStateView
import com.zchu.rxcache.data.CacheResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


@ActivityScope
class ContentsPresenter
@Inject
constructor(model: ContentsContract.Model, rootView: ContentsContract.View) :
        BasePresenter<ContentsContract.Model, ContentsContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler


//    fun getVolumeList(bookId: String?) {
//        mModel.getVolumeList(bookId)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
//                .subscribe(object : ErrorHandleSubscriber<BaseResponse<MutableList<Volume>?>>(mErrorHandler) {
//                    override fun onNext(t: BaseResponse<MutableList<Volume>?>) {
//                        if (!t.data.isNullOrEmpty()) {
//                            mRootView.showVolume(t.data)
//                            getChapterList(bookId, t.data[0].volume)
//                        } else {
//                            mRootView.showState(MultiStateView.VIEW_STATE_EMPTY)
//                        }
//
//                    }
//
//                    override fun onError(t: Throwable) {
//                        super.onError(t)
//                        mRootView.showState(MultiStateView.VIEW_STATE_ERROR)
//                    }
//                })
//    }
//
//    fun getChapterList(bookId: String?, volume: String?) {
//        val params = HashMap<String, Any?>()
//        params["novelId"] = bookId
//        params["volume"] = volume
//        params["sort"] = "ASC"
//
//        mModel.getChapterList(params)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
//                .subscribe(object : ErrorHandleSubscriber<BaseResponse<ChapterBean>>(mErrorHandler) {
//                    override fun onNext(t: BaseResponse<ChapterBean>) {
//                        mRootView.showState(MultiStateView.VIEW_STATE_CONTENT)
//                        mRootView.showChapterList(volume, t.data)
//
//                    }
//
//                    override fun onError(t: Throwable) {
//                        super.onError(t)
//                        mRootView.showState(MultiStateView.VIEW_STATE_ERROR)
//                    }
//                })
//    }

    fun getCatalogue(novelId: String) {
        mModel.getCalalogue(novelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<CacheResult<CalalogueVo>>(mErrorHandler) {
                    override fun onNext(t: CacheResult<CalalogueVo>) {
                        val list = ArrayList<VolumeBean>()
                        t.data.catalogue.groupBy { it.volumeId }
                                .forEach {
                                    val value = it.value
                                    val volumeBean = VolumeBean(value[0].volumeId, value[0].volumeTitle, value)
                                    list.add(volumeBean)
                                }
                        mRootView.showCalalogueInfo(list)
                    }
                })
    }
}
