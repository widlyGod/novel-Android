package com.novel.cn.mvp.ui.adapter

import android.graphics.Color
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.mvp.model.entity.PageChapterBean
import com.novel.cn.mvp.model.entity.VolumeBean

class PageChooseAdapter : BaseQuickAdapter<PageChapterBean, BaseViewHolder>(R.layout.item_volume) {

    private var currentPosition = 0

    override fun convert(helper: BaseViewHolder, item: PageChapterBean) {
        (helper.itemView as TextView).text = "第${item.startChapter}章 - 第${item.endChapter}章"
        (helper.itemView as TextView).setBackgroundColor(if (currentPosition == helper.adapterPosition) Color.parseColor("#e3f1ff") else mContext.resources.getColor(R.color.white))
    }

    fun setCurrentPosition(position: Int) {
        currentPosition = position
        notifyDataSetChanged()
    }
}