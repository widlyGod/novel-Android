package com.novel.cn.mvp.presenter

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils

import com.novel.cn.app.Constant
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.MessageContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.Message
import com.novel.cn.mvp.model.entity.MessageBean
import com.novel.cn.mvp.ui.adapter.MessageAdapter
import com.novel.cn.mvp.ui.adapter.MessageFilterAdapter
import com.novel.cn.view.MultiStateView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


@ActivityScope
class MessagePresenter
@Inject
constructor(model: MessageContract.Model, rootView: MessageContract.View) :
        BasePresenter<MessageContract.Model, MessageContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler

    @Inject
    lateinit var mAdapter: MessageAdapter

    @Inject
    lateinit var mFilterAdapter: MessageFilterAdapter

    private var mPageIndex = 1


    fun getMessageList(pullToRefresh: Boolean, keyword: String?) {
        if (pullToRefresh) mPageIndex = 1
        val params = HashMap<String, String>()
        params.put("pageNum", mPageIndex.toString())
        params.put("pageSize", Constant.PAGE_SIZE.toString())
        params.put("keyWord", keyword ?: "")
        messageRead()

        mModel.getMessageList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<MessageBean>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<MessageBean>) {
                        //判断是否还有下一页
                        val noMore = mPageIndex * Constant.PAGE_SIZE >= t.data.total
                        mRootView.showStateView(if (t.data.total > 0) MultiStateView.VIEW_STATE_CONTENT else MultiStateView.VIEW_STATE_EMPTY)
                        if (pullToRefresh) {
                            mAdapter.replaceData(t.data.list)
                            mRootView.refreshComplete()
                        } else {
                            mAdapter.addData(t.data.list)
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
                        mRootView.showStateView(MultiStateView.VIEW_STATE_ERROR)
                    }
                })
    }

    fun messageRead() {

        mModel.messageRead()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {

                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)

                    }
                })
    }



    fun getFilterList() {
        val list = ArrayList<String>()
        list.add("全部")
        list.add("书评回复")
        list.add("连载更新")
        list.add("书籍评论")
        list.add("稿费通知")
        list.add("充值通知")
        list.add("审核结果")
        list.add("审核通知")
        list.add("推荐票")
        list.add("钻石")
        list.add("月票")
        mFilterAdapter.setNewData(list)
    }
}
