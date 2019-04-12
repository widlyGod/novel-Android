package com.novel.cn.mvp.ui.adapter

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.flyco.roundview.RoundFrameLayout
import com.novel.cn.R
import com.novel.cn.view.read.PageStyle

class WallpaperAdapter : BaseQuickAdapter<PageStyle, BaseViewHolder>(R.layout.item_page_style) {
    override fun convert(helper: BaseViewHolder, item: PageStyle) {
        (helper.itemView as RoundFrameLayout).delegate.backgroundColor = ContextCompat.getColor(mContext, item.bgColor)
    }
}