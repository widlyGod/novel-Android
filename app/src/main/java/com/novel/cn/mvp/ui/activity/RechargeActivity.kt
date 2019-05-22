package com.novel.cn.mvp.ui.activity

import android.graphics.Rect
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.alipay.sdk.app.EnvUtils
import com.google.gson.Gson
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.novel.cn.BuildConfig
import com.novel.cn.R
import com.novel.cn.app.click
import com.novel.cn.app.loadImage
import com.novel.cn.di.component.DaggerRechargeComponent
import com.novel.cn.di.module.RechargeModule
import com.novel.cn.mvp.contract.RechargeContract
import com.novel.cn.mvp.model.entity.PayInfo
import com.novel.cn.mvp.model.entity.User
import com.novel.cn.mvp.presenter.RechargePresenter
import com.novel.cn.mvp.ui.adapter.RechargeOptionAdapter
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.utils.pay.Alipay
import com.novel.cn.utils.pay.Result
import com.novel.cn.wxapi.WechatSdk
import com.tencent.mm.opensdk.modelbase.BaseResp
import kotlinx.android.synthetic.main.activity_recharge.*
import kotlinx.android.synthetic.main.include_title.*
import kotlinx.android.synthetic.main.layout_my_header.*
import org.jetbrains.anko.toast
import javax.inject.Inject
import com.novel.cn.ext.textWatcher
import com.novel.cn.mvp.model.entity.Recharge
import com.novel.cn.wxapi.WXPayEvent

import org.greenrobot.eventbus.Subscribe
import java.util.regex.Pattern


class RechargeActivity : BaseActivity<RechargePresenter>(), RechargeContract.View {


    @Inject
    lateinit var mAdapter: RechargeOptionAdapter

    private val mWechatSdk by lazy { WechatSdk(this, BuildConfig.APP_ID_WECHAT); }

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
        //支付宝沙盒环境
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX)

        mPresenter?.getUserInfo()


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


        val list = ArrayList<Recharge>()

        list.add(Recharge("9.9", "1000", ""))
        list.add(Recharge("29.9", "3000", "66"))
        list.add(Recharge("49.9", "5000", "99"))
        list.add(Recharge("99.9", "10000", "299"))
        mAdapter.setNewData(list)

        click(iv_back, tv_wechat_pay, tv_alipay, tv_recharge) {
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
                tv_recharge -> {
                    var money = et_money.text.toString()
                    if (!money.isEmpty()) {
                        val pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$")
                        if (!pattern.matcher(money).matches()) {
                            toast("输入的格式不正确")
                        }
                    } else {
                        money = mAdapter.getSelectedItem().price
                    }
                    val code = if (!tv_wechat_pay.isEnabled) "0" else "1"
                    mPresenter?.recharge(code, money)
                }
            }
        }
        et_money.textWatcher {
            onTextChanged { charSequence, start, before, count ->
                val text = et_money.text.toString()
                if (text == ".") {
                    et_money.text = null
                } else {
                    tv_money.text = "(共${text}元)"
                }
            }
        }
    }

    override fun showUserInfo(data: User) {
        iv_avatar.loadImage(data.userPhoto)
        iv_gender.setImageResource(if (data.userGender == 0) R.drawable.ic_male else R.drawable.ic_famale)
        tv_read_count.text = "读过${data.readCount}本"
        tv_account.text = data.userName
        tv_blance.text = "${data.moneys}阅币"
    }

    override fun showRechargeInfo(data: String, code: String) {
        if (code == "0") {
            mWechatSdk.pay(data, object : WechatSdk.OnResult<BaseResp> {
                override fun onResult(info: BaseResp) {
                    finish()
                }
            })
        } else {
            Alipay.pay(this, data, object : Alipay.OnResult {
                override fun onResult(result: Result) {
                    if (result.isSuccess || result.isPending) {
                        toast("支付成功")
                    }
                }
            })
        }
    }

    @Subscribe
    fun payEvent(event: WXPayEvent) {
        mWechatSdk.onWXPayEvent(event.data)
    }
}
