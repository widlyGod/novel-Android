package com.novel.cn.mvp.ui.adapter

import android.graphics.Color
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.mvp.model.entity.Volume
import com.novel.cn.mvp.model.entity.VolumeBean

class VolumeAdapter : BaseQuickAdapter<VolumeBean, BaseViewHolder>(R.layout.item_volume) {

    private var currentPosition = 0

    override fun convert(helper: BaseViewHolder, item: VolumeBean) {
        (helper.itemView as TextView).text = item.volumeName
        (helper.itemView as TextView).setBackgroundColor(if(currentPosition == helper.adapterPosition) Color.parseColor("#e3f1ff") else mContext.resources.getColor(R.color.white))
    }

    fun setCurrentPosition(position: Int) {
        currentPosition = position
        notifyDataSetChanged()
    }
}