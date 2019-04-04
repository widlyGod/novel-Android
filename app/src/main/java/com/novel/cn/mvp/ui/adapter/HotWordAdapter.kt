package com.novel.cn.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.google.gson.Gson
import com.jess.arms.utils.LogUtils
import com.novel.cn.R
import com.novel.cn.mvp.model.entity.BookInfo
import kotlinx.android.synthetic.main.item_hot_words.view.*

class HotWordAdapter:BaseQuickAdapter<BookInfo,BaseViewHolder>(R.layout.item_hot_words){
    override fun convert(helper: BaseViewHolder, item: BookInfo) {
        helper.itemView.tv_title.text = item.novelTitle


    }
}