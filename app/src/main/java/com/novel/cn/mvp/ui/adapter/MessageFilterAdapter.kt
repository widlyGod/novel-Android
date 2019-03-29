package com.novel.cn.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import kotlinx.android.synthetic.main.item_message_filter.view.*

class MessageFilterAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_message_filter) {
    override fun convert(helper: BaseViewHolder, item: String?) {
        helper.itemView.tv_title.text = item
    }

}