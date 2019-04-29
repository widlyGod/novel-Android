package com.novel.cn.ext

import android.app.Dialog
import android.content.Context
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager


fun Context.getDisplayMetrics(): DisplayMetrics = DisplayMetrics().apply {
    (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(this)
}

fun Context.dp2px(value: Int): Int = ((getDisplayMetrics().density * value) + 0.5f).toInt()

fun Fragment.dp2px(value: Int): Int = this.activity?.dp2px(value) ?: 0

fun View.dp2px(value: Int): Int = this.context.dp2px(value)


fun Context.px2dp(value: Int) = (value / getDisplayMetrics().density + 0.5f).toInt()

fun Fragment.px2dp(value: Int) = this.activity?.px2dp(value) ?: 0

fun View.px2dp(value: Int) = this.context.px2dp(value)


