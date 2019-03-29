package com.novel.cn.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.mvp.model.entity.Book
import com.novel.cn.utils.TimeUtils
import kotlinx.android.synthetic.main.item_read_record.view.*

class ReadRecordAdapter : BaseQuickAdapter<Book, BaseViewHolder>(R.layout.item_read_record) {
    override fun convert(helper: BaseViewHolder, item: Book) {
        with(helper.itemView) {
            tv_bookName.text = item.novelTitle
            tv_time.text = TimeUtils.millis2String(item.readTime)
        }
    }

}