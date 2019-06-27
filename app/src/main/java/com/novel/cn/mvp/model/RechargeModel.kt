package com.novel.cn.mvp.model

import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.novel.cn.mvp.contract.RechargeContract
import com.novel.cn.mvp.model.api.service.BookService
import com.novel.cn.mvp.model.api.service.UserService
import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.CouponBean
import com.novel.cn.mvp.model.entity.PayInfoBean
import com.novel.cn.mvp.model.entity.User
import io.reactivex.Observable


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/10/2019 16:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
class RechargeModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), RechargeContract.Model {

    override fun getUserCoupon(params: HashMap<String, String>): Observable<BaseResponse<List<CouponBean>>> {
        return mRepositoryManager.obtainRetrofitService(UserService::class.java).getUserCoupon(params)
    }

    override fun getUserInfo(): Observable<BaseResponse<User>> {
        return mRepositoryManager.obtainRetrofitService(UserService::class.java).getUserInfo()
    }

    override fun recharge(params: HashMap<String, String>): Observable<BaseResponse<PayInfoBean>> {
        return mRepositoryManager.obtainRetrofitService(BookService::class.java).recharge(params)
    }



}
