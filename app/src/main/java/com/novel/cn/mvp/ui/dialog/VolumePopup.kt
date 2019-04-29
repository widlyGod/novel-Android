package com.novel.cn.mvp.ui.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.PopupWindow
import com.novel.cn.R
import com.novel.cn.ext.dp2px
import com.novel.cn.mvp.model.entity.Volume
import com.novel.cn.mvp.ui.adapter.VolumeAdapter
import kotlinx.android.synthetic.main.layout_volume_popup.view.*

class VolumePopup(context: Context) : PopupWindow(context) {

    private val mAdapter by lazy { VolumeAdapter() }

    private var mData = ArrayList<Volume>()

    fun setData(data: MutableList<Volume>?) {
        mAdapter.setNewData(data)
        mAdapter.notifyDataSetChanged()
    }

    init {
        height = context.dp2px(163)
        width = context.dp2px(152)
        isOutsideTouchable = true
        isFocusable = true

        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = LayoutInflater.from(context).inflate(R.layout.layout_volume_popup, null, false)
        contentView = view


        view.apply {
            recyclerView.adapter = mAdapter
            mAdapter.setNewData(mData)
        }

    }

}
