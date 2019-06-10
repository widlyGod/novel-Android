package com.novel.cn.mvp.ui.activity

import android.content.Intent
import android.os.Bundle

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.novel.cn.di.component.DaggerMineAccountComponent
import com.novel.cn.di.module.MineAccountModule
import com.novel.cn.mvp.contract.MineAccountContract
import com.novel.cn.mvp.presenter.MineAccountPresenter

import com.novel.cn.R
import com.novel.cn.mvp.model.entity.UserAccountBean
import kotlinx.android.synthetic.main.activity_mine_account.*


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/06/2019 15:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
/**
 * 如果没presenter
 * 你可以这样写
 *
 * @ActivityScope(請注意命名空間) class NullObjectPresenterByActivity
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */
class MineAccountActivity : BaseActivity<MineAccountPresenter>(), MineAccountContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerMineAccountComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mineAccountModule(MineAccountModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_mine_account //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun getUserAccountInfoSuccess(userAccountBean: UserAccountBean) {
        tv_user_money.text = userAccountBean.goldNumber.toString()
    }


    override fun initData(savedInstanceState: Bundle?) {
        mPresenter?.getUserAccountInfo()
    }


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        finish()
    }
}
