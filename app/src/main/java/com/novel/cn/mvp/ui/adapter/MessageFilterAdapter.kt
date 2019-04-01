package com.novel.cn.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import kotlinx.android.synthetic.main.item_message_filter.view.*

class MessageFilterAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_message_filter) {

    private var onCheckChange: ((Boolean) -> Unit)? = null

    init {
        setOnItemClickListener { adapter, view, position ->
            select(position)
        }
    }

    private var mCheckPosition = -1

    override fun convert(helper: BaseViewHolder, item: String) {
        val tvTitle = helper.itemView.tv_title
        tvTitle.text = item
        tvTitle.isSelected = mCheckPosition == helper.adapterPosition
    }

    fun select(position: Int) {
        mCheckPosition = position
        notifyDataSetChanged()
        onCheckChange?.invoke(true)
    }

    /**
     * 选择状态改变监听
     */
    fun onCheckChange(method: ((Boolean) -> Unit)?) {
        this.onCheckChange = method
    }

    fun getCheckItem(): String? {
        // 0 是全部，服务器规定传 空字符串，其他的不变
        if (mCheckPosition !in setOf(-1, 0)) {
            return getItem(mCheckPosition)
        }
        return null
    }

}