package com.novel.cn.mvp.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import com.flyco.dialog.widget.base.BaseDialog
import com.novel.cn.R
import com.novel.cn.mvp.model.entity.EconomizeBean
import com.novel.cn.mvp.ui.adapter.EconomizeAdapter
import kotlinx.android.synthetic.main.dialog_economize.*

class EconomizeDialog(context: Context) : BaseDialog<EconomizeDialog>(context) {

    private val mAdapter by lazy { EconomizeAdapter() }

    override fun setUiBeforShow() {

    }

    override fun onCreateView(): View {
        return layoutInflater.inflate(R.layout.dialog_economize, mLlControlHeight, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setCanceledOnTouchOutside(false)
        iv_close.setOnClickListener { dismiss() }
        tv_done.setOnClickListener { dismiss() }

        var list = mutableListOf<EconomizeBean>()
        list.add(EconomizeBean())
        list.add(EconomizeBean())
        list.add(EconomizeBean())
        list.add(EconomizeBean())
        mAdapter.setNewData(list)
        recyclerView.adapter = mAdapter
    }

}