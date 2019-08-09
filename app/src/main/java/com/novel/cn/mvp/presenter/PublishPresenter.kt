package com.novel.cn.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.ext.applySchedulers
import com.novel.cn.ext.toast
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.PublishContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.BookInfo
import com.novel.cn.mvp.model.entity.Novel
import com.novel.cn.mvp.model.entity.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/18/2019 15:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class PublishPresenter
@Inject
constructor(model: PublishContract.Model, rootView: PublishContract.View) :
        BasePresenter<PublishContract.Model, PublishContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    fun public(type: Int, momentTitle: String, momentContent: String, address: String, fliesPath: List<String>, novelId: String, longitude: Double, latitude: Double) {
        var flies = mutableListOf<String>()
        flies.addAll(fliesPath)
        val parts = ArrayList<MultipartBody.Part>(fliesPath.size)
        if (flies[flies.lastIndex].isBlank()) {
            flies.removeAt(flies.lastIndex)
        }
        flies.forEach {
            val file = File(it)
            val requestBody = RequestBody.create("image/png".toMediaTypeOrNull(), file)
            val part = MultipartBody.Part.createFormData("file", file.name, requestBody)
            parts.add(part)
        }
        val type = MultipartBody.Part.createFormData("momentType", type.toString())
        parts.add(type)
        if (momentTitle.isNotEmpty()) {
            val momentTitle = MultipartBody.Part.createFormData("momentTitle", momentTitle)
            parts.add(momentTitle)
        }
        val momentContent = MultipartBody.Part.createFormData("momentContent", momentContent)
        parts.add(momentContent)
        val novelId = MultipartBody.Part.createFormData("novelId", novelId)
        parts.add(novelId)
        if (address.isNotEmpty()) {
            val address = MultipartBody.Part.createFormData("address", address)
            parts.add(address)
            val longitude = MultipartBody.Part.createFormData("longitude", longitude.toString())
            parts.add(longitude)
            val latitude = MultipartBody.Part.createFormData("latitude", latitude.toString())
            parts.add(latitude)
        }
        mModel.public(parts)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mRootView.publicSuccess()
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        toast(t.message)
                    }
                })
    }

    fun getHotSearch() {
        mModel.getHotSearch()
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<List<BookInfo>>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<List<BookInfo>>) {
                        mRootView.getHotSearchSuccess(t.data)
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        toast(t.message)
                    }
                })
    }

    fun getMomentNovel(novelId: String) {
        mModel.getMomentNovel(novelId)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Novel>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Novel>) {
                        mRootView.getMomentNovelSuccess(t.data)
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        toast(t.message)
                    }
                })
    }

    fun getUserInfo() {
        mModel.getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<User>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<User>) {
                        mRootView.showUserInfo(t.data)
                    }
                })
    }
}
