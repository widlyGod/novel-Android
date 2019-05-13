package com.novel.cn.mvp.presenter

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.RxLifecycleUtils
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.ReadContract
import com.novel.cn.mvp.model.entity.*
import com.novel.cn.view.readpage.CacheManager
import com.novel.cn.view.readpage.TxtChapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


@ActivityScope
class ReadPresenter
@Inject
constructor(model: ReadContract.Model, rootView: ReadContract.View) :
        BasePresenter<ReadContract.Model, ReadContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler


    fun getVolumeList(bookId: String?) {
        mModel.getVolumeList(bookId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<MutableList<Volume>?>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<MutableList<Volume>?>) {
                        if (!t.data.isNullOrEmpty()) {
                            mRootView.showVolume(t.data)
                            getChapterList(bookId, t.data.get(0).volume)
                        }
                    }
                })
    }

    fun getChapterList(bookId: String?, volume: String?) {
        val params = HashMap<String, Any?>()
        params["novelId"] = bookId
        params["volume"] = volume
        params["sort"] = "ASC"

        mModel.getChapterList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<ChapterBean>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<ChapterBean>) {
                        mRootView.showChapterList(volume, t.data)
                    }
                })
    }

    fun readNovel(requestChapters: MutableList<TxtChapter>?, bookId: String?) {

        requestChapters?.let {
            val size = it.size

            val chapterInfoBean = ArrayList<Observable<BaseResponse<ChapterInfoBean>>>(size)
            for (i in 0 until size) {
                val params = HashMap<String, Any?>()
                val child = HashMap<String, Any?>()
                child["id"] = it.getOrNull(i)?.link
                child["uniqueId"] = "-1"
                params["chapterInfo"] = child
                chapterInfoBean.add(mModel.readNovel(params))
            }

            Observable.concat(chapterInfoBean)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                    .subscribe(object : ErrorHandleSubscriber<BaseResponse<ChapterInfoBean>>(mErrorHandler) {
                        override fun onNext(t: BaseResponse<ChapterInfoBean>) {
                            val data = t.data
                            CacheManager.getInstance().saveChapterInfo(bookId, data.chapterInfo.id, data.chapterInfo.content)
                            mRootView.loadChapterSuccess(data.chapterInfo)
                        }
                    })
        }
    }

    fun addCollection(novelId: String) {
        val params = HashMap<String, Any>()
        params["novel_id"] = novelId

        mModel.addConllection(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mRootView.collectionSuccess()
                    }
                })
    }

    fun cancelCollection(novelId: String) {
        val params = HashMap<String, Any>()
        params["novel_id"] = novelId

        mModel.cancelCollection(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mRootView.cancelCollection()
                    }
                })
    }

    fun readNovel(txtChapter: TxtChapter?, novelId: String, mCurChapterPos: Int) {
        val params = HashMap<String, Any?>()
        val child = HashMap<String, Any?>()
        child["id"] = txtChapter?.link
        child["uniqueId"] = "-1"
        params["chapterInfo"] = child
        mModel.readNovel(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<ChapterInfoBean>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<ChapterInfoBean>) {
                        val data = t.data
                        CacheManager.getInstance().saveChapterInfo(novelId, data.chapterInfo.id, data.chapterInfo.content)
                        mRootView.showChapter(data,txtChapter,mCurChapterPos)
                    }
                })
    }

    fun readNovel2(txtChapter: TxtChapter?, novelId: String) {
        val data = ArrayList<TxtChapter>()
        data.add(txtChapter!!)
        readNovel(data, novelId)
    }
}
