package com.novel.cn.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.db.DbManager
import com.novel.cn.db.SearchHistory
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.SearchContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.HotNovelBean
import com.novel.cn.mvp.ui.adapter.SearchRecordAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


@ActivityScope
class SearchPresenter
@Inject
constructor(model: SearchContract.Model, rootView: SearchContract.View) :
        BasePresenter<SearchContract.Model, SearchContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler


    @Inject
    lateinit var mSearchRecordAdapter: SearchRecordAdapter

    fun getHotWords() {
        mModel.getHotWords()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<HotNovelBean>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<HotNovelBean>) {

                    }
                })
    }

    fun getSearchRecordList() {
        val list = DbManager.getSearchRecordList()
        mSearchRecordAdapter.setNewData(list)
    }

    fun cleanRecord() {
        DbManager.clearSearchRecord()
        getSearchRecordList()
    }

    fun saveKeyword(keyword: String) {
        DbManager.saveSearch(keyword)
        getSearchRecordList()
    }
}