package com.novel.cn.mvp.presenter

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.LogUtils
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
import okhttp3.ResponseBody
import java.nio.charset.Charset


@ActivityScope
class ReadPresenter
@Inject
constructor(model: ReadContract.Model, rootView: ReadContract.View) :
        BasePresenter<ReadContract.Model, ReadContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler

    fun subscribeBook(chapterId: String, chapterTitle: String, money: String, chapter: Int, novelId: String, volumeId: String, txtChapter: TxtChapter?, mCurChapterPos: Int) {

        val params = HashMap<String, Any?>()
        params["chapter"] = chapter
        params["chapterTitle"] = chapterTitle
        params["chargeNumber"] = money
        params["novelChapterId"] = chapterId
        params["novelId"] = novelId
        params["novelVolumeId"] = volumeId

        mModel.subscribeBook(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
//                        readNovel(txtChapter, novelId, mCurChapterPos)
                        txtChapter?.isFree = true
                        preDownload(txtChapter,novelId,mCurChapterPos)
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        mRootView.subscribeError()
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
                            mRootView.loadChapterSuccess()
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
                        mRootView.openBook(mCurChapterPos, txtChapter)
                    }
                })
    }

    fun isChargeChapter(novelId: String, volume: String?, txt: TxtChapter, mCurChapterPos: Int) {
        val param = HashMap<String, Any?>()
        param["novelId"] = novelId
        param["novelVolumeId"] = volume
        param["novelChapterId"] = txt.link
        mModel.isChargeChapter(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<ChargeChapter>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<ChargeChapter>) {
                        if (t.data.isSubscibe) {
                            readNovel(txt, novelId, mCurChapterPos)
                        } else {
                            getChapterInfo(txt, novelId, mCurChapterPos, t.data)
                        }
                    }
                })
    }

    fun getChapterInfo(txt: TxtChapter, novelId: String, mCurChapterPos: Int, charge: ChargeChapter) {
        mModel.getChapterInfo(txt.chapterId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<ChapterInfoBean>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<ChapterInfoBean>) {
                        val data = t.data
                        mRootView.showChapter(data, txt, mCurChapterPos, charge)
                    }
                })
    }

    fun getCatalogue(novelId: String) {
        mModel.getCalalogue(novelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<MutableList<Calalogue>>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<MutableList<Calalogue>>) {
                        val list = ArrayList<VolumeBean>()
                        t.data.groupBy { it.volumeId }
                                .forEach {
                                    val value = it.value
                                    val volumeBean = VolumeBean(value[0].volumeId, value[0].volumeTitle, value)
                                    list.add(volumeBean)
                                }
                        mRootView.showCalalogueInfo(list)
                    }
                })
    }

    fun preDownload(requestChapters: MutableList<TxtChapter>?) {

        val url = requestChapters!![0].filePath

        mModel.preDownload(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<ResponseBody>(mErrorHandler) {
                    override fun onNext(t: ResponseBody) {
                        CacheManager.getInstance().saveChapterInfo(requestChapters[0].bookId, requestChapters[0].filePath, t)
                        mRootView.loadChapterSuccess()
                    }
                })
    }
    fun preDownload(txtChapter: TxtChapter?,novelId: String?,position:Int) {

        val url = txtChapter?.filePath

        mModel.preDownload(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<ResponseBody>(mErrorHandler) {
                    override fun onNext(t: ResponseBody) {

                        CacheManager.getInstance().saveChapterInfo(txtChapter?.bookId, txtChapter?.filePath, t)
                        mRootView.openBook(position, txtChapter)
                    }
                })
    }


}
