package com.novel.cn.mvp.ui.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.flyco.dialog.widget.base.BottomBaseDialog
import com.jakewharton.rxbinding3.widget.textChanges
import com.jess.arms.utils.DeviceUtils
import com.novel.cn.R
import com.novel.cn.ext.toast
import kotlinx.android.synthetic.main.dialog_comment.*

class CommentDialog(context: Context) : BottomBaseDialog<CommentDialog>(context) {

    private var onReleaseClickListener: ((content: String) -> Unit)? = null
    private var content = "我也说两句"

    init {

    }

    override fun onCreateView(): View {
        return layoutInflater.inflate(R.layout.dialog_comment, mLlControlHeight, false)
    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tv_back.setOnClickListener { dismiss() }

        tv_release.setOnClickListener {
            val content = et_content.text.toString()
            if (content.isEmpty()) {
                mContext.toast("请输入内容！")
                return@setOnClickListener
            }
            onReleaseClickListener?.invoke(content)
        }
        et_content.textChanges().subscribe{
            tv_text_num.text = "${et_content.text.toString().length}/50"
            if(it.isNotEmpty())
                tv_release.setTextColor(Color.parseColor("#000000"))
            else
                tv_release.setTextColor(Color.parseColor("#999999"))
        }
    }

    override fun onViewCreated(inflate: View?) {
        super.onViewCreated(inflate)
    }

    fun setOnReleaseClickListener(listener: (content: String) -> Unit) {
        this.onReleaseClickListener = listener
    }

    override fun showWithAnim() {
        //取消动画
        mInnerShowAnim = null
        super.showWithAnim()
    }

    override fun dismissWithAnim() {
        mInnerDismissAnim = null
        super.dismissWithAnim()
    }

    override fun setUiBeforShow() {
        et_content.setText("")
        et_content.hint = content
    }


    fun show(content: String) {
        this.content = content
        show()
    }

    override fun show() {
        //显示dialog时，自动打开软键盘
        DeviceUtils.showSoftKeyboard(this)
        super.show()
    }
}
