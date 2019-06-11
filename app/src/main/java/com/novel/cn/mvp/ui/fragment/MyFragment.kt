package com.novel.cn.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.novel.cn.R
import com.novel.cn.app.JumpManager
import com.novel.cn.app.click
import com.novel.cn.app.loadImage
import com.novel.cn.app.visible
import com.novel.cn.di.component.DaggerMyComponent
import com.novel.cn.di.module.MyModule
import com.novel.cn.mvp.contract.MyContract
import com.novel.cn.mvp.model.entity.User
import com.novel.cn.mvp.presenter.MyPresenter
import com.novel.cn.mvp.ui.activity.MessageActivity
import com.novel.cn.mvp.ui.activity.SettingActivity
import com.novel.cn.utils.StatusBarUtils
import kotlinx.android.synthetic.main.fragment_my.*
import kotlinx.android.synthetic.main.layout_my_header.*
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

        click(iv_setting, fl_messsage, iv_avatar, tv_recharge, tv_my_account) { view ->
            when (view) {
                iv_setting -> activity?.startActivity<SettingActivity>()
                fl_messsage -> activity?.startActivity<MessageActivity>()
                iv_avatar -> mUser?.let { JumpManager.jumpUserInfo(activity, it) }
                tv_recharge -> JumpManager.jumpRecharge(activity)
                tv_my_account -> JumpManager.jumpMineAccount(activity)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        mPresenter?.getUserInfo()
    }


    override fun showUserInfo(data: User) {
        mUser = data
        iv_avatar.loadImage(data.userPhoto)
        iv_gender.setImageResource(if (data.userGender == 0) R.drawable.ic_male else R.drawable.ic_famale)
        tv_read_count.text = "读过${data.readCount}本"

        tv_meet_day.text = "${data.meetDays}天"
        tv_sign_day.text = "${data.signDays}天"

        tv_month_ticket.text = data.monthTickets
        tv_recommend_ticket.text = data.recommendTickets

        tv_msg.text = data.msgCount
        tv_account.text = "余额\t\t\t${data.moneys}阅点"

    }
}
