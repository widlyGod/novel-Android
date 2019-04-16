package com.novel.cn.mvp.ui.activity

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.novel.cn.R
import com.novel.cn.app.click
import com.novel.cn.app.loadImage
import com.novel.cn.di.component.DaggerRechargeComponent
import com.novel.cn.di.module.RechargeModule
import com.novel.cn.mvp.contract.RechargeContract
import com.novel.cn.mvp.model.entity.Demo
import com.novel.cn.mvp.model.entity.User
import com.novel.cn.mvp.presenter.RechargePresenter
import com.novel.cn.mvp.ui.adapter.RechargeOptionAdapter
import com.novel.cn.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_recharge.*
import kotlinx.android.synthetic.main.include_title.*
import kotlinx.android.synthetic.main.layout_my_header.*
import javax.inject.Inject


class RechargeActivity : BaseActivity<RechargePresenter>(), RechargeContract.View {

    @Inject
    lateinit var mAdapter: RechargeOptionAdapter


    private val mUser by lazy { intent.getParcelableExtra<User?>("user") }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerRechargeComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .rechargeModule(RechargeModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_recharge //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        StatusBarUtils.darkMode(this)
        StatusBarUtils.setPaddingSmart(this, cl_top)
        StatusBarUtils.setPaddingSmart(this, toolbar)

        mUser?.let { data ->
            iv_avatar.loadImage(data.userPhoto)
            iv_gender.setImageResource(if (data.userGender == 0) R.drawable.ic_male else R.drawable.ic_famale)
            tv_read_count.text = "读过${data.readCount}本"
            tv_account.text = data.userName
            tv_blance.text = "${data.moneys}阅币"
        }

        recyclerView.adapter = mAdapter
        recyclerView.isFocusableInTouchMode = false
        recyclerView.requestFocus()
        //设置间隔
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            //水平线
            val horizontalSpec = ArmsUtils.dip2px(this@RechargeActivity, 17f)
            //垂直线，因为左右2个item，所以要除以2
            val verticalHalfSpec = ArmsUtils.dip2px(this@RechargeActivity, 30f) / 2

            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                //获取当前position
                val position = parent.getChildAdapterPosition(view)
                //列数
                val spanCount = (parent.layoutManager as GridLayoutManager).spanCount
                //当前所在列数下标
                val spanIndex = (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex

                val itemCount = parent.adapter!!.itemCount

                var left = 0
                var bottom = 0
                var right = 0

                if (spanIndex == 0 && spanCount > 1) {
                    right = verticalHalfSpec
                } else if (spanIndex == spanCount - 1 && spanCount > 1) {
                    left = verticalHalfSpec
                }
                //不是最后一行最后一行
                if (position < itemCount - spanCount) {
                    bottom = horizontalSpec
                }
                outRect.set(left, 0, right, bottom)

            }
        })

        /*val h = ArmsUtils.dip2px(this, 80f)
        var lastScrollY = 0
        val color = ContextCompat.getColor(applicationContext, R.color.white) and 0x00ffffff


        scrollView.setOnScrollChangeListener { nestedScrollView: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (lastScrollY < h) {
                var temp = Math.min(h, scrollY)
                toolbar.setBackgroundColor(255 * temp / h shl 24 or color)
            }
            lastScrollY = scrollY
        }*/


        val list = ArrayList<Any>()

        list.add("")
        list.add("")
        list.add("")
        list.add("")
        mAdapter.setNewData(list)

        click(iv_back, tv_wechat_pay, tv_alipay) {
            when (it) {
                iv_back -> finish()
                tv_wechat_pay -> {
                    it.isEnabled = false
                    tv_alipay.isEnabled = true
                }
                tv_alipay -> {
                    it.isEnabled = false
                    tv_wechat_pay.isEnabled = true
                }
            }
        }
    }


}
