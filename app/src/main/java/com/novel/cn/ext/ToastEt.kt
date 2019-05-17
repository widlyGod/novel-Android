package com.novel.cn.ext

import android.content.Context
import android.support.v4.app.Fragment
import android.widget.Toast
import com.jess.arms.base.BaseApplication
import com.jess.arms.utils.ArmsUtils


fun Context.toast(message: String? = "") {
    ArmsUtils.makeText(this.applicationContext, message)
}


fun Fragment.toast(message: String? = "") = activity?.toast(message)


fun Any.toast(message: String?) = BaseApplication.getInstance().toast(message)