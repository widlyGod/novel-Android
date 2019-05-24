package com.xmssx.common.ext

import android.app.ActivityManager
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.novel.cn.utils.DensityUtil
import org.jetbrains.anko.displayMetrics

fun Context.getPackageInfo() = packageManager?.getPackageInfo(packageName, 0)

fun Context.getVersionName() = getPackageInfo()?.versionName

fun Context.getVersionCode() = getPackageInfo()?.versionCode

fun Context.dpToPx(@DimenRes resID: Int): Int = resources.getDimensionPixelOffset(resID)

fun Context.getCompactColor(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

fun Context.getInstallTime() = getPackageInfo()?.firstInstallTime ?: System.currentTimeMillis()

fun Context.getMetaData() = packageManager?.getApplicationInfo(packageName, PackageManager.GET_META_DATA)?.metaData

fun Context.dp2px(dip: Float) = DensityUtil.dp2px(this, dip)

fun Context.px2dp(px: Float) = DensityUtil.px2dp(this, px)

fun Context.sp2px(sp: Float) = DensityUtil.sp2px(this, sp)

fun Context.getDimension(id: Int) = resources.getDimension(id)
fun Context.getDimensionPixelOffset(id: Int) = resources.getDimensionPixelOffset(id)
fun Context.getScreenWidthHeight(): Pair<Int, Int> {
    val metrics = displayMetrics
    return Pair(metrics.widthPixels, metrics.heightPixels)
}

fun Context.getScreenWidth() = displayMetrics.widthPixels
fun Context.getScreenHeight() = displayMetrics.heightPixels

fun Context.getCompactDrawable(@DrawableRes resID: Int): Drawable? = ContextCompat.getDrawable(this, resID)

fun Fragment.getDimension(id: Int) = context?.getDimension(id) ?: 0F
fun Fragment.getDimensionPixelOffset(id: Int) = context?.getDimensionPixelOffset(id) ?: 0
fun Fragment.getScreenWidth() = context?.getScreenWidth() ?: 0
fun Fragment.getScreenHeight() = context?.getScreenHeight() ?: 0
fun Fragment.getScreenWidthHeight() = context?.getScreenWidthHeight() ?: Pair(0, 0)
fun Fragment.getCompactDrawable(@DrawableRes resID: Int): Drawable? = context?.getCompactDrawable(resID)

/**
 * 获取当前的进程名
 */
fun Context.getProcessName(): String {
    val pid = android.os.Process.myPid()
    try2 {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for (process in activityManager.runningAppProcesses) {
            if (process.pid == pid) {
                return process.processName
            }
        }
    }
    return ""
}
