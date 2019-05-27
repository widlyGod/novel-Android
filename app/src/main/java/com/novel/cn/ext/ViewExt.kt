package com.novel.cn.ext

import android.view.View

/**
 * Created by hy on 2018/10/12
 */
fun View.setVisible() {
    visibility = View.VISIBLE
}

fun View.setVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.setGone() {
    visibility = View.GONE
}

fun View.setInVisible() {
    visibility = View.INVISIBLE
}

fun View.isVisible() = visibility == View.VISIBLE



