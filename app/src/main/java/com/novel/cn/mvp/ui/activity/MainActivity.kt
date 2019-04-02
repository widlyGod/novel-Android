package com.novel.cn.mvp.ui.activity

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.novel.cn.di.component.DaggerMainComponent
import com.novel.cn.di.module.MainModule
import com.novel.cn.mvp.contract.MainContract
import com.novel.cn.mvp.presenter.MainPresenter
import com.novel.cn.R
import com.novel.cn.mvp.ui.fragment.BookshelfFragment
import com.novel.cn.mvp.ui.fragment.MyFragment
import com.novel.cn.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList
import javax.inject.Inject
import javax.inject.Named

class MainActivity : BaseActivity<MainPresenter>(), MainContract.View {

    @Inject
    @field:Named("NavTabEntity")
    lateinit var mTabEntities: ArrayList<CustomTabEntity>

    @Inject
    @field:Named("NavFragment")
    lateinit var mFragments: Array<Fragment>

    private var mCurrentFragment: Fragment? = null

    val mTitleTypeface by lazy { Typeface.createFromAsset(assets, "fonts/FZQKBYSJW.TTF") }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(MainModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }


    override fun initData(savedInstanceState: Bundle?) {
        setupPages()
        switchFragment(0)

    }

    /**
     * 设置fragment页面
     */
    private fun setupPages() {
        tabLayout.setTabData(mTabEntities)
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                switchFragment(position)

            }

            override fun onTabReselect(position: Int) {
            }
        })
    }

    private fun changeStatusBar() {
        if (mCurrentFragment is MyFragment) {
            //我的页面状态栏字体为白色
            StatusBarUtils.darkMode(this, false)
            StatusBarUtils.immersive(this)
        } else {
            StatusBarUtils.darkMode(this)
        }
    }

    /**
     * 切换fragment页面
     */
    private fun switchFragment(position: Int) {
        val fragment = mFragments[position]
        val transaction = supportFragmentManager.beginTransaction()
        if (fragment != mCurrentFragment) {
            if (mCurrentFragment == null) {
                transaction.add(R.id.fl_content, fragment).commit()
            } else if (!fragment.isAdded) {
                transaction.hide(mCurrentFragment!!).add(R.id.fl_content, fragment).commit()
            } else {
                transaction.hide(mCurrentFragment!!).show(fragment).commit()
            }
            mCurrentFragment = fragment
            changeStatusBar()
        }
    }
}
