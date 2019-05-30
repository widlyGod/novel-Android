package com.novel.cn.mvp.ui.dialog

import android.content.Context
import android.view.View
import com.novel.cn.R
import com.novel.cn.ext.clicks
import com.novel.cn.mvp.contract.ContentsContract
import com.novel.cn.mvp.model.entity.Calalogue
import com.novel.cn.mvp.model.entity.PageChapterBean
import com.novel.cn.mvp.ui.adapter.PageChooseAdapter
import kotlinx.android.synthetic.main.layout_volume_popup.view.*
import razerdp.basepopup.BasePopupWindow
import java.util.concurrent.TimeUnit

class PageChoosePopup(context: Context, private val readContractView: ContentsContract.View) : BasePopupWindow(context) {
    override fun onCreateContentView(): View {
        return createPopupById(R.layout.layout_volume_popup)
    }

    private val mAdapter by lazy { PageChooseAdapter() }
    private var PageChapters = mutableListOf<PageChapterBean>()


    init {
        contentView.apply {
            recyclerView.adapter = mAdapter
            mAdapter.setNewData(PageChapters)
            mAdapter.clicks()
                    .throttleFirst(1, TimeUnit.SECONDS)
                    .subscribe {
                        readContractView.selectPage(it.second)
                        mAdapter.setCurrentPosition(it.second)
                        dismiss()
                    }
        }
    }

    fun setData(data: MutableList<Calalogue>) {
        PageChapters.clear()
        for (i in 0 until data.size - 1 step 100) {
            if (i != 0)
                PageChapters.add(PageChapterBean(data[i - 100].chapter, data[i - 1].chapter))
        }
        if (data.size % 100 != 0) {
            if (PageChapters.size > 0) {
                PageChapters.add(PageChapterBean(data[data.size - data.size % 100].chapter, data[data.size - 1].chapter))
            } else {
                PageChapters.add(PageChapterBean(data[0].chapter, data[data.size - 1].chapter))
            }
        }
        mAdapter.setNewData(PageChapters)
        mAdapter.setCurrentPosition(0)
        mAdapter.notifyDataSetChanged()
    }

    fun setCurrentPosition(position: Int) {
        mAdapter.setCurrentPosition(position)
        mAdapter.notifyDataSetChanged()
    }
}