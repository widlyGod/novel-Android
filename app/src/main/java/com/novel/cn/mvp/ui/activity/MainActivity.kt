package com.novel.cn.mvp.ui.activity

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.LoginEvent
import com.novel.cn.R
import com.novel.cn.app.Constant
import com.novel.cn.app.Preference
import com.novel.cn.di.component.DaggerMainComponent
import com.novel.cn.di.module.MainModule
import com.novel.cn.mvp.contract.MainContract
import com.novel.cn.mvp.model.entity.LoginInfo
import com.novel.cn.mvp.presenter.MainPresenter
import com.novel.cn.mvp.ui.fragment.MyFragment
import com.novel.cn.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity
import java.util.*
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


    val mTitleTypeface: Typeface by lazy { Typeface.createFromAsset(assets, "fonts/FZQKBYSJW.TTF") }

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
        mPresenter?.uploadUseTime()
        mPresenter?.uploadReadTime()
        setupPages()
        val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)

        if (user!!.sessionId.isBlank())
            switchFragment(1)
        else
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

    var positionTab = 0

    /**
     * 切换fragment页面
     */
    private fun switchFragment(position: Int) {
        val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
        if (position == 3 && user!!.userId.isBlank()) {
            startActivity<LoginActivity>()
            tabLayout.currentTab = positionTab
            return
        }
        positionTab = position
        tabLayout.currentTab = position
        val fragment = mFragments[position]
        val transaction = supportFragmentManager.beginTransaction()
        if (fragment != mCurrentFragment) {
            if (mCurrentFragment == null) {
                transaction.add(R.id.fl_content, fragment).commitAllowingStateLoss()
            } else if (!fragment.isAdded) {
                transaction.hide(mCurrentFragment!!).add(R.id.fl_content, fragment).commitAllowingStateLoss()
            } else {
                transaction.hide(mCurrentFragment!!).show(fragment).commitAllowingStateLoss()
            }
            mCurrentFragment = fragment
            changeStatusBar()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onLoginChange(event: LoginEvent) {
        val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
        if (user!!.sessionId.isBlank())
            switchFragment(1)
        else
            switchFragment(0)
    }
}
