package com.novel.cn.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.novel.cn.ext.applySchedulers
import com.novel.cn.ext.toast
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.PublishContract
import com.novel.cn.mvp.model.entity.BaseResponse
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import okhttp3.MediaType
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

    fun public(type: Int, momentTitle: String, momentContent: String, address: String, fliesPath: List<String>) {
        var flies = mutableListOf<String>()
        flies.addAll(fliesPath)
        val parts = ArrayList<MultipartBody.Part>(fliesPath.size)
        if(flies[flies.lastIndex].isBlank()){
            flies.removeAt(flies.lastIndex)
        }
        flies.forEach {
            val file = File(it)
            val requestBody = RequestBody.create(MediaType.parse("image/png"), file)
            val part = MultipartBody.Part.createFormData("file", file.name, requestBody)
            parts.add(part)
        }
        val type = MultipartBody.Part.createFormData("momentType", type.toString())
        parts.add(type)
        val momentTitle = MultipartBody.Part.createFormData("momentTitle",momentTitle)
        parts.add(momentTitle)
        val momentContent = MultipartBody.Part.createFormData("momentContent",momentContent)
        parts.add(momentContent)
        val address = MultipartBody.Part.createFormData("address","深圳")
        parts.add(address)
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

}
