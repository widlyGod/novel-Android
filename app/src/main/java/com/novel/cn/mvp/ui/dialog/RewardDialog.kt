package com.novel.cn.mvp.ui.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat.getSystemService
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.flyco.dialog.widget.base.BaseDialog
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.novel.cn.R
import com.novel.cn.app.JumpManager
import com.novel.cn.app.visible
import com.novel.cn.ext.clicks
import com.novel.cn.mvp.contract.ReadContract
import com.novel.cn.mvp.model.entity.Reward
import com.novel.cn.mvp.model.entity.TabEntity
import com.novel.cn.mvp.model.entity.UserAccountBean
import com.novel.cn.mvp.ui.adapter.RewardAdapter
import kotlinx.android.synthetic.main.dialog_reward.*
import kotlinx.android.synthetic.main.dialog_reward_recommend.*
import kotlinx.android.synthetic.main.dialog_reward_recommend.view.*
import kotlinx.android.synthetic.main.layout_reward.*
import kotlinx.android.synthetic.main.layout_reward.view.*


class RewardDialog(context: Context, private val view: ReadContract.View) : BaseDialog<RewardDialog>(context) {

    private val title = arrayOf("推荐票", "月票", "钻石", "打赏")

    private var recommendView: View? = null

    private var rewardView: View? = null

    private val views = ArrayList<View>()

    private lateinit var mUserAccountBean: UserAccountBean

    private var mPosition = 0
    private var rewardNum = 0
    var readMoneySelected = 0

    init {
        this.window?.setDimAmount(0f)
        widthScale(0.85f)
    }

    override fun setUiBeforShow() {

    }

    fun hintKeyboard() {
        val view = window.peekDecorView()
        val inputmanger = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputmanger.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @SuppressLint("CheckResult")
    fun setUserAccount(userAccountBean: UserAccountBean) {
        mUserAccountBean = userAccountBean
        adapter.setUserMoney(userAccountBean.goldNumber)
        recommendView!!.et_reward_num.textChanges()
                .subscribe {
                    rewardNum = if (recommendView!!.et_reward_num.text.isNotEmpty())
                        recommendView!!.et_reward_num.text.toString().toInt()
                    else
                        0
                    when (mPosition) {
                        0 -> {
                            recommendView!!.tv_user_recommend.text = "账户中还有 ${mUserAccountBean.recommendNumber} 推荐票，本次投 $rewardNum 推荐票"
                        }
                        1 -> {
                            recommendView!!.tv_user_recommend.text = "账户中还有 ${mUserAccountBean.monthRecommendNumber} 月票，本次投 $rewardNum 月票"
                        }
                        2 -> {
                            recommendView!!.tv_user_recommend.text = "账户中还有 ${mUserAccountBean.diamondNumber} 钻石，本次投 $rewardNum 钻石"
                        }
                        3 -> {
                            setView(rewardView)
                        }
                    }
                }
        rewardView!!.tv_reward_detail.text = "账户余额 ${mUserAccountBean.goldNumber} 阅读币，本次打赏 ${0} 阅读币"
        if (mUserAccountBean.goldNumber < 10) {
            rewardView!!.tv_reward_done.text = "去充值"
            rewardView!!.tv_reward_detail.text = "账户余额 ${mUserAccountBean.goldNumber} 阅读币，余额不足"
            rewardView!!.setOnClickListener {
                JumpManager.jumpRecharge(context)
                dismiss()
            }
        }
        readMoneySelected = 0
        adapter.setSelect(readMoneySelected)

    }

    override fun onCreateView(): View {
        return layoutInflater.inflate(R.layout.dialog_reward, null, false)
    }

    private val adapter by lazy { RewardAdapter() }

    @SuppressLint("CheckResult", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tabEntitys = ArrayList<CustomTabEntity>(title.size)
        title.forEach { tabEntitys.add(TabEntity(it, 0, 0)) }
        tabLayout.setTabData(tabEntitys)
        recommendView = layoutInflater.inflate(R.layout.dialog_reward_recommend, fl_content, false)
        rewardView = layoutInflater.inflate(R.layout.layout_reward, fl_content, false)
        val list = arrayListOf(
                Reward("10", 0), Reward("30", 0), Reward("50", 0),
                Reward("100", 0), Reward("150", 0), Reward("200", 0),
                Reward("500", 1), Reward("1000", 2), Reward("1500", 3))
        adapter.setNewData(list)
        rewardView!!.recyclerView.adapter = adapter
        views.add(recommendView!!)
        views.add(rewardView!!)
        fl_content.addView(recommendView)
        fl_content.addView(rewardView)
        setView(recommendView)


        adapter.clicks().subscribe {
            if (mUserAccountBean.goldNumber > list[it.second].money.toInt()) {
                adapter.setSelect(it.second)
                readMoneySelected = it.second
                rewardView!!.tv_reward_detail.text = "账户余额 ${mUserAccountBean.goldNumber} 阅读币，本次打赏 ${list[readMoneySelected].money} 阅读币"

            }
        }
        recommendView!!.tv_reward.clicks().subscribe {
            when (mPosition) {
                0 -> view.reward("recommend", rewardNum)
                1 -> view.reward("monthRecommend", rewardNum)
                2 -> view.reward("diamond", rewardNum)
            }
            dismiss()
        }
        rewardView!!.tv_reward_done.clicks().subscribe {
            view.reward("Money", list[readMoneySelected].money.toInt())
            dismiss()
        }

        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                mPosition = position
                rewardNum = 0
                recommendView!!.et_reward_num.setText("")
                when (position) {
                    0 -> {
                        setView(recommendView)
                        recommendView!!.tv_reward_name.text = "推荐票"
                        recommendView!!.tv_user_recommend.text = "账户中还有 ${mUserAccountBean.recommendNumber} 推荐票，本次投 0 推荐票"
                    }
                    1 -> {
                        setView(recommendView)
                        recommendView!!.tv_reward_name.text = "月票"
                        recommendView!!.tv_user_recommend.text = "账户中还有 ${mUserAccountBean.monthRecommendNumber} 月票，本次投 0 月票"
                    }
                    2 -> {
                        setView(recommendView)
                        recommendView!!.tv_reward_name.text = "钻石"
                        recommendView!!.tv_user_recommend.text = "账户中还有 ${mUserAccountBean.diamondNumber} 钻石，本次投 0 钻石"
                    }
                    3 -> {
                        hintKeyboard()
                        setView(rewardView)
                        rewardView!!.tv_reward_detail.text = "账户余额 ${mUserAccountBean.goldNumber} 阅读币，本次打赏 ${list[readMoneySelected].money} 阅读币"
                        if (mUserAccountBean.goldNumber < 10) {
                            rewardView!!.tv_reward_done.text = "去充值"
                            rewardView!!.tv_reward_detail.text = "账户余额 ${mUserAccountBean.goldNumber} 阅读币，余额不足"
                            rewardView!!.setOnClickListener {
                                JumpManager.jumpRecharge(context)
                                dismiss()
                            }

                        }
                    }
                }
            }

            override fun onTabReselect(position: Int) {
            }
        })
    }

    private fun setView(view: View?) {
        views.forEach { it.visible(view == it) }
    }

}