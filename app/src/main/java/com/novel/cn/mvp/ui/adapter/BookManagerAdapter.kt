package com.novel.cn.mvp.ui.adapter

import com.chad.library.adapter.base.BaseItemDraggableAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.app.loadImage
import com.novel.cn.mvp.model.entity.Book
import kotlinx.android.synthetic.main.item_book_manager.view.*
import java.util.*

class BookManagerAdapter : BaseItemDraggableAdapter<Book, BaseViewHolder>(R.layout.item_book_manager, mutableListOf<Book>()) {
    private val mCheckList by lazy { LinkedList<String>() }

    private var onCheckChange: ((Int) -> Unit)? = null


    init {

        setOnItemClickListener { adapter, view, position ->
            val book = adapter.getItem(position) as Book
            val isCheck = isCheck(book.novelId)
            if (isCheck)
                mCheckList.remove(book.novelId)
            else
                mCheckList.add(book.novelId)
            notifyItemChanged(position)
            this.onCheckChange?.invoke(mCheckList.size)
        }
    }


    override fun convert(helper: BaseViewHolder, item: Book) {
        with(helper.itemView) {
            iv_book_image.loadImage(item.novelPoto)
            tv_book_name.text = item.novelTitle
            tv_update.text = if (item.isRecord) "更新至第${item.newChapter}章" else "未读"
            iv_check.isSelected = isCheck(item.novelId)
        }
    }

    private fun isCheck(id: String): Boolean {
        mCheckList.forEach {
            if (id == it)
                return true
        }
        return false
    }

    fun getCheckList(): LinkedList<String> {
        return mCheckList
    }

    /**
     * 选择状态改变监听
     */
    fun onCheckChange(method: ((Int) -> Unit)?) {
        this.onCheckChange = method
    }

    /**
     * 全选
     */
    fun checkAll() {
        if (mCheckList.size < data.size) {
            mCheckList.clear()
            data.forEach {
                mCheckList.add(it.novelId)
            }
        }else{
            mCheckList.clear()
        }
        notifyDataSetChanged()
        this.onCheckChange?.invoke(mCheckList.size)
    }

    fun cleanCheck() {

        val iterator = mData.iterator()
        while (iterator.hasNext()) {
            val book = iterator.next()
            mCheckList.forEach {
                if (book.novelId == it)
                    iterator.remove()
            }
        }
        mCheckList.clear()
        notifyDataSetChanged()
        this.onCheckChange?.invoke(mCheckList.size)
    }
}