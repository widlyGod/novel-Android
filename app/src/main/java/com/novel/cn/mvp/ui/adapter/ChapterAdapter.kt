package com.novel.cn.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.mvp.model.entity.ChapterInfo
import kotlinx.android.synthetic.main.item_chapter.view.*

class ChapterAdapter:BaseQuickAdapter<ChapterInfo,BaseViewHolder>(R.layout.item_chapter){

    override fun convert(helper: BaseViewHolder, item: ChapterInfo) {
        with(helper.itemView){
            tv_chapter.text = item.title
        }
    }
}