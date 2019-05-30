package com.novel.cn.mvp.ui.adapter

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.utils.LogUtils
import com.novel.cn.R
import com.novel.cn.app.visible
import com.novel.cn.mvp.model.entity.Calalogue
import com.novel.cn.mvp.model.entity.ChapterInfo
import kotlinx.android.synthetic.main.item_chapter.view.*

class ChapterAdapter : BaseQuickAdapter<Calalogue, BaseViewHolder>(R.layout.item_chapter) {


    private var currentPosition = 0
    private var isCurrentPositionShow = true

    override fun convert(helper: BaseViewHolder, item: Calalogue) {
        with(helper.itemView) {
            tv_chapter.text = "第${item.chapter}章${item.chapterTitle}"
            //添加了个头部，所以要减去
            if (isCurrentPositionShow) {
                val isCurrentChapter = currentPosition == helper.layoutPosition - headerLayoutCount
                iv_location.visible(isCurrentChapter)
                tv_chapter.setTextColor(ContextCompat.getColor(mContext, if (isCurrentChapter) R.color.color_5e8fca else R.color.color_999999))
                iv_free.visible(!isCurrentChapter && !item.isFree && !item.isLocked)
            } else {
                iv_location.visible(false)
                tv_chapter.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999))
                iv_free.visible(!item.isFree && !item.isLocked)
            }
        }
    }

    fun setCurrentPosition(position: Int) {
        isCurrentPositionShow = true
        this.currentPosition = position
        notifyDataSetChanged()
//        recyclerView.layoutManager?.scrollToPosition(position)
    }

    fun isCurrentPositionShow(isShow: Boolean) {
        isCurrentPositionShow = isShow
        notifyDataSetChanged()
    }

    fun getCurrentChapter(): Calalogue? {
        if (this.data.size > 0) {
            return getItem(currentPosition)
        } else {
            return null
        }
    }
}