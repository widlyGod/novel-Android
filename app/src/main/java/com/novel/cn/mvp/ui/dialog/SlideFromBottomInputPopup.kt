package com.novel.cn.mvp.ui.dialog

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.utils.DeviceUtils
import com.novel.cn.R
import com.novel.cn.ext.toast
import com.novel.cn.mvp.contract.UserInfoContract
import kotlinx.android.synthetic.main.popup_slide_from_bottom_with_input.view.*
import razerdp.basepopup.BasePopupWindow
import java.util.concurrent.TimeUnit

class SlideFromBottomInputPopup(context: Context, val type: Int, private val userInfoContractView: UserInfoContract.View) : BasePopupWindow(context) {

    init {
        popupGravity = Gravity.BOTTOM
        bindView()
        setAutoShowInputMethod(contentView.ed_input, true)
    }

    override fun onCreateContentView(): View {
        return createPopupById(R.layout.popup_slide_from_bottom_with_input)
    }

    override fun onCreateShowAnimation(): Animation {
        return getTranslateVerticalAnimation(1f, 0f, 350)
    }

    override fun onCreateDismissAnimation(): Animation {
        return getTranslateVerticalAnimation(0f, 1f, 350)
    }

    private fun bindView() {
        when (type) {
            0 -> {
                contentView.ed_input.hint = "请输入昵称"
                contentView.btn_send.clicks().throttleFirst(1, TimeUnit.SECONDS).subscribe {
                    if (contentView.ed_input.text.isBlank()) {
                        toast("昵称不能为空！")
                    } else {
                        userInfoContractView.modifyName(contentView.ed_input.text.toString())
                        dismiss()
                    }

                }
            }
            else -> {
                contentView.ed_input.hint = "请输入个性签名"
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
    }

}