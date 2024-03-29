package com.novel.cn.mvp.ui.adapter

import android.view.Gravity
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.flyco.tablayout.utils.UnreadMsgUtils
import com.novel.cn.R
import com.novel.cn.app.loadImage
import com.novel.cn.app.visible
import com.novel.cn.mvp.model.entity.Book
import kotlinx.android.synthetic.main.item_bookshelf.view.*
import java.util.*

class BookshelfAdapter : BaseQuickAdapter<Book, BaseViewHolder>(R.layout.item_bookshelf) {


    override fun convert(helper: BaseViewHolder, item: Book) {

        with(helper.itemView) {
            iv_book_image.loadImage(item.novelPoto)
            tv_book_name.text = item.novelTitle
            if (item.isLocal)
                tv_update.text = ""
            else
                tv_update.text = if (item.isRecord) "更新至第${item.newChapter}章" else "未读"
            tv_noRead.text = if (item.noReadNum < 100) item.noReadNum.toString() else "99+"
            tv_noRead.visible(item.noReadNum > 0)

        }
    }

}