package com.novel.cn.utils

import android.content.Context

/**
 * 屏幕密度相关的工具类
 * Created by hy on 2018/8/4
 */
object DensityUtil {

    private var mDensity: Float = 0F
    private var mScaledDensity: Float = 0F

    fun getDensity(context: Context): Float {
        if (mDensity <= 0) {
            mDensity = context.resources.displayMetrics.density
        }
        return mDensity
    }

    fun getScaledDensity(context: Context): Float {
        if (mScaledDensity <= 0) {
            mScaledDensity = context.resources.displayMetrics.scaledDensity
        }
        return mScaledDensity
    }

    fun dp2px(context: Context, dip: Float): Int {
        return (dip * getDensity(context) + 0.5f).toInt()
    }

    fun px2dp(context: Context, px: Float): Int {
        return (px / getDensity(context) + 0.5f).toInt()
    }

    fun sp2px(context: Context, sp: Float): Int {
        return (sp * getScaledDensity(context) + 0.5f).toInt()
    }
}