package com.novel.cn.mvp.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import com.flyco.dialog.widget.base.BottomBaseDialog
import com.jess.arms.utils.DeviceUtils
import com.novel.cn.R
import kotlinx.android.synthetic.main.dialog_comment.*

class CommentDialog(context: Context) : BottomBaseDialog<CommentDialog>(context) {

    private var onReleaseClickListener: (() -> Unit)? = null

    override fun onCreateView(): View {
        return layoutInflater.inflate(R.layout.dialog_comment, mLlControlHeight, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tv_back.setOnClickListener { dismiss() }

        tv_release.setOnClickListener { onReleaseClickListener?.invoke() }
    }

    fun setOnReleaseClickListener(listener: () -> Unit) {
        this.onReleaseClickListener = listener
    }

    override fun showWithAnim() {
        //取消动画
        mInnerShowAnim = null
        super.showWithAnim()
    }

    override fun setUiBeforShow() {

    }


    override fun show() {
        //显示dialog时，自动打开软键盘
        DeviceUtils.showSoftKeyboard(this)
        super.show()
    }


}
