package com.novel.cn.mvp.ui.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.app.JumpManager
import com.novel.cn.ext.setVisible
import com.novel.cn.mvp.model.entity.RankBean
import com.novel.cn.mvp.model.entity.RankResult
import com.novel.cn.mvp.model.entity.RankWeek
import kotlinx.android.synthetic.main.header_rank.view.*
import kotlinx.android.synthetic.main.item_rank.view.*
import kotlin.random.Random

class RankAdapter : BaseQuickAdapter<RankBean, BaseViewHolder>(R.layout.item_rank) {

    private val colors = arrayOf(0xFF74A7E2, 0xFFFFC125, 0xFFFFC0CB, 0xFF00FA9A, 0xFFAB82FF, 0xFF00FA9A,
            0xFF74A7E2, 0xFFFFC125, 0xFFFFC0CB, 0xFFE0FFFF, 0xFFAB82FF, 0xFF00FA9A,
            0xFF74A7E2, 0xFFFFC125, 0xFFFFC0CB, 0xFFE0FFFF, 0xFFAB82FF, 0xFF00FA9A)


    val images = HashMap<String, Array<Int>>()

    init {
//        images["ALL"] = arrayOf(R.drawable.rank_month)
//        images["RECOMMEND"] = arrayOf(R.drawable.rank_month)
        images["CLICK"] = arrayOf(R.drawable.rank_click)
        images["SUBANDCOL"] = arrayOf(R.drawable.rank_subcribe)
        images["FINISH"] = arrayOf(R.drawable.rank_over)
//        images["UPDATE"] = arrayOf(R.drawable.rank_month)
        images["NEW"] = arrayOf(R.drawable.rank_new)
//        images["DIAMOND"] = arrayOf(R.drawable.rank_month)
        images["READING"] = arrayOf(R.drawable.rank_pc)
        images["MONTHLY"] = arrayOf(R.drawable.rank_month)

    }

    override fun setNewData(data: MutableList<RankBean>?) {
        var list = mutableListOf<RankBean>()
        data?.forEach {
            if (images[it.code] != null)
                list.add(it)
        }
        super.setNewData(list)
    }

    override fun convert(helper: BaseViewHolder, item: RankBean) {

        with(helper.itemView) {
            setBackgroundColor(colors[helper.adapterPosition].toInt())
            val adapter = RankChildAdapter()
            adapter.setOnItemClickListener { a, view, position ->
                val info = adapter.getItem(position) as RankWeek
                JumpManager.jumpBookDetail(mContext, info.novelId)
            }
            this.recyclerView.adapter = adapter
            //解决切换页面，再次回来时，会自动滚动的问题
            this.recyclerView.isFocusableInTouchMode = false
            this.recyclerView.requestFocus()
            //添加头部和尾部
            val header = LayoutInflater.from(mContext).inflate(R.layout.header_rank, recyclerView, false)
            val footer = LayoutInflater.from(mContext).inflate(R.layout.footer_rank, recyclerView, false)
            adapter.addHeaderView(header, -1, LinearLayout.HORIZONTAL)
            adapter.addFooterView(footer, -1, LinearLayout.HORIZONTAL)

            images[item.code]?.get(0)?.let { header.iv_rank.setImageResource(it) }
            header.setOnClickListener {
                JumpManager.toRankList(mContext, item.code, item.name)
            }
            footer.setOnClickListener {
                JumpManager.toRankList(mContext, item.code, item.name)
            }
            adapter.setNewData(item.result.week)
        }
    }
}