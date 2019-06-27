package com.novel.cn.mvp.contract

import com.jess.arms.mvp.IView
import com.jess.arms.mvp.IModel
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
interface RechargeContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun showRechargeInfo(data: String, code: String)
        fun showUserInfo(data: User)
        fun getUserCouponSuccess(list: List<CouponBean>)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model : IModel {
        fun recharge(params: HashMap<String, String>): Observable<BaseResponse<PayInfoBean>>
        fun getUserInfo(): Observable<BaseResponse<User>>
        fun getUserCoupon(params: HashMap<String, String>): Observable<BaseResponse<List<CouponBean>>>
    }

}
