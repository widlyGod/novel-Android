package com.novel.cn.mvp.ui.dialog

import kotlinx.android.synthetic.main.layout_bubble_circle_popup.view.*
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupWindow
import com.novel.cn.R
import com.novel.cn.app.JumpManager
import com.novel.cn.app.click
import com.novel.cn.mvp.ui.activity.ReadRecordActivity
import org.jetbrains.anko.startActivity

class CircleMorePopup constructor(context: Context) : PopupWindow(context) {


    init {
        height = ViewGroup.LayoutParams.WRAP_CONTENT
        width = ViewGroup.LayoutParams.WRAP_CONTENT
        isOutsideTouchable = true
        isFocusable = true

        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = LayoutInflater.from(context).inflate(R.layout.layout_bubble_circle_popup, null, false)
        contentView = view


        view.apply {
            click(tv_more_text, tv_more_image, tv_more_comment) {
                dismiss()
                when (it) {
                    tv_more_text -> JumpManager.toPublish(context, 0)
                    tv_more_image -> JumpManager.toPublish(context, 1)
                    tv_more_comment -> JumpManager.toPublish(context, 2)
                }
            }
        }
    }

}
