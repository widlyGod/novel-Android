package com.novel.cn.mvp.presenter

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.app.Constant
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.ChapterCommentContract
import com.novel.cn.mvp.model.entity.BaseResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/13/2019 10:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class ChapterCommentPresenter
@Inject
constructor(model: ChapterCommentContract.Model, rootView: ChapterCommentContract.View) :
        BasePresenter<ChapterCommentContract.Model, ChapterCommentContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler

    private var mPageIndex = 1

    fun getChapterComment(mBookId: String?, mChapterId: String?, pullToRefresh: Boolean) {
        if (pullToRefresh) mPageIndex = 1
        val params = HashMap<String, Any?>()
        params["novelId"] = mBookId
        params["chapterId"] = mChapterId
        params["pageNum"] = mPageIndex
        params["pageSise"] = Constant.PAGE_SIZE
        mModel.getChapterComment(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {

                    }
                })
    }

    fun chapterComment(mBookId: String, mChapterId: String, mVolumeId: String, content: String) {
        val params = HashMap<String, Any?>()
        params["novelId"] = mBookId
        params["chapterId"] = mChapterId
        params["volumeId"] = mChapterId
        params["content"] = mChapterId
        params["remindUid"] = null
        params["replyType"] = ""
        params["isAuthor"] = "0"
//        mModel.chapterComment(mBookId,mChapterId,mVolumeId,content,)
    }
}
