package com.novel.cn.mvp.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import com.flyco.dialog.widget.base.BaseDialog
import com.novel.cn.R
import kotlinx.android.synthetic.main.dialog_update_gender.*

class UpdateGenderDialog(context: Context, var gender: Int? = 0) : BaseDialog<UpdateGenderDialog>(context, true) {


    private var onSelectGenderListener: ((gender: Int) -> Unit)? = null

    override fun onCreateView(): View {
        return layoutInflater.inflate(R.layout.dialog_update_gender, mLlControlHeight, false)
    }

    fun setOnSelectGenderListener(listener: ((gender: Int) -> Unit)?) {
        this.onSelectGenderListener = listener
    }


    override fun setUiBeforShow() {

        iv_male.setOnClickListener {
            gender = 0
            it.isEnabled = false
            iv_famale.isEnabled = true
            onSelectGenderListener?.invoke(gender!!)
        }
        iv_famale.setOnClickListener {
            gender != 0
            it.isEnabled = false
            iv_male.isEnabled = true
            onSelectGenderListener?.invoke(gender!!)
        }

        iv_male.isEnabled = gender != 0
    }

}
