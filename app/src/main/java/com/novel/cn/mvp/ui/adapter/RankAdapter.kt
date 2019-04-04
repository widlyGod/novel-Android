package com.novel.cn.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.mvp.model.entity.RankResult
import kotlinx.android.synthetic.main.item_rank.view.*
import kotlin.random.Random

class RankAdapter : BaseQuickAdapter<RankResult, BaseViewHolder>(R.layout.item_rank) {

    private val colors = arrayOf(0xFF74A7E2, 0xFFFFC125, 0xFFFFC0CB, 0xFFE0FFFF, 0xFFAB82FF, 0xFF00FA9A,
            0xFF74A7E2, 0xFFFFC125, 0xFFFFC0CB, 0xFFE0FFFF, 0xFFAB82FF, 0xFF00FA9A,
            0xFF74A7E2, 0xFFFFC125, 0xFFFFC0CB, 0xFFE0FFFF, 0xFFAB82FF, 0xFF00FA9A)

    override fun convert(helper: BaseViewHolder, item: RankResult) {

        with(helper.itemView) {
            setBackgroundColor(colors[helper.adapterPosition].toInt())
            val adapter = RankChildAdapter()
            this.recyclerView.adapter = adapter
            adapter.setNewData(item.week)
        }
    }
}