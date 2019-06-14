package com.novel.cn.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.app.isVisible
import com.novel.cn.app.loadImage
import com.novel.cn.app.visible
import com.novel.cn.mvp.model.entity.CategoryBook
import com.novel.cn.mvp.model.entity.RankWeek
import kotlinx.android.synthetic.main.item_rank_list.view.*

class CategoryBookAdapter : BaseQuickAdapter<CategoryBook, BaseViewHolder>(R.layout.item_rank_list) {

    private var onConllectClickListener: ((Int) -> Unit)? = null

    override fun convert(helper: BaseViewHolder, item: CategoryBook) {
        with(helper.itemView) {
            iv_book_image.loadImage(item.novelPhoto)
            tv_book_name.text = item.novelTitle
            tv_author.text = item.writer
//            tv_desc.text = item.novelDescribe
            iv_collect.setOnClickListener { onConllectClickListener?.invoke(helper.adapterPosition) }
            iv_collect.visible(item.isCollect != "true")
        }

    }

    fun setOnConllectClickListener(listener: ((Int) -> Unit)?) {
        this.onConllectClickListener = listener
    }
}