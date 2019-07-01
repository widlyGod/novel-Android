package com.novel.cn.mvp.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import com.flyco.dialog.widget.base.BaseDialog
import com.novel.cn.R
import kotlinx.android.synthetic.main.dialog_recharge.*

class RechargeDialog(context: Context, val content: String) : BaseDialog<RechargeDialog>(context) {

    override fun setUiBeforShow() {

    }

    override fun onCreateView(): View {
        return layoutInflater.inflate(R.layout.dialog_recharge, mLlControlHeight, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCanceledOnTouchOutside(false)
        rl_dg_main.setOnClickListener { dismiss() }

        tv_content.text = content
    }

}