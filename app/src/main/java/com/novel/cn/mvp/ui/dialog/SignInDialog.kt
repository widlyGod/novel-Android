package com.novel.cn.mvp.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.flyco.animation.BaseAnimatorSet
import com.flyco.animation.FadeEnter.FadeEnter
import com.flyco.animation.FadeExit.FadeExit
import com.flyco.animation.ZoomExit.ZoomInExit
import com.flyco.dialog.widget.base.BaseDialog
import com.flyco.dialog.widget.base.BottomBaseDialog
import com.flyco.dialog.widget.base.TopBaseDialog
import com.novel.cn.R
import kotlinx.android.synthetic.main.popup_signin.*

class SignInDialog(context: Context) : Dialog(context) {


    override fun onCreate(savedInstanceState: Bundle?) {
        val view = layoutInflater.inflate(R.layout.popup_signin, null, false)
        this.setContentView(view)
        window.setBackgroundDrawable(null);
    }

    fun setToast(readCoupon: Int) {
        tv_signin.text = "签到成功，赠送${readCoupon}阅读币"
    }
}