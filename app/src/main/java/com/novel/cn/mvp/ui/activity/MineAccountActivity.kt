package com.novel.cn.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import com.jakewharton.rxbinding3.view.clicks

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.novel.cn.di.component.DaggerMineAccountComponent
import com.novel.cn.di.module.MineAccountModule
import com.novel.cn.mvp.contract.MineAccountContract
import com.novel.cn.mvp.presenter.MineAccountPresenter

import com.novel.cn.R
import com.novel.cn.app.JumpManager
import com.novel.cn.ext.bindToLifecycle
import com.novel.cn.mvp.model.entity.MyAccountBean
import com.novel.cn.mvp.model.entity.UserAccountBean
import com.novel.cn.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_mine_account.*
import kotlinx.android.synthetic.main.include_title.*
import org.jetbrains.anko.startActivity


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/06/2019 15:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
/**
 * 如果没presenter
 * 你可以这样写
 *
 * @ActivityScope(請注意命名空間) class NullObjectPresenterByActivity
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */
class MineAccountActivity : BaseActivity<MineAccountPresenter>(), MineAccountContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerMineAccountComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mineAccountModule(MineAccountModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_mine_account //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun getUserAccountInfoSuccess(userAccountBean: MyAccountBean) {
        tv_user_money.text = userAccountBean.recargaGoldNumber.ifEmpty { "0" }
        tv_user_prendar.text = userAccountBean.prendarGoldNumber.ifEmpty { "0" }
        tv_discount_num.text = userAccountBean.discountCoupon.ifEmpty { "0" }
        tv_coupon_num.text = userAccountBean.coupon.ifEmpty { "0" }
        if (userAccountBean.isVip == "1") {
            tv_is_vip.text = "${userAccountBean.expireTime}到期"
            monthly_payment.text = "续费"
        } else tv_is_vip.text = "未开通"
        tv_experience_num.text = userAccountBean.experienceCard.ifEmpty { "0" }
        tv_recommended_num.text = userAccountBean.recommendedVotes.ifEmpty { "0" }
        tv_monthly_num.text = userAccountBean.monthlyPass.ifEmpty { "0" }
    }

    override fun initStatusBar(savedInstanceState: Bundle?) {
        //白底黑字
        StatusBarUtils.darkMode(this)
        //给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, toolbar)
    }


    override fun initData(savedInstanceState: Bundle?) {
        mPresenter?.getUserAccountInfo()
        tv_recharge.clicks().subscribe {
            JumpManager.jumpRecharge(this)
        }.bindToLifecycle(this)
        rl_mine_buy.clicks().subscribe {
            startActivity<MineBillActivity>()
        }.bindToLifecycle(this)
    }


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        finish()
    }
}
