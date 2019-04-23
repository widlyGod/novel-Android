package com.novel.cn.mvp.ui.dialog

import android.app.Activity
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import com.flyco.dialog.widget.base.BottomBaseDialog
import com.jess.arms.utils.ArmsUtils
import com.novel.cn.R
import com.novel.cn.app.click
import com.novel.cn.mvp.ui.adapter.WallpaperAdapter
import com.novel.cn.utils.ScreenUtils
import com.novel.cn.view.readpage.PageStyle
import com.novel.cn.view.readpage.PageMode
import com.novel.cn.view.readpage.ReadSettingManager
import kotlinx.android.synthetic.main.layout_read_setting.*

class ReadSettingDialog(val activity: Activity) : BottomBaseDialog<ReadSettingDialog>(activity) {


    private val list = ArrayList<PageStyle>()
    private val mAdapter by lazy { WallpaperAdapter() }
    private var onSettingChangeListener: OnSettingChangeListener? = null
    private var chapterCount = 1
    private var curPosition = 1

    init {
        list.add(PageStyle.BG_0)
        list.add(PageStyle.BG_1)
        list.add(PageStyle.BG_2)
        list.add(PageStyle.BG_3)
        list.add(PageStyle.NIGHT)

        dimEnabled(false)

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
        tv_textsize.text = ScreenUtils.pxToSp(ReadSettingManager.getInstance().textSize).toString()

        click(tv_add, tv_minus) {
            when (it) {
                tv_add -> {
                    var textSize = ScreenUtils.pxToSp(ReadSettingManager.getInstance().textSize)
                    if (textSize >= 30) {
                        ArmsUtils.makeText(mContext, "已经是最大字体了")
                        return@click
                    }
                    textSize += 1
                    if (textSize > 30) {
                        textSize = 30
                    }
                    saveTextSize(textSize)
                }
                tv_minus -> {
                    var textSize = ScreenUtils.pxToSp(ReadSettingManager.getInstance().textSize)
                    if (textSize <= 8) {
                        ArmsUtils.makeText(mContext, "已经是最小字体了")
                        return@click
                    }
                    textSize -= 1
                    if (textSize < 8) {
                        textSize = 8
                    }
                    saveTextSize(textSize)
                }
            }
        }
    }

    override fun setUiBeforShow() {

    }


    private fun saveTextSize(sp: Int) {
        val textSize = ScreenUtils.spToPx(sp)
        tv_textsize.text = sp.toString()
        ReadSettingManager.getInstance().textSize = textSize
        onSettingChangeListener?.onTextSize(textSize)

    }

    fun setOnSettingChanageListener(listener: OnSettingChangeListener) {
        this.onSettingChangeListener = listener
    }


    interface OnSettingChangeListener {
        fun onPageStyle(style: PageStyle)

        fun onPageMode(mode: PageMode)

        fun onTextSize(textSize: Int)
    }

}