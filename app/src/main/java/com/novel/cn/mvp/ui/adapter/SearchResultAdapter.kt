package com.novel.cn.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.app.JumpManager
import com.novel.cn.app.loadImage
import com.novel.cn.mvp.model.entity.SearchInfo
import kotlinx.android.synthetic.main.item_search_result.view.*

class SearchResultAdapter : BaseQuickAdapter<SearchInfo, BaseViewHolder>(R.layout.item_search_result) {
    override fun convert(helper: BaseViewHolder, item: SearchInfo) {
        with(helper.itemView) {
            iv_book_image.loadImage(item.photoContent)
            tv_book_name.text = item.title
        }
    }
}