package com.novel.cn.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.novel.cn.di.component.DaggerReadComponent
import com.novel.cn.di.module.ReadModule
import com.novel.cn.mvp.contract.ReadContract
import com.novel.cn.mvp.presenter.ReadPresenter

import com.novel.cn.R
import com.novel.cn.app.click
import com.novel.cn.app.isVisible
import com.novel.cn.app.visible
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.utils.SystemBarUtils
import kotlinx.android.synthetic.main.activity_read.*


class ReadActivity : BaseActivity<ReadPresenter>(), ReadContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerReadComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .readModule(ReadModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_read
    }


    override fun initData(savedInstanceState: Bundle?) {
        StatusBarUtils.immersive(this)
        StatusBarUtils.setPaddingSmart(this, toolbar)


        click(tv_content) {
            when (it) {
                tv_content -> toggleMenu()
            }
        }
    }

    private fun toggleMenu() {
        if (toolbar.isVisible()) {
            //close
            hideSystemBar()
        } else {
            showSystemBar()
        }
    }

    private fun hideSystemBar() {
        //隐藏
        toolbar.visible(false)
        ll_bottom_menu.visible(false)
        val attrs = window.attributes
        attrs.flags = attrs.flags or WindowManager.LayoutParams.FLAG_FULLSCREEN
        window.attributes = attrs
    }

    private fun showSystemBar() {
        //显示
        toolbar.visible(true)
        ll_bottom_menu.visible(true)
        val attrs = window.attributes
        attrs.flags = attrs.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
        window.attributes = attrs

    }
}
