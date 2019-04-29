package com.novel.cn.mvp.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.mvp.model.entity.Volume

class VolumeAdapter:BaseQuickAdapter<Volume,BaseViewHolder>(R.layout.item_volume){
    override fun convert(helper: BaseViewHolder, item: Volume) {
        (helper.itemView as TextView).text = item.title
    }
}