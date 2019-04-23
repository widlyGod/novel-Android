package com.novel.cn.ext

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager


fun Context.getDisplayMetrics(): DisplayMetrics = DisplayMetrics().apply {
    (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getMetrics(this)
}

fun View.dp2px(value: Int): Int = ((context.getDisplayMetrics().density * value) + 0.5f).toInt()



fun Context.dp2px(value: Int): Int = ((getDisplayMetrics().density * value) + 0.5f).toInt()



