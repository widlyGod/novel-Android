package com.novel.cn.mvp.ui.activity

import android.content.Intent
import android.os.Bundle

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.novel.cn.di.component.DaggerUserInfoComponent
import com.novel.cn.di.module.UserInfoModule
import com.novel.cn.mvp.contract.UserInfoContract
import com.novel.cn.mvp.presenter.UserInfoPresenter

import com.novel.cn.R
import com.novel.cn.utils.StatusBarUtils
import kotlinx.android.synthetic.main.include_title.*


class UserInfoActivity : BaseActivity<UserInfoPresenter>(), UserInfoContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerUserInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .userInfoModule(UserInfoModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_user_info //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        //白底黑字
        StatusBarUtils.darkMode(this)
        //给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, toolbar)
    }


}
