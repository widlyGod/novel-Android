package com.novel.cn.mvp.presenter

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.LogUtils
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.app.Constant
import com.novel.cn.app.Preference
import com.novel.cn.ext.applySchedulers
import com.novel.cn.ext.toast
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.ReadContract
import com.novel.cn.mvp.model.entity.*
import com.novel.cn.view.readpage.CacheManager
import com.novel.cn.view.readpage.TxtChapter
import com.zchu.rxcache.data.CacheResult
import com.zchu.rxcache.kotlin.rxCache
import com.zchu.rxcache.stategy.CacheStrategy
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

    fun subscribeBook(chapterId: String, chapterTitle: String, money: String, chapter: Int, novelId: String, volumeId: String, txtChapter: TxtChapter, mCurChapterPos: Int, charge: ChargeChapter, data: ChapterInfoBean) {

        val params = HashMap<String, Any?>()
        params["chapter"] = chapter
        params["chapterTitle"] = chapterTitle
        params["chargeNumber"] = money
        params["novelChapterId"] = chapterId
        params["novelId"] = novelId
        params["novelVolumeId"] = volumeId

        mModel.subscribeBook(params)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
//                        readNovel(txtChapter, novelId, mCurChapterPos)
                        txtChapter.isFree = true
                        preDownload(txtChapter, novelId, mCurChapterPos)
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        mRootView.showChapter(data, txtChapter, mCurChapterPos, charge)
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
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        mRootView.collectionSuccess()
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        toast(t.message)
                        mRootView.collectionSuccess()
                    }
                })
    }

    fun cancelCollection(novelId: String) {

        mModel.cancelCollection(novelId)

                .applySchedulers(mRootView)
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

    fun isChargeChapter(novelId: String, volume: String?, chapterId: String, txt: TxtChapter, mCurChapterPos: Int) {
        val param = HashMap<String, Any?>()
        param["novelId"] = novelId
        param["novelVolumeId"] = volume
        param["novelChapterId"] = chapterId
        mModel.isChargeChapter(param)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<ChargeChapter>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<ChargeChapter>) {
                        if (t.code == 1) {
//                            readNovel(txt, novelId, mCurChapterPos)
                            preDownload(txt, novelId, mCurChapterPos)
                        } else {
                            getChapterInfo(txt, novelId, mCurChapterPos, t.data)
                        }
                    }
                })
    }

    fun getChapterInfo(txt: TxtChapter, novelId: String, mCurChapterPos: Int, charge: ChargeChapter) {
        mModel.getChapterInfo(txt.chapterId)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<ChapterInfoBean>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<ChapterInfoBean>) {
                        val data = t.data
                        if (charge.isSubscibe)
                            subscribeBook(data.chapterInfo.id, data.chapterInfo.title,
                                    data.chapterInfo.money.toString(), data.chapterInfo.chapter,
                                    novelId, data.chapterInfo.volumeId, txt, mCurChapterPos, charge, data)
                        else
                            mRootView.showChapter(data, txt, mCurChapterPos, charge)


                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                    }
                })
    }

    fun getCatalogue(novelId: String) {
        mModel.getCalalogue(novelId)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<CacheResult<CalalogueVo>>(mErrorHandler) {
                    override fun onNext(t: CacheResult<CalalogueVo>) {
                        val list = ArrayList<VolumeBean>()
                        t.data.catalogue.groupBy { it.volumeId }
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

    fun preDownload(txtChapter: TxtChapter?, novelId: String?, position: Int) {

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

    fun updateRead(novelId: String, chapterId: String) {
        val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
        val param = HashMap<String, Any>()
        param["userId"] = user!!.userId
        param["novelId"] = novelId
        param["chapterId"] = chapterId
        var list = ArrayList<HashMap<String, Any>>()
        list.add(param)
        mModel.updateRead(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {

                    }
                })
    }

    fun getUserAccountInfo() {
        mModel.getUserAccountInfo()
                .applySchedulers(mRootView)
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<UserAccountBean>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<UserAccountBean>) {
                        if (t.data == null)
                            mRootView.getUserAccountInfoSuccess(UserAccountBean())
                        else
                            mRootView.getUserAccountInfoSuccess(t.data)
                    }
                })
    }

    fun reward(novelId: String, operation: String, number: Int) {
        val param = HashMap<String, Any?>()
        param["novelId"] = novelId
        param["operation"] = operation
        param["number"] = number
        mModel.reward(param)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {
                        toast(t.message)
                    }
                })
    }

    fun addAutoSubscribe(novelId: String) {

        var list = ArrayList<String>()
        list.add(novelId)
        mModel.addAutoSubscribe(list)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {

                    }
                })
    }

    fun clickNum(novelId: String) {
        val param = HashMap<String, Any?>()
        val param1 = HashMap<String, Any?>()
        param1["id"] = novelId
        param["novelInfo"] = param1
        mModel.clickNum(param)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Any>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Any>) {

                    }
                })
    }

    fun getUserInfo() {
        mModel.getUserInfo()
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<User>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<User>) {
                        mRootView.showUserInfo(t.data)
                    }

                    override fun onError(t: Throwable) {
                        mRootView.showUserInfo(null)
                    }
                })
    }

}
