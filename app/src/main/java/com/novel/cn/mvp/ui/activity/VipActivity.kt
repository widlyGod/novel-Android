package com.novel.cn.mvp.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.view.View
import com.alipay.sdk.app.EnvUtils

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.novel.cn.BuildConfig

import com.novel.cn.di.component.DaggerVipComponent
import com.novel.cn.di.module.VipModule
import com.novel.cn.mvp.contract.VipContract
import com.novel.cn.mvp.presenter.VipPresenter

import com.novel.cn.R
import com.novel.cn.app.JumpManager
import com.novel.cn.app.click
import com.novel.cn.app.loadHeadImage
import com.novel.cn.app.visible
import com.novel.cn.ext.getCompactDrawable
import com.novel.cn.mvp.model.entity.CouponBean
import com.novel.cn.mvp.model.entity.User
import com.novel.cn.mvp.model.entity.VipInfo
import com.novel.cn.mvp.ui.dialog.EconomizeDialog
import com.novel.cn.mvp.ui.dialog.SelectCouponPopup
import com.novel.cn.mvp.ui.dialog.VipTimeoutDialog
import com.novel.cn.mvp.ui.fragment.VipPowerFragment
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.utils.pay.Alipay
import com.novel.cn.utils.pay.Result
import com.novel.cn.view.SelectCoupon
import com.novel.cn.wxapi.WechatSdk
import com.tencent.mm.opensdk.modelbase.BaseResp
import kotlinx.android.synthetic.main.activity_vip.*
import kotlinx.android.synthetic.main.activity_vip.iv_avatar
import kotlinx.android.synthetic.main.fragment_my.*
import kotlinx.android.synthetic.main.include_title.*
import kotlinx.android.synthetic.main.item_comment.view.*
import kotlinx.android.synthetic.main.layout_my_header.*
import net.lucode.hackware.magicindicator.ViewPagerHelper
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import razerdp.basepopup.BasePopupWindow
import razerdp.blur.PopupBlurOption


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
class VipActivity : BaseActivity<VipPresenter>(), VipContract.View, SelectCoupon {

    private val mUser by lazy { intent.getParcelableExtra<User?>("user") }
    private val isTime by lazy { intent.getBooleanExtra("isTime",false) }

    private val mWechatSdk by lazy { WechatSdk(this, BuildConfig.APP_ID_WECHAT); }


    override fun getUserCouponSuccess(list: List<CouponBean>) {
        var couponList = mutableListOf<CouponBean>()
        couponList.add(CouponBean())
        couponList.addAll(list)
        couponList.forEach {
            it.isSelect = selectedCouponId == it.id
        }
        mSelectCouponPopup = SelectCouponPopup(this, couponList, this)
        mSelectCouponPopup.onBeforeShowCallback = BasePopupWindow.OnBeforeShowCallback { popupRootView, anchorView, hasShowAnima ->
            mSelectCouponPopup.setBlurBackgroundEnable(true)
            true
        }
        mSelectCouponPopup.showPopupWindow()
    }

    private val mViewPager by lazy { vp_rank_reputation }
    private lateinit var mSelectCouponPopup: SelectCouponPopup
    private var selectedCouponId: String = ""

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerVipComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .vipModule(VipModule(this))
                .build()
                .inject(this)
    }

    var rechargeVipType = 0
    var payType = -1

    override fun initStatusBar(savedInstanceState: Bundle?) {
        //白底黑字
        StatusBarUtils.immersive(this)
//        给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, ll_vip_main)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_vip //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {
        //支付宝沙盒环境
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX)
        if(isTime)
            VipTimeoutDialog(this).show()
        initViewPager()
        iv_avatar.loadHeadImage(mUser?.userPhoto)
        tv_user_name.text = mUser?.userNickName
        if (mUser!!.vipInfo.isVip == 0) {
            vip_monthly_title.visible(true)
            iv_vip_text.visible(false)
            ll_vip_card_main.background = getCompactDrawable(R.drawable.ic_vip_card_bg)
        } else {
            vip_monthly_title.visible(false)
            iv_vip_text.visible(true)
            ll_vip_card_main.background = getCompactDrawable(R.drawable.ic_vip_card_gold_bg)
            tv_economize.setTextColor(Color.parseColor("#b7890c"))
            tv_user_name.setTextColor(Color.parseColor("#b7890c"))
        }
        click(rl_monthly_recharge, rl_quarter_recharge, rl_year_recharge, tv_call_service, tv_frequently_asked_questions, tv_select_coupon, rl_wechat_pay, rl_alipay_pay, tv_vip_done,tv_economize) { view ->
            when (view) {
                rl_monthly_recharge -> selectRechargeVipType(0)
                rl_quarter_recharge -> selectRechargeVipType(1)
                rl_year_recharge -> selectRechargeVipType(2)
                tv_call_service -> WebActivity.actionStart(this, "http://59.110.124.41/app/callCenter.html")
                tv_frequently_asked_questions -> WebActivity.actionStart(this, "http://59.110.124.41/app/helpCenter.html")
                tv_select_coupon -> mPresenter?.getUserCoupon()
                rl_wechat_pay -> {
                    payType = 0
                    rl_wechat_pay_selected.isSelected = true
                    rl_alipay_pay_selected.isSelected = false
                    tv_vip_done.isSelected = true
                }
                rl_alipay_pay -> {
                    payType = 1
                    rl_wechat_pay_selected.isSelected = false
                    rl_alipay_pay_selected.isSelected = true
                    tv_vip_done.isSelected = true
                }
                tv_vip_done -> {
                    if (tv_vip_done.isSelected && payType >= 0) {
                        mPresenter?.vipPay(rechargeVipType, "$payType", selectedCouponId)
                    }
                }
                tv_economize -> EconomizeDialog(this).show()
            }
        }
    }

    private fun initViewPager() {
        mViewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> VipPowerFragment().apply { setArguments(0) }
                    else -> VipPowerFragment().apply { setArguments(1) }
                }
            }

            override fun getCount() = 2
        }
        magic_indicator.navigator = CircleNavigator(this).apply {
            circleCount = 2
            circleColor = -0xa17036
            this.setCircleClickListener { index -> mViewPager.currentItem = index }
        }
        ViewPagerHelper.bind(magic_indicator, mViewPager)
    }

    override fun showRechargeInfo(data: String, code: String) {
        if (code == "0") {
            mWechatSdk.pay(data, object : WechatSdk.OnResult<BaseResp> {
                override fun onResult(info: BaseResp) {
                    if (info.errCode == BaseResp.ErrCode.ERR_OK) {
                        toast("支付成功")
                        finish()
                    }
                }
            })
        } else {
            Alipay.pay(this, data, object : Alipay.OnResult {
                override fun onResult(result: Result) {
                    if (result.isSuccess || result.isPending) {
                        toast("支付成功")
                        finish()
                    }
                }
            })
        }
    }

    private fun selectRechargeVipType(type: Int) {
        rechargeVipType = type
        when (type) {
            0 -> {
                rl_monthly_recharge.background = getCompactDrawable(R.drawable.shape_bule_select_bg)
                rl_quarter_recharge.background = getCompactDrawable(R.drawable.shape_hollow_gray_bg)
                rl_year_recharge.background = getCompactDrawable(R.drawable.shape_hollow_gray_bg)
            }
            1 -> {
                rl_monthly_recharge.background = getCompactDrawable(R.drawable.shape_hollow_gray_bg)
                rl_quarter_recharge.background = getCompactDrawable(R.drawable.shape_bule_select_bg)
                rl_year_recharge.background = getCompactDrawable(R.drawable.shape_hollow_gray_bg)
            }
            2 -> {
                rl_monthly_recharge.background = getCompactDrawable(R.drawable.shape_hollow_gray_bg)
                rl_quarter_recharge.background = getCompactDrawable(R.drawable.shape_hollow_gray_bg)
                rl_year_recharge.background = getCompactDrawable(R.drawable.shape_bule_select_bg)
            }
        }
    }

    override fun selectCoupon(couponId: String) {
        selectedCouponId = couponId
        tv_select_coupon.isSelected = selectedCouponId.isNotEmpty()
    }

}
