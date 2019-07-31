package com.novel.cn.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.app.Constant
import com.novel.cn.ext.applySchedulers
import com.novel.cn.ext.toast
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.CircleCommentContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.Circle
import com.novel.cn.mvp.model.entity.CircleBean
import com.novel.cn.mvp.model.entity.CircleCommentBean
import com.novel.cn.mvp.ui.adapter.CircleCommentAdapter
import com.novel.cn.view.MultiStateView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/29/2019 11:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class CircleCommentPresenter
@Inject
constructor(model: CircleCommentContract.Model, rootView: CircleCommentContract.View) :
        BasePresenter<CircleCommentContract.Model, CircleCommentContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    private var mPageIndex = 1

    @Inject
    lateinit var mCircleCommentAdapter: CircleCommentAdapter

    fun getComments(momentId: String, pullToRefresh: Boolean = true) {
        if (pullToRefresh) mPageIndex = 1

        val params = HashMap<String, String>()
        params["pageNum"] = mPageIndex.toString()
        params["pageSize"] = Constant.PAGE_SIZE.toString()
        params["momentId"] = momentId
        mModel.getComments(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<CircleCommentBean>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<CircleCommentBean>) {
                        if (t.data.totalElements == 0) {
                        } else {
                            val noMore = t.data.totalPages <= mPageIndex

                            if (pullToRefresh) {
                                mCircleCommentAdapter.setNewData(t.data.content)
                            } else {
                                mCircleCommentAdapter.addData(t.data.content)
                                mCircleCommentAdapter.loadMoreComplete()
                            }
                            if (noMore)
                                mCircleCommentAdapter.loadMoreEnd()

                            mPageIndex++
                        }
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                    }
                })
    }

    fun getMomentDetail(momentId: String) {
        mModel.getMomentDetail(momentId)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Circle>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Circle>) {
                        mRootView.getMomentDetailSuccess(t.data)
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        toast(t.message)
                    }
                })
    }

    fun agree(momentId: String) {
        mModel.agree(momentId)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mRootView.agreeSuccess()
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        toast(t.message)
                    }
                })
    }

    fun chapter(momentId: String, commentContent: String) {
        val params = HashMap<String, Any?>()
        params["momentId"] = momentId
        params["commentContent"] = commentContent
        mModel.chapterComment(params)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mRootView.chapterCommentSuccess()
                        toast("评论成功")
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        toast(t.message)
                    }
                })
    }

    fun agreeComment(position: Int) {
        val item = mCircleCommentAdapter.getItem(position)
        mModel.agreeReply(item?.commentId!!)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        item.hadThumbed = true
                        item.thumbNum++
                        mCircleCommentAdapter.notifyDataSetChanged()
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        toast(t.message)
                    }
                })
    }

    fun chapterComment(momentId: String, commentContent: String,toReplyUserId:String) {
        val params = HashMap<String, Any?>()
        params["commentId"] = momentId
        params["replyContent"] = commentContent
        params["toReplyUserId"] = toReplyUserId
        mModel.chapterCommentReply(params)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mRootView.chapterCommentSuccess()
                        toast("评论成功")
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        toast(t.message)
                    }
                })
    }
}
