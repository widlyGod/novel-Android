package com.novel.cn.mvp.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.view.readpage.PageStyle
import com.novel.cn.view.readpage.ReadSettingManager

class WallpaperAdapter : BaseQuickAdapter<PageStyle, BaseViewHolder>(R.layout.item_page_style) {
    override fun convert(helper: BaseViewHolder, item: PageStyle) {

        val imageView = helper.itemView as ImageView
        val pageStyle = ReadSettingManager.getInstance().pageStyle
        if (pageStyle == item) {
            imageView.setImageResource(item.thumbnailCheck)
        } else {
            imageView.setImageResource(item.thumbnail)
        }
    }
}