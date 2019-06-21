package com.novel.cn.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import com.jakewharton.rxbinding3.view.clicks

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.novel.cn.di.component.DaggerMineBillComponent
import com.novel.cn.di.module.MineBillModule
import com.novel.cn.mvp.contract.MineBillContract
import com.novel.cn.mvp.presenter.MineBillPresenter

import com.novel.cn.R
import com.novel.cn.app.visible
import com.novel.cn.ext.bindToLifecycle
import com.novel.cn.mvp.model.entity.MyBillBean
import com.novel.cn.mvp.ui.adapter.MyBillAdapter
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.CustomLoadMoreView
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.activity_mine_bill.*
import kotlinx.android.synthetic.main.activity_mine_bill.recyclerView
import kotlinx.android.synthetic.main.activity_mine_bill.stateView
import kotlinx.android.synthetic.main.include_title.*
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/19/2019 11:30
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
class MineBillActivity : BaseActivity<MineBillPresenter>(), MineBillContract.View {

    override fun getMyBillSuccess(myBillBean: MyBillBean) {
        mAdapter.setNewData(myBillBean.myBuyOrders)
    }

    @Inject
    lateinit var mAdapter: MyBillAdapter

    var type = 0
    var startDate = ""
    var endDate = ""

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerMineBillComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mineBillModule(MineBillModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_mine_bill //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initStatusBar(savedInstanceState: Bundle?) {
        //白底黑字
        StatusBarUtils.darkMode(this)
//        给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, toolbar)
    }


    override fun initData(savedInstanceState: Bundle?) {
        val form = SimpleDateFormat("yyyy-MM")
        val calendar = Calendar.getInstance()
        val today = calendar.time
        startDate = form.format(today)
        recyclerView.adapter = mAdapter
        rv_select_date.clicks().subscribe {
            val intent = Intent()
            intent.setClass(this, MineBillDateSelectActivity::class.java)
            startActivityForResult(intent, 0x01)
        }.bindToLifecycle(this)
        mPresenter?.getMyBill(startDate, endDate, type)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0x01 && resultCode == 0x01) {
            type = data!!.getIntExtra("type", 0)
            startDate = data.getStringExtra("startDate")
            endDate = data.getStringExtra("endDate")
            if (type == 0) {
                endDate = ""
                tv_bill_date.text = startDate
                tv_bill_date_end.visible(false)
                mPresenter?.getMyBill(startDate, endDate, type)
            } else {
                tv_bill_date.text = startDate
                tv_bill_date_end.text = endDate
                tv_bill_date_end.visible(true)
                mPresenter?.getMyBill(startDate, endDate, type)
            }
        }
    }

    override fun showStateView(state: Int) {
        stateView.viewState = state
    }
}
