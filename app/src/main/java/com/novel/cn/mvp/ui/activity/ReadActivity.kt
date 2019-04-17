package com.novel.cn.mvp.ui.activity

import android.os.Bundle
import android.view.WindowManager

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent

import com.novel.cn.di.component.DaggerReadComponent
import com.novel.cn.di.module.ReadModule
import com.novel.cn.mvp.contract.ReadContract
import com.novel.cn.mvp.presenter.ReadPresenter

import com.novel.cn.R
import com.novel.cn.app.click
import com.novel.cn.app.isVisible
import com.novel.cn.app.visible
import com.novel.cn.mvp.ui.dialog.ReadSettingDialog
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.read.PageView
import kotlinx.android.synthetic.main.activity_read.*


class ReadActivity : BaseActivity<ReadPresenter>(), ReadContract.View {


    private val mSettingDialog by lazy {
        ReadSettingDialog(this).apply {
            setOnDismissListener {
                hideSystemBar()
            }
        }
    }


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

        pageView.setOnTouchListener(object : PageView.OnTouchListener {
            override fun onMenuClick() {
                toggleMenu()
            }

            override fun onClick() {
                if (toolbar.isVisible()) {
                    hideSystemBar()
                }
            }

        })
        pageView.init()
        pageView.postDelayed({pageView.init()},100)

        click(tv_content, tv_setting) {
            when (it) {
                tv_content -> toggleMenu()
                tv_setting -> {
                    ll_bottom_menu.visible(false)
                    mSettingDialog.show()
                }
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
