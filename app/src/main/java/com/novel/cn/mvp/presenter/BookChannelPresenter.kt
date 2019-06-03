package com.novel.cn.mvp.presenter

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.google.gson.Gson

import com.jess.arms.di.scope.FragmentScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.utils.LogUtils
import com.jess.arms.utils.RxLifecycleUtils
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.BookChannelContract
import com.novel.cn.mvp.model.entity.*
import com.novel.cn.mvp.ui.adapter.BookChannelAdapter
import com.youth.banner.Banner
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber
import com.google.gson.reflect.TypeToken
import com.novel.cn.view.MultiStateView


@FragmentScope
class BookChannelPresenter
@Inject
constructor(model: BookChannelContract.Model, rootView: BookChannelContract.View) :
        BasePresenter<BookChannelContract.Model, BookChannelContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mAdapter: BookChannelAdapter

    @Inject
    lateinit var mGson: Gson


    fun getChannel(type: Int) {
        mModel.getChannelData(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<Map<String, Any>>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<Map<String, Any>>) {
                        mRootView.changeState(MultiStateView.VIEW_STATE_CONTENT)
                        val data = t.data
                        val list = ArrayList<MultiItemEntity>()

                        val iterator = data.iterator()
                        //服务器返回的不同类型的 value，所以需要map遍历解析
                        while (iterator.hasNext()) {
                            val next = iterator.next()
                            when (next.key) {

                                "1" -> {
                                    val map = next.value as Map<String, Any>
                                    val bannerList = mGson.fromJson<List<BannerInfo>>(mGson.toJson(map["recommendResultList"]), object : TypeToken<List<BannerInfo>>() {}.type)
                                    list.add(BannerBean(bannerList))
                                    //菜单
                                    list.add(MenuBean())
                                }
                                "2" -> {
                                    val map = next.value as Map<*, *>
                                    val hotList = mGson.fromJson<List<BookInfo>>(mGson.toJson(map["hotNovelInfoList"]), object : TypeToken<List<BookInfo>>() {}.type)
                                    if (hotList != null) {
                                        list.add(SearchBean(hotList))
                                    }
                                }
                                "3" -> {
                                    val json = mGson.toJson(next.value)
                                    val bookInfoList = mGson.fromJson<List<BookInfoBean>>(json, object : TypeToken<List<BookInfoBean>>() {}.type)

                                    bookInfoList.forEach { it ->
                                        //UI 展示样式，1：表示 展示六本 2：表示 左右滑动
                                        if (it.uIStyle == 1) {
//                                            list.add(TitleIndicator(it.selectLabelName))
//
//                                            it.novelList.forEach {
//                                                list.add(it)
//                                            }
                                            list.add(SwitchoverBooks(it))
                                        } else {
                                            list.add(BookHorizontal(it.novelList))
                                        }
                                    }
                                }
                                "4" -> {
                                    val json = mGson.toJson((next.value as Map<*, *>)["pageInfoList"])
                                    val lateyList = mGson.fromJson<List<BookInfo>>(json, object : TypeToken<List<BookInfo>>() {}.type)
                                    if (lateyList.size > 0) {
                                        list.add(TitleIndicator("最近更新"))
                                        lateyList.forEach {
                                            it.itemType2 = BookChannelAdapter.TYPE_LATEY_UPDATE
                                            list.add(it)
                                        }
                                    }
                                }
                            }
                        }

                        list.add(MultiItemEntity { 7 })
                        mAdapter.setNewData(list)
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        mRootView.changeState(MultiStateView.VIEW_STATE_ERROR)
                    }
                })
    }
}
