package com.novel.cn.mvp.ui.adapter

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.db.SearchHistory

class SearchRecordAdapter:BaseQuickAdapter<SearchHistory,BaseViewHolder>(R.layout.item_search_record){
    override fun convert(helper: BaseViewHolder, item: SearchHistory) {
        (helper.itemView as TextView).text = item.text
    }
}
