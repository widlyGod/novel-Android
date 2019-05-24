package com.xmssx.common.ext

import android.app.Dialog
import android.support.annotation.DimenRes
import android.support.v4.app.DialogFragment

/**
 * Created by hy on 2018/10/29
 */
fun Dialog.setWidthHeight(@DimenRes widthId: Int, @DimenRes heightId: Int) {
    setWidthHeightPixel(context.resources.getDimensionPixelOffset(widthId), context.resources.getDimensionPixelOffset(heightId))
}

fun DialogFragment.setWidthHeight(@DimenRes widthId: Int, @DimenRes heightId: Int) {
    dialog?.setWidthHeight(widthId, heightId)
}

fun Dialog.setWidthHeightPixel(widthPixel: Int, heightPixel: Int) {
    window?.attributes = window?.attributes?.apply {
        width = widthPixel
        height = heightPixel
    }
}

fun DialogFragment.setWidthHeightPixel(widthPixel: Int, heightPixel: Int) {
    dialog?.setWidthHeightPixel(widthPixel, heightPixel)
}