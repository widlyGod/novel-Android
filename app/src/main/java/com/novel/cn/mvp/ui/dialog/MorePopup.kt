package com.novel.cn.mvp.ui.dialog

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import com.ess.filepicker.FilePicker
import com.novel.cn.R
import com.novel.cn.app.JumpManager
import com.novel.cn.app.click
import com.novel.cn.mvp.ui.activity.BookManagerActivity
import com.novel.cn.mvp.ui.activity.ReadRecordActivity
import kotlinx.android.synthetic.main.layout_bubble_popup.view.*
import org.jetbrains.anko.startActivity

class MorePopup constructor(context: Context) : PopupWindow(context) {

    init {
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        isOutsideTouchable = true
        isFocusable = true

        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = LayoutInflater.from(context).inflate(R.layout.layout_bubble_popup, null, false)
        contentView = view


        view.apply {
            click(tv_read_record, tv_manager, tv_auto_subscribe, tv_local_import) {
                when (it) {
                    tv_read_record -> context.startActivity<ReadRecordActivity>()
                    tv_manager -> JumpManager.jumpBookManager(context, 0)
                    tv_auto_subscribe -> JumpManager.jumpBookManager(context, 1)
                    tv_local_import -> {
                        FilePicker.from(context as Activity)
                                .chooseForBrowser()
                                .setMaxCount(1)
                                .setFileTypes("txt")
                                .requestCode(0x01)
                                .start()
                    }
                }
                dismiss()
            }
        }

    }


}