package com.novel.cn.mvp.ui.dialog

import android.content.Context
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.flyco.dialog.widget.base.BaseDialog
import com.novel.cn.R
import com.flyco.animation.SlideExit.SlideRightExit
import com.flyco.animation.SlideEnter.SlideRightEnter
import com.jess.arms.utils.ArmsUtils.getScreenHeidth
import android.R.attr.gravity
import android.graphics.Color
import com.jess.arms.utils.DataHelper
import android.opengl.ETC1.getWidth
import android.os.Build
import android.view.*
import com.jess.arms.utils.ArmsUtils
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.Gravity
import android.view.WindowManager









class MessageFilterDialog(context: Context) : BaseDialog<MessageFilterDialog>(context) {

    init {
        this.widthScale(0.8f)
        this.heightScale(1f)
        this.showAnim(SlideRightEnter())
        this.dismissAnim(SlideRightExit())

    }

    override fun onCreateView(): View {
        return layoutInflater.inflate(R.layout.dialog_message_filter, null, false)
    }

    override fun setUiBeforShow() {

    }


    override fun show() {
        super.show()

//        window.setLayout(mDisplayMetrics.widthPixels,mDisplayMetrics.heightPixels)

//        mLlTop.layoutParams = FrameLayout.LayoutParams(mDisplayMetrics.widthPixels,mDisplayMetrics.heightPixels)

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        val lp = window!!.attributes
        lp.gravity = Gravity.TOP and Gravity.RIGHT
        lp.height = mDisplayMetrics.heightPixels
        mLlTop.setBackgroundColor(-0xffffff)
    }
}