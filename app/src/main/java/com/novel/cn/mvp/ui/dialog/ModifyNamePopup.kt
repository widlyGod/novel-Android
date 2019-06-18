package com.novel.cn.mvp.ui.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputFilter
import android.text.Selection
import android.text.Spanned
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.novel.cn.R
import com.novel.cn.ext.toast
import com.novel.cn.mvp.contract.UserInfoContract
import kotlinx.android.synthetic.main.popup_slide_from_bottom_with_input_name.view.*
import razerdp.basepopup.BasePopupWindow
import java.util.concurrent.TimeUnit

class ModifyNamePopup(context: Context, private val userInfoContractView: UserInfoContract.View) : BasePopupWindow(context) {

    init {
        popupGravity = Gravity.BOTTOM
        bindView()
        setAutoShowInputMethod(contentView.ed_input, true)
    }

    override fun onCreateContentView(): View {
        return createPopupById(R.layout.popup_slide_from_bottom_with_input_name)
    }

    override fun onCreateShowAnimation(): Animation {
        return getTranslateVerticalAnimation(1f, 0f, 350)
    }

    override fun onCreateDismissAnimation(): Animation {
        return getTranslateVerticalAnimation(0f, 1f, 350)
    }

    @SuppressLint("CheckResult")
    private fun bindView() {
        var maxLen = 20
        contentView.ed_input.filters = arrayOf(object : InputFilter.LengthFilter(20) {})
        contentView.ed_input.textChanges().subscribe {
            var editable = contentView.ed_input.text
            var len = editable.length

            if (len >= maxLen) {
                toast("昵称不能超过20字")
                //设置新光标所在的位置
                Selection.setSelection(editable, editable.length)
            }
        }
        contentView.btn_send.clicks().throttleFirst(1, TimeUnit.SECONDS).subscribe {
            if (contentView.ed_input.text.isBlank()) {
                toast("昵称不能为空！")
            } else {
                userInfoContractView.modifyName(contentView.ed_input.text.toString())
                dismiss()
            }

        }

    }

}