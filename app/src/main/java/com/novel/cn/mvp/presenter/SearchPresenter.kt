package com.novel.cn.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.app.Constant
import com.novel.cn.db.DbManager
import com.novel.cn.db.SearchHistory
import com.novel.cn.ext.applySchedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.SearchContract
import com.novel.cn.mvp.model.entity.*
import com.novel.cn.mvp.ui.adapter.SearchRecordAdapter
import com.novel.cn.view.MultiStateView
import com.zchu.rxcache.data.CacheResult
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

    private var mPageIndex = 1

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

    fun getSearchResult(param: String, pullToRefresh: Boolean = true) {
        if (pullToRefresh) mPageIndex = 1

        mModel.getSearchResult(param, mPageIndex)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<SearchResultBean>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<SearchResultBean>) {
                        if (t.data.novelInfos.total == 0) {
                            mRootView.showState(MultiStateView.VIEW_STATE_EMPTY)
                        } else {
                            mRootView.showState(MultiStateView.VIEW_STATE_CONTENT)
                            //判断是否还有下一页
                            val noMore = mPageIndex * Constant.PAGE_SIZE >= t.data.novelInfos.total
                            mRootView.showsearchResult(pullToRefresh, t.data.novelInfos.list)
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
