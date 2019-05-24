package com.xmssx.common.ext

import android.support.v7.widget.RecyclerView

/**
 * Created by hy on 2018/10/17
 */
fun RecyclerView.dontShowChangeAnimator() {
    itemAnimator?.changeDuration = 0
}

fun RecyclerView.dontShowMoveAnimator() {
    itemAnimator?.moveDuration = 0
}