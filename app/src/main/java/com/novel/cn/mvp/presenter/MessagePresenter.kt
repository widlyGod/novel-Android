package com.novel.cn.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.MessageContract
import com.novel.cn.mvp.model.entity.Message
import com.novel.cn.mvp.ui.adapter.MessageAdapter


@ActivityScope
class MessagePresenter
@Inject
constructor(model: MessageContract.Model, rootView: MessageContract.View) :
        BasePresenter<MessageContract.Model, MessageContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAdapter: MessageAdapter


    override fun onDestroy() {
        super.onDestroy();
    }

    fun getMessageList() {
        val list = ArrayList<Message>()

        for (i in 0..14){
            list.add(Message("",
                    "",
                    "","",0,"","","",i))
        }
        mAdapter.setNewData(list)
    }
}
