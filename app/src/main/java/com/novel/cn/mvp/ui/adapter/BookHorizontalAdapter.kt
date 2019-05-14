package com.novel.cn.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.app.JumpManager
import com.novel.cn.app.loadImage
import com.novel.cn.mvp.model.entity.BookInfo
import kotlinx.android.synthetic.main.item_banner_child.view.*

class BookHorizontalAdapter : BaseQuickAdapter<BookInfo, BaseViewHolder>(R.layout.item_banner_child) {
    init {
        setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as BookInfo
            JumpManager.jumpBookDetail(mContext, item.novelId)
        }

    }
    override fun convert(helper: BaseViewHolder, item: BookInfo) {

        with(helper.itemView) {
            iv_book_image2.loadImage(item.photoContent)
            tv_title.text = item.novelTitle
            tv_author.text = item.penName
            tv_desc.text = item.novelDescribe
        }

    }
}
