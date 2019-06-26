package com.novel.cn.mvp.presenter

import android.app.Application
import com.google.gson.Gson

import com.jess.arms.integration.AppManager
import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.mvp.BasePresenter
import com.jess.arms.http.imageloader.ImageLoader
import com.jess.arms.utils.RxLifecycleUtils
import com.novel.cn.ext.applySchedulers
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import javax.inject.Inject

import com.novel.cn.mvp.contract.VipContract
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.CouponBean
import com.novel.cn.mvp.model.entity.PayInfoBean
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/24/2019 18:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class VipPresenter
@Inject
constructor(model: VipContract.Model, rootView: VipContract.View) :
        BasePresenter<VipContract.Model, VipContract.View>(model, rootView) {
    @Inject
    lateinit var mErrorHandler: RxErrorHandler
    @Inject
    lateinit var mApplication: Application
    @Inject
    lateinit var mImageLoader: ImageLoader
    @Inject
    lateinit var mAppManager: AppManager
    @Inject
    lateinit var mGson: Gson

    fun getUserCoupon() {
        val params = HashMap<String, String>()
        params["selectType"] = "0"
        params["orderAmount"] = "0"
        mModel.getUserCoupon(params)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<List<CouponBean>>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<List<CouponBean>>) {
                        if (t.data != null)
                            mRootView.getUserCouponSuccess(t.data)
                        else
                            mRootView.getUserCouponSuccess(mutableListOf<CouponBean>())
                    }
                })
    }

    fun vipPay(vipType: Int, code: String, couponId: String) {
        val params = HashMap<String, String>()
        params["vipType"] = "$vipType"
        params["autoRenew"] = "0"
        params["rechargeCode"] = code
        params["requestCode"] = "3"
        if (couponId.isEmpty())
            params["useCoupon"] = "0"
        else
            params["useCoupon"] = "1"
        params["couponId"] = couponId
        mModel.vipPay(params)
                .applySchedulers(mRootView)
                .subscribe(object : ErrorHandleSubscriber<BaseResponse<PayInfoBean>>(mErrorHandler) {
                    override fun onNext(t: BaseResponse<PayInfoBean>) {
                        val data = mGson.toJson(t.data.payCode)
                        if (code == "1") {
                            mRootView.showRechargeInfo(t.data.payCode.toString(), code)
                        } else {
                            mRootView.showRechargeInfo(data, code)
                        }
                    }

                    override fun onError(t: Throwable) {
                        super.onError(t)
                    }
                })
    }

}
