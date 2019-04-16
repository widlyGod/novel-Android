package com.novel.cn.mvp.ui.activity

import android.content.Intent
import android.os.Bundle

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils

import com.novel.cn.di.component.DaggerUserInfoComponent
import com.novel.cn.di.module.UserInfoModule
import com.novel.cn.mvp.contract.UserInfoContract
import com.novel.cn.mvp.presenter.UserInfoPresenter

import com.novel.cn.R
import com.novel.cn.app.loadImage
import com.novel.cn.mvp.model.entity.User
import com.novel.cn.mvp.ui.dialog.UpdateGenderDialog
import com.novel.cn.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_user_info.*
import kotlinx.android.synthetic.main.include_title.*


class UserInfoActivity : BaseActivity<UserInfoPresenter>(), UserInfoContract.View {

    private val mUser by lazy { intent.getParcelableExtra<User?>("user") }


    private val mGenderDialog by lazy {
        UpdateGenderDialog(this, mUser?.userGender)
                .apply {
                    setOnSelectGenderListener {
                        dismiss()
                        //这里有个坑，使用activity控件必须要activity实例点出来，如果不加activity实例，默认是使用dialog的（即使dialog没有这个控件）
                        this@UserInfoActivity.tv_gender.text = if (it == 0) "男" else "女"
                        mPresenter?.updateGender(it)
                    }
                }
    }

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

        mUser?.let {
            iv_avatar.loadImage(it.userPhoto)
            tv_nickname.text = it.userName
            tv_gender.text = if (it.userGender == 0) "男" else "女"
            tv_intro.text = it.userIntro
        }

        cl_gender.setOnClickListener {
            mGenderDialog.show()
        }
    }


}
