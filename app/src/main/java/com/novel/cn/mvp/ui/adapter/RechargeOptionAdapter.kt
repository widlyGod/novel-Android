package com.novel.cn.mvp.ui.adapter

import android.widget.FrameLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.mvp.model.entity.Recharge
import kotlinx.android.synthetic.main.item_recharge_opstion.view.*

class RechargeOptionAdapter : BaseQuickAdapter<Recharge, BaseViewHolder>(R.layout.item_recharge_opstion) {

    init {
        setOnItemClickListener { adapter, view, position ->
            selectedPosition = position
            notifyDataSetChanged()
        }
    }

    private var selectedPosition = 0

    override fun convert(helper: BaseViewHolder, item: Recharge) {
        helper.itemView.isSelected = selectedPosition == helper.adapterPosition
        helper.itemView.tv_money.text = item.price
        helper.itemView.tv_bi.text = "${item.readNumber}阅读币"

        helper.itemView.tv_quan.text = if (item.quan.isEmpty()) "" else "+${item.quan}阅读券"
    }

    fun getSelectedItem(): Recharge {
        return data[selectedPosition]
    }

}