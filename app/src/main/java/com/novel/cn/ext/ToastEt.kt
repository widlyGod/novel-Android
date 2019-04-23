package com.novel.cn.ext

import android.content.Context
import android.support.v4.app.Fragment
import android.widget.Toast
import com.jess.arms.utils.ArmsUtils


inline fun Context.toast(message: String? = "") {
    ArmsUtils.makeText(this.applicationContext, message)
}


inline fun Fragment.toast(message: String? = "") = activity?.toast(message)