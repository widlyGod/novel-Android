package com.novel.cn.mvp.ui.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputFilter
import android.text.Selection
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

class ModifySignaturePopup(context: Context, private val userInfoContractView: UserInfoContract.View) : BasePopupWindow(context) {

    init {
        popupGravity = Gravity.BOTTOM
        bindView()
        setAutoShowInputMethod(contentView.ed_input, true)
    }

    override fun onCreateContentView(): View {
        return createPopupById(R.layout.popup_slide_from_bottom_with_input_signature)
    }

    override fun onCreateShowAnimation(): Animation {
        return getTranslateVerticalAnimation(1f, 0f, 350)
    }

    override fun onCreateDismissAnimation(): Animation {
        return getTranslateVerticalAnimation(0f, 1f, 350)
    }

    fun setSignature(signature:String){
        contentView.ed_input.setText(signature)
    }

    @SuppressLint("CheckResult")
    private fun bindView() {
        contentView.ed_input.filters = arrayOf(object : InputFilter.LengthFilter(200) {})
        contentView.ed_input.textChanges().subscribe {
            var editable = contentView.ed_input.text
            var len = editable.length

            if (len >= 200) {
                toast("个性签名不能超过200字")
                //设置新光标所在的位置
                Selection.setSelection(editable, editable.length)
            }
        }
        contentView.btn_send.clicks().throttleFirst(1, TimeUnit.SECONDS).subscribe {
            if (contentView.ed_input.text.isBlank()) {
                toast("个性签名不能为空！")
            } else {
                userInfoContractView.modifySignature(contentView.ed_input.text.toString())
                dismiss()
            }

        }
    }

}