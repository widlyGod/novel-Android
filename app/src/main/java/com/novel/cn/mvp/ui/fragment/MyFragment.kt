package com.novel.cn.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.IndexEvent
import com.novel.cn.R
import com.novel.cn.app.*
import com.novel.cn.di.component.DaggerMyComponent
import com.novel.cn.di.module.MyModule
import com.novel.cn.mvp.contract.MyContract
import com.novel.cn.mvp.model.entity.LoginInfo
import com.novel.cn.mvp.model.entity.User
import com.novel.cn.mvp.model.entity.VipInfo
import com.novel.cn.mvp.presenter.MyPresenter
import com.novel.cn.mvp.ui.activity.MessageActivity
import com.novel.cn.mvp.ui.activity.SettingActivity
import com.novel.cn.mvp.ui.activity.VipActivity
import com.novel.cn.utils.StatusBarUtils
import kotlinx.android.synthetic.main.fragment_my.*
import kotlinx.android.synthetic.main.layout_my_header.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity


class MyFragment : BaseFragment<MyPresenter>(), MyContract.View {


    companion object {
        fun newInstance(): MyFragment {
            val fragment = MyFragment()
            return fragment
        }
    }

    private var mUser: User? = null

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerMyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myModule(MyModule(this))
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun initStatusBar(savedInstanceState: Bundle?) {
        //给布局加一个状态栏高度
        StatusBarUtils.setPadding(activity, cl_top)
    }

    override fun initData(savedInstanceState: Bundle?) {
        iv_setting.visible(true)

        click(iv_setting, fl_messsage, iv_avatar, tv_recharge, tv_my_account, rrl_vip) { view ->
            when (view) {
                iv_setting -> activity?.startActivity<SettingActivity>()
                fl_messsage -> activity?.startActivity<MessageActivity>()
                iv_avatar -> mUser?.let {
                    if (it.vipInfo == null)
                        it.vipInfo = VipInfo()
                    JumpManager.jumpUserInfo(activity, it)
                }
                tv_recharge -> JumpManager.jumpRecharge(activity)
                tv_my_account -> mUser?.let {
                    if (it.vipInfo == null)
                        it.vipInfo = VipInfo()
                    JumpManager.jumpMineAccount(activity, it)
                }
                rrl_vip -> mUser?.let {
                    if (it.vipInfo == null)
                        it.vipInfo = VipInfo()
                    JumpManager.jumpVipInfo(activity, it)
                }
            }
        }

    }

    override fun onResume() {
        super.onResume()
        mPresenter?.getUserInfo()
    }


    override fun showUserInfo(data: User) {
        mUser = data
        iv_avatar.loadHeadImage(data.userPhoto)
        iv_gender.setImageResource(if (data.userGender == "0") R.drawable.ic_male else R.drawable.ic_famale)
        tv_read_count.text = "读过${data.readCount}本"
        tv_read_time.text = "阅读${formatDateTime(data.readTime)}"
        rating_star_bar.rating = data.gradeRate.toFloat() / 20
        tv_thumbedNum.text = "被赞${data.thumbedNum}次"
        if (data.vipInfo != null && data.vipInfo.vipLevel != 0) {
            rtv_vip_level.text = "VIP${data.vipInfo.vipLevel}"
            rtv_vip_level.visible(true)
        } else
            rtv_vip_level.visible(false)
        if (data.userIntroduction.isNotEmpty()) {
            tv_edit.text = data.userIntroduction
        } else
            tv_edit.text = "暂无简介"
        tv_meet_day.text = "${data.meetDays}天"
        tv_sign_day.text = "${data.signDays}天"
        tv_thumbNum.text = "${data.thumbNum}个"
        tv_commentNum.text = "${data.commentNum}条"
        tv_user_level.text = "${data.grade}"

        tv_discount_num.text = "${data.discountCoupon}"
        tv_month_ticket.text = "${data.monthRecommendNumber}"
        tv_recommend_ticket.text = "${data.recommendNumber}"
        tv_coupon.text = "${data.coupon}"
        if (data.msgCount <= 0) {
            tv_msg.visible(false)
        } else {
            tv_msg.visible(true)
            tv_msg.text = "${data.msgCount}"
        }

        tv_account.text = "余额\t\t\t${data.rechargeNumber}阅点 + ${data.rewardNumber}阅券"

        if (data.vipInfo == null || data.vipInfo.isVip == 0) {
            vip_dredge.visible(true)
            rl_vip_info.visible(false)
        } else {
            vip_dredge.visible(false)
            rl_vip_info.visible(true)
            tv_vip_no.text = "NO.${data.vipInfo.vipNo}"
        }
    }

    private fun formatDateTime(mss: Int): String {
        var DateTimes = ""
        var days = mss / (60 * 24)
        var hours = (mss % (60 * 24)) / 60
        var minutes = (mss % 60)
        DateTimes = if (days > 0) {
            "${days}天${hours}小时${minutes}分钟"
        } else if (hours > 0) {
            "${hours}小时${minutes}分钟"
        } else if (minutes > 0) {
            "$${minutes}分钟"
        } else {
            "0分钟"
        }
        return DateTimes

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onIndexChange(event: IndexEvent) {
        if (event.index == 3)
            mPresenter?.getUserInfo()
    }

}
