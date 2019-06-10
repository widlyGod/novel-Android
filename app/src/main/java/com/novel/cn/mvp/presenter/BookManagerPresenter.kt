package com.novel.cn.mvp.presenter

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.app.Constant
import com.novel.cn.ext.applySchedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.BookManagerContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.Book
import com.novel.cn.mvp.model.entity.Pagination
import com.novel.cn.mvp.ui.adapter.BookManagerAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import java.util.*
import kotlin.collections.HashMap


@ActivityScope
class BookManagerPresenter
@Inject
constructor(model: BookManagerContract.Model, rootView: BookManagerContract.View) :
        BasePresenter<BookManagerContract.Model, BookManagerContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler

    @Inject
    lateinit var mAdapter: BookManagerAdapter

    private var mPageIndex = 1

    fun getBookList(pullToRefresh: Boolean, type: Int) {
        if (pullToRefresh) mPageIndex = 1

        val params = HashMap<String, String>()
        params.put("pageNum", mPageIndex.toString())
        params.put("pageSize", Constant.PAGE_SIZE.toString())
        mModel.getBookList(params, type)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Pagination<Book>>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Pagination<Book>>) {
                        //判断是否还有下一页
                        val noMore = mPageIndex * Constant.PAGE_SIZE >= t.data.total

                        if (pullToRefresh) {
                            mAdapter.replaceData(t.data.book)
                        } else {
                            mAdapter.addData(t.data.book)
                            if (!noMore)
                                mAdapter.loadMoreComplete()
                        }
                        if (noMore)
                            mAdapter.loadMoreEnd()

                        //请求成功后，当前页改变
                        mPageIndex++
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        mAdapter.loadMoreComplete()
                    }
                })
    }

    fun deleteBook(checkList: LinkedList<String>, type: Int) {
//        val params = HashMap<String, LinkedList<String>>()
//        params.put("novelIds",checkList)
        mModel.deleteBook(checkList, type)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mRootView.deleteSuccess()
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                    }

                })

    }

    fun moveBook(map: HashMap<String, Int>) {
        val params = HashMap<String, HashMap<String, Int>>()
        params["moveNovelMap"] = map
        mModel.moveBook(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mRootView.moveSuccess()
                    }

                })

    }

}
