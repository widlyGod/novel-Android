package com.novel.cn.mvp.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Message
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.novel.cn.di.component.DaggerMyComponent
import com.novel.cn.di.module.MyModule
import com.novel.cn.mvp.contract.MyContract
import com.novel.cn.mvp.presenter.MyPresenter

import com.novel.cn.R
import com.novel.cn.app.click
import com.novel.cn.mvp.ui.activity.*
import com.novel.cn.utils.StatusBarUtils
import kotlinx.android.synthetic.main.fragment_my.*
import org.jetbrains.anko.startActivity


class MyFragment : BaseFragment<MyPresenter>(), MyContract.View {
    companion object {
        fun newInstance(): MyFragment {
            val fragment = MyFragment()
            return fragment
        }
    }


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerMyComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .myModule(MyModule(this))
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_my, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {
        //给布局加一个状态栏高度
        StatusBarUtils.setPadding(activity, top)
        click(iv_setting, fl_messsage,iv_avatar,tv_recharge) {
            when (it) {
                iv_setting -> activity?.startActivity<SettingActivity>()
                fl_messsage -> activity?.startActivity<MessageActivity>()
                iv_avatar -> activity?.startActivity<UserInfoActivity>()
                tv_recharge->activity?.startActivity<RechargeActivity>()
            }
        }
    }


    override fun setData(data: Any?) {

    }

}
