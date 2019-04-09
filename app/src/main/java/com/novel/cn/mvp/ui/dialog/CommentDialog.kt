package com.novel.cn.mvp.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import com.flyco.dialog.widget.base.BottomBaseDialog
import com.jess.arms.utils.DeviceUtils
import com.novel.cn.R
import kotlinx.android.synthetic.main.dialog_comment.*

class CommentDialog(context: Context) : BottomBaseDialog<CommentDialog>(context) {

    init {
    }

    override fun onCreateView(): View {
        return layoutInflater.inflate(R.layout.dialog_comment, mLlControlHeight, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tv_back.setOnClickListener {
            dismiss()
        }
    }

    override fun showWithAnim() {
        mInnerShowAnim = null
        super.showWithAnim()
    }

    override fun setUiBeforShow() {

    }


    override fun show() {
        DeviceUtils.showSoftKeyboard(this)
        super.show()
    }


}
