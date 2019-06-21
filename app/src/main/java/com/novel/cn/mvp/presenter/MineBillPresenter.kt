package com.novel.cn.mvp.presenter

import android.app.Application

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.app.Constant
import com.novel.cn.app.Preference
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.MineBillContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.LoginInfo
import com.novel.cn.mvp.model.entity.MessageBean
import com.novel.cn.mvp.model.entity.MyBillBean
import com.novel.cn.mvp.ui.adapter.MyBillAdapter
import com.novel.cn.view.MultiStateView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/19/2019 11:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class MineBillPresenter
@Inject
constructor(model: MineBillContract.Model, rootView: MineBillContract.View) :
        BasePresenter<MineBillContract.Model, MineBillContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager

    fun getMyBill(startDate: String, endDate: String, type: Int) {

        val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
        val params = HashMap<String, Any>()
        params["startDate"] = startDate
        params["endDate"] = endDate
        params["type"] = type
        params["userId"] = user!!.userId

        mModel.getMyBill(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<MyBillBean>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<MyBillBean>) {
                        mRootView.showStateView(if (t.data.myOrders.isNotEmpty()) MultiStateView.VIEW_STATE_CONTENT else MultiStateView.VIEW_STATE_EMPTY)
                        mRootView.getMyBillSuccess(t.data)
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                        mRootView.showStateView(MultiStateView.VIEW_STATE_ERROR)
                    }
                })
    }


}
