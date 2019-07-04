package com.novel.cn.mvp.ui.activity

import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.InputFilter
import android.text.Spanned
import android.view.View
import com.alipay.sdk.app.EnvUtils
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.novel.cn.BuildConfig
import com.novel.cn.R
import com.novel.cn.app.JumpManager
import com.novel.cn.app.click
import com.novel.cn.app.loadHeadImage
import com.novel.cn.app.visible
import com.novel.cn.di.component.DaggerRechargeComponent
import com.novel.cn.di.module.RechargeModule
import com.novel.cn.ext.bindToLifecycle
import com.novel.cn.ext.clicks
import com.novel.cn.mvp.contract.RechargeContract
import com.novel.cn.mvp.model.entity.User
import com.novel.cn.mvp.presenter.RechargePresenter
import com.novel.cn.mvp.ui.adapter.RechargeOptionAdapter
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.utils.pay.Alipay
import com.novel.cn.utils.pay.Result
import com.novel.cn.wxapi.WechatSdk
import com.tencent.mm.opensdk.modelbase.BaseResp
import kotlinx.android.synthetic.main.activity_recharge.*
import kotlinx.android.synthetic.main.layout_my_header.*
import org.jetbrains.anko.toast
import javax.inject.Inject
import com.novel.cn.ext.textWatcher
import com.novel.cn.mvp.model.entity.CouponBean
import com.novel.cn.mvp.model.entity.Recharge
import com.novel.cn.mvp.model.entity.VipInfo
import com.novel.cn.mvp.ui.dialog.RechargeDialog
import com.novel.cn.mvp.ui.dialog.SelectCouponPopup
import com.novel.cn.view.SelectCoupon
import com.novel.cn.wxapi.WXPayEvent

import org.greenrobot.eventbus.Subscribe
import razerdp.basepopup.BasePopupWindow
import java.util.regex.Pattern
import kotlinx.android.synthetic.main.include_title.toolbar_back as toolbar_back1


class RechargeActivity : BaseActivity<RechargePresenter>(), RechargeContract.View, SelectCoupon {

    @Inject
    lateinit var mAdapter: RechargeOptionAdapter

    private val mWechatSdk by lazy { WechatSdk(this, BuildConfig.APP_ID_WECHAT); }
    private lateinit var mSelectCouponPopup: SelectCouponPopup
    private var payMoney = ""
    private var mUser: User? = null

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

    override fun initStatusBar(savedInstanceState: Bundle?) {
        StatusBarUtils.darkMode(this)
        StatusBarUtils.setMargin(this, toolbar_back)
        StatusBarUtils.setPaddingSmart(this, cl_top)
    }


    override fun initData(savedInstanceState: Bundle?) {

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
        mAdapter.clicks().subscribe {
            mAdapter.setSelectedItem(it.second)
            hideSoftKeyboard()
            et_money.setText("")
            tv_money_all.text = "(共${mAdapter.getSelectedItem()}元)"
            rfl_done.delegate.backgroundColor = -0xa17036
        }.bindToLifecycle(this)

        click(iv_back, tv_wechat_pay, tv_alipay, tv_recharge, rrl_vip) {
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
                    var money = et_money.text.toString().trim()
                    if (!money.isEmpty()) {
                        val pattern = Pattern.compile("^-?\\d+(\\.\\d+)?$")
                        if (!pattern.matcher(money).matches()) {
                            toast("输入的格式不正确")
                        }
                    } else {
                        money = mAdapter.getSelectedItem()
                    }
                    if (money.isEmpty()) {
                        toast("请选择充值金额")
                        return@click
                    } else if (money.toFloat() < 9.9F) {
                        toast("最低充值9.9元")
                        return@click
                    }
                    payMoney = money
                    mPresenter?.getUserCoupon(payMoney)
                }
                rrl_vip -> mUser?.let {
                    if (it.vipInfo == null)
                        it.vipInfo = VipInfo()
                    JumpManager.jumpVipInfo(this, it)
                }
            }
        }
        val lengthier = object : InputFilter {
            override fun filter(source: CharSequence, start: Int, end: Int,
                                dest: Spanned, dstart: Int, dend: Int): CharSequence? {
                // 删除等特殊字符，直接返回
                if ("" == source.toString()) {
                    return null
                }
                val dValue = dest.toString()
                val splitArray = dValue.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (splitArray.size > 1) {
                    val dotValue = splitArray[1]
                    val diff = dotValue.length + 1 - 2
                    if (diff > 0) {
                        return source.subSequence(start, end - diff)
                    }
                }
                return null
            }
        }
        et_money.filters = arrayOf<InputFilter>(lengthier)

        et_money.textWatcher {
            onTextChanged { charSequence, start, before, count ->
                val text = et_money.text.toString()
                if (text == ".") {
                    et_money.setText("")
                } else {
                    tv_money_all.text = "(共${text}元)"
                }
                if (text.isNotEmpty())
                    mAdapter.setSelectedItem(-1)
                else {
                    tv_money_all.text = "(共0.0元)"
                }
                if (text.isEmpty() || text.toFloat() < 9.9F) {
                    tv_input_money_hint.text = "最低9.9元"
                    tv_money_all.text = ""
                    rfl_done.delegate.backgroundColor = -0x323233
                } else {
                    rfl_done.delegate.backgroundColor = -0xa17036
                    when (text) {
                        "9.9" -> tv_input_money_hint.text = "1000阅读币"
                        "29.9" -> tv_input_money_hint.text = "3000阅读币+66阅读券"
                        "49.9" -> tv_input_money_hint.text = "5000阅读币+99阅读券"
                        "99.9" -> tv_input_money_hint.text = "10000阅读币+299阅读券"
                        else -> tv_input_money_hint.text = "${(text.toFloat() * 100).toInt()}阅读币"
                    }

                }
            }
        }
        toolbar_back.clicks().subscribe {
            finish()
        }.bindToLifecycle(this)
    }

    override fun showUserInfo(data: User) {
        mUser = data
        iv_avatar.loadHeadImage(data.userPhoto)
        iv_gender.setImageResource(if (data.userGender == "0") R.drawable.ic_male else R.drawable.ic_famale)
        tv_read_count.text = "读过${data.readCount}本"
        tv_read_time.text = "阅读${formatDateTime(data.readTime)}"
        rating_star_bar.rating = data.gradeRate.toFloat() / 20
        if (data.userIntroduction.isNotEmpty()) {
            tv_edit.text = data.userIntroduction
        } else
            tv_edit.text = "暂无简介"
        tv_thumbedNum.text = "被赞${data.thumbedNum}次"
        if (data.vipInfo != null && data.vipInfo.vipLevel != 0) {
            rtv_vip_level.text = "VIP${data.vipInfo.vipLevel}"
            rtv_vip_level.visible(true)
        } else
            rtv_vip_level.visible(false)
        tv_account.text = data.userNickName
        tv_blance.text = "${data.goldNumber}阅读币"

        if (data.vipInfo == null || data.vipInfo.isVip == 0) {
            vip_dredge.visible(true)
            rl_vip_info.visible(false)
        } else {
            vip_dredge.visible(false)
            rl_vip_info.visible(true)
            tv_vip_no.text = "NO.${data.vipInfo.userId}"
        }
    }

    override fun dialogDismiss() {
        mPresenter?.getUserInfo()
    }

    override fun showRechargeInfo(data: String, code: String) {
        if (code == "0") {
            mWechatSdk.pay(data, object : WechatSdk.OnResult<BaseResp> {
                override fun onResult(info: BaseResp) {
                    if (info.errCode == BaseResp.ErrCode.ERR_OK) {
                        RechargeDialog(this@RechargeActivity, "支付成功", this@RechargeActivity).show()
                    } else {
                        RechargeDialog(this@RechargeActivity, "支付失败", this@RechargeActivity).show()
                    }
                }
            })
        } else {
            Alipay.pay(this, data, object : Alipay.OnResult {
                override fun onResult(result: Result) {
                    if (result.isSuccess || result.isPending) {
                        RechargeDialog(this@RechargeActivity, "支付成功", this@RechargeActivity).show()
                    } else {
                        RechargeDialog(this@RechargeActivity, "支付失败", this@RechargeActivity).show()
                    }
                }
            })
        }
    }

    override fun selectCoupon(couponId: String) {
        val code = if (!tv_wechat_pay.isEnabled) "0" else "1"
        mPresenter?.recharge(code, payMoney, couponId)
    }

    override fun getUserCouponSuccess(list: List<CouponBean>) {
        var couponList = mutableListOf<CouponBean>()
        couponList.add(CouponBean(isSelect = true))
        couponList.addAll(list)

        mSelectCouponPopup = SelectCouponPopup(this, couponList, this)
        mSelectCouponPopup.onBeforeShowCallback = BasePopupWindow.OnBeforeShowCallback { popupRootView, anchorView, hasShowAnima ->
            mSelectCouponPopup.setBlurBackgroundEnable(true)
            true
        }
        mSelectCouponPopup.showPopupWindow()
    }


    private fun formatDateTime(mss: Int): String {
        var Times = ""
        var days = mss / (60 * 24)
        var hours = (mss % (60 * 24)) / 60
        var minutes = (mss % 60)
        Times = if (days > 0) {
            "${days}天${hours}小时${minutes}分钟"
        } else if (hours > 0) {
            "${hours}小时${minutes}分钟"
        } else if (minutes > 0) {
            "$${minutes}分钟"
        } else {
            "0分钟"
        }
        return Times

    }


    @Subscribe
    fun payEvent(event: WXPayEvent) {
        mWechatSdk.onWXPayEvent(event.data)
    }
}
