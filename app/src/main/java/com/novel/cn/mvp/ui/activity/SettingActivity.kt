package com.novel.cn.mvp.ui.activity

import android.os.Bundle
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.EventBusManager
import com.jess.arms.utils.LoginEvent
import com.novel.cn.BuildConfig
import com.novel.cn.R
import com.novel.cn.app.Constant
import com.novel.cn.app.Preference
import com.novel.cn.app.click
import com.novel.cn.di.component.DaggerSettingComponent
import com.novel.cn.di.module.SettingModule
import com.novel.cn.mvp.contract.SettingContract
import com.novel.cn.mvp.model.entity.LoginInfo
import com.novel.cn.mvp.presenter.SettingPresenter
import com.novel.cn.utils.CacheDataManager
import com.novel.cn.utils.StatusBarUtils
import com.tencent.bugly.beta.Beta
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.include_title.*
import org.jetbrains.anko.startActivity


class SettingActivity : BaseActivity<SettingPresenter>(), SettingContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerSettingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .settingModule(SettingModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_setting //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initStatusBar(savedInstanceState: Bundle?) {
        //白底黑字
        StatusBarUtils.darkMode(this)
        //给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, toolbar)
    }


    override fun initData(savedInstanceState: Bundle?) {

        tv_version.text = "V${BuildConfig.VERSION_NAME}"
        tv_cache.text = CacheDataManager.getTotalCacheSize(this)


        click(fl_cache, tv_about, tv_logout, examine_update) {
            when (it) {
                tv_logout -> {
                    Preference.clean()
                    Preference.put(Constant.LOGIN_INFO, LoginInfo("","",0))
                    finish()
                    EventBusManager.getInstance().post(LoginEvent())
//                    startActivity<MainActivity>()
//                    AppManager.getAppManager().killAll(MainActivity::class.java)
                }
                tv_about -> WebActivity.actionStart(this,"http://59.110.124.41/app/aboutUs.html")
                fl_cache -> {
                    CacheDataManager.clearAllCache(this)
                    tv_cache.text = CacheDataManager.getTotalCacheSize(this)
                }
                examine_update -> Beta.checkUpgrade()
            }
        }
    }


}
