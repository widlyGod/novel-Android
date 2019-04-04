package com.novel.cn.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.app.loadImage
import com.novel.cn.mvp.model.entity.RankWeek
import kotlinx.android.synthetic.main.item_rank_child_horizontal.view.*

class RankChildAdapter : BaseQuickAdapter<RankWeek, BaseViewHolder>(R.layout.item_rank_child_horizontal) {
    override fun convert(helper: BaseViewHolder, item: RankWeek) {
        with(helper.itemView) {
            tv_book_name.text = item.novelTitle
            iv_book_image.loadImage(item.novelPhoto)
        }
    }
}
