package com.novel.cn.mvp.ui.adapter

import android.widget.FrameLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R

class RechargeOptionAdapter : BaseQuickAdapter<Any, BaseViewHolder>(R.layout.item_recharge_opstion) {

    init {
        setOnItemClickListener { adapter, view, position ->
            selectedPosition = position
            notifyDataSetChanged()
        }
    }

    private var selectedPosition = 0

    override fun convert(helper: BaseViewHolder, item: Any?) {

        helper.itemView.isSelected = selectedPosition == helper.adapterPosition

    }

    fun getSelectedItem(){

    }

}