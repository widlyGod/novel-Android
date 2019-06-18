package com.novel.cn.mvp.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.novel.cn.R
import kotlinx.android.synthetic.main.popup_signin.view.*

class SignInDialog(context: Context) : Dialog(context) {

    var readCoupon = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        val view = layoutInflater.inflate(R.layout.popup_signin, null, false)
        view.tv_signin.text = "签到成功，赠送${readCoupon}阅读币"
        this.setContentView(view)
        window.setBackgroundDrawable(null);
    }

    fun setToast(readCoupon: Int) {
        this.readCoupon = readCoupon
    }


}