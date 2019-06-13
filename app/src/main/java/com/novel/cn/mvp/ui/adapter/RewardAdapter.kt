package com.novel.cn.mvp.ui.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.utils.LogUtils
import com.novel.cn.R
import com.novel.cn.app.visible
import com.novel.cn.mvp.model.entity.Reward
import kotlinx.android.synthetic.main.item_reward.view.*

class RewardAdapter : BaseQuickAdapter<Reward, BaseViewHolder>(R.layout.item_reward) {

    private var mSelectedPosition = 0
    private var userMoney = 0F

    override fun convert(helper: BaseViewHolder, item: Reward) {
        with(helper.itemView) {
            tv_money.text = "${item.money} 阅读币"
            tv_diamond.text = "赠${item.diamond}颗钻石"
            tv_diamond.visible(item.diamond != 0)
            this.isSelected = mSelectedPosition == helper.adapterPosition
            if (userMoney <= item.money.toInt()) {
                LogUtils.warnInfo(item.money)
                this.tv_money.setTextColor(Color.parseColor("#999999"))
            }else{
                this.tv_money.setTextColor(Color.parseColor("#666666"))
            }
            if (userMoney < 10) {
                this.isSelected = false
            }

        }
    }

    fun setSelect(position: Int) {
        mSelectedPosition = position
        notifyDataSetChanged()
    }

    fun setUserMoney(money: Float) {
        userMoney = money
        notifyDataSetChanged()
    }
}