package com.novel.cn.mvp.model

import android.app.Application
import io.reactivex.Observable
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.novel.cn.mvp.contract.VipContract
import com.novel.cn.mvp.model.api.service.UserService
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.CouponBean
import com.novel.cn.mvp.model.entity.PayInfoBean


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
class VipModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), VipContract.Model {

    @Inject
    lateinit var mGson: Gson;
    @Inject
    lateinit var mApplication: Application;

    override fun getUserCoupon(params: HashMap<String, String>): Observable<BaseResponse<List<CouponBean>>> {
        return mRepositoryManager.obtainRetrofitService(UserService::class.java).getUserCoupon(params)
    }

    override fun vipPay(params: HashMap<String, String>): Observable<BaseResponse<PayInfoBean>> {
        return mRepositoryManager.obtainRetrofitService(UserService::class.java).vipPay(params)
    }

}
