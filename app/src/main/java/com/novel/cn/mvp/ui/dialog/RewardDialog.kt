package com.novel.cn.mvp.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import com.flyco.dialog.widget.base.BaseDialog
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.novel.cn.R
import com.novel.cn.app.visible
import com.novel.cn.mvp.model.entity.Reward
import com.novel.cn.mvp.model.entity.TabEntity
import com.novel.cn.mvp.ui.adapter.RewardAdapter
import kotlinx.android.synthetic.main.dialog_reward.*
import kotlinx.android.synthetic.main.layout_reward.*
import kotlinx.android.synthetic.main.layout_reward.view.*


class RewardDialog(context: Context) : BaseDialog<RewardDialog>(context) {

    private val title = arrayOf("推荐票", "月票", "钻石", "打赏")

    private var recommendView: View? = null

    private var rewardView: View? = null

    private val views = ArrayList<View>()

    init {
        this.window?.setDimAmount(0f)
        widthScale(0.85f)
    }

    override fun setUiBeforShow() {

    }

    override fun onCreateView(): View {
        return layoutInflater.inflate(R.layout.dialog_reward, null, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tabEntitys = ArrayList<CustomTabEntity>(title.size)
        title.forEach { tabEntitys.add(TabEntity(it, 0, 0)) }
        tabLayout.setTabData(tabEntitys)
        recommendView = layoutInflater.inflate(R.layout.dialog_reward_recommend, fl_content, false)
        rewardView = layoutInflater.inflate(R.layout.layout_reward, fl_content, false)
        val adapter = RewardAdapter()
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
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                when(position){
                    0->setView(recommendView)
                    1->setView(null)
                    2->setView(null)
                    3->setView(rewardView)
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