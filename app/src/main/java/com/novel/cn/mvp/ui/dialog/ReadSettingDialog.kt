package com.novel.cn.mvp.ui.dialog

import android.app.Activity
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.flyco.dialog.widget.base.BottomBaseDialog
import com.jess.arms.utils.ArmsUtils
import com.novel.cn.R
import com.novel.cn.mvp.ui.adapter.WallpaperAdapter
import com.novel.cn.view.read.PageStyle
import kotlinx.android.synthetic.main.layout_read_setting.*

class ReadSettingDialog(val activity: Activity) : BottomBaseDialog<ReadSettingDialog>(activity) {


    private val list = ArrayList<PageStyle>()
    private val mAdapter by lazy { WallpaperAdapter() }


    init {
        list.add(PageStyle.BG_0)
        list.add(PageStyle.BG_1)
        list.add(PageStyle.BG_2)
        list.add(PageStyle.BG_3)
        list.add(PageStyle.BG_NIGHT)

        dimEnabled(false)

    }

    override fun setUiBeforShow() {

    }

    override fun onCreateView(): View {
        return layoutInflater.inflate(R.layout.layout_read_setting, null, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recyclerView.adapter = mAdapter
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                val count = mAdapter.itemCount
                val position = parent.getChildAdapterPosition(view)
                var right = 0
                if (position < count - 1) {
                    right = ArmsUtils.dip2px(activity, 25f)
                }
                outRect.set(0, 0, right, 0)
            }
        })
        mAdapter.setNewData(list)
    }

}