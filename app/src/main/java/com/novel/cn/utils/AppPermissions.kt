package com.novel.cn.utils

import android.Manifest
import android.app.Activity
import android.support.v4.app.Fragment
import com.jess.arms.mvp.IView
import com.novel.cn.R
import com.tbruyelle.rxpermissions2.RxPermissions
import com.novel.cn.ext.applySchedulers
import com.novel.cn.ext.bindToLifecycle
import com.novel.cn.ext.observeOnMain
import io.reactivex.Single
import pub.devrel.easypermissions.AppSettingsDialog
import java.util.concurrent.TimeUnit

/**
 * Created by hy on 2019/3/5
 */
object AppPermissions {

    fun requestCameraPermission(
            rxPermissions: RxPermissions,
            view: IView,
            settingRequestCode: Int = 9999,
            tips: String = "使用拍摄需要获取相应权限，否则无法使用。",
            call: (() -> Unit)? = null) {

        val isActivity = view is Activity
        if (!isActivity && view !is Fragment) {
            view.showMessage("view必须继承activity或fragment")
            return
        }
        rxPermissions.requestEachCombined(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO)
                .observeOnMain()
                .subscribe { permission ->
                    when {
                        permission.granted -> {
                            call?.invoke()
                        }
                        permission.shouldShowRequestPermissionRationale -> {
                            view.showMessage(tips)
                            Single.just(Unit) // 避免看不到提示消息
                                    .delay(1, TimeUnit.SECONDS)
                                    .applySchedulers(view)
                                    .subscribe({
                                        requestCameraPermission(rxPermissions, view, settingRequestCode, tips, call)
                                    }, {}).bindToLifecycle(view)
                        }
                        else -> {
                            val builder = if (isActivity) {
                                AppSettingsDialog.Builder(view as Activity)
                            } else {
                                AppSettingsDialog.Builder(view as Fragment)
                            }
                            builder
                                    .setTitle("权限设置")
                                    .setRationale("$tips\n打开app设置界面勾选存储相机录音权限")
                                    .setRequestCode(settingRequestCode)
                                    .setThemeResId(R.style.Base_Theme_AppCompat_Light_Dialog)
                                    .build()
                                    .show()
                        }
                    }
                }.bindToLifecycle(view)
    }

    fun requestLocationPermission(rxPermissions: RxPermissions, view: IView, settingRequestCode: Int = 9999, call: (() -> Unit)? = null) {
        val isActivity = view is Activity
        if (!isActivity && view !is Fragment) {
            view.showMessage("view必须继承activity或fragment")
            return
        }
        rxPermissions.requestEachCombined(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ).observeOnMain()
                .subscribe { permission ->
                    when {
                        permission.granted -> {
                            call?.invoke()
                        }
                        permission.shouldShowRequestPermissionRationale -> {
                            view.showMessage("为了您更好的使用请打开定位权限")
                            Single.just(Unit) // 避免看不到提示消息
                                    .delay(1, TimeUnit.SECONDS)
                                    .applySchedulers(view)
                                    .subscribe({
                                        requestLocationPermission(rxPermissions, view, settingRequestCode, call)
                                    }, {}).bindToLifecycle(view)
                        }
                        else -> {
                            val builder = if (isActivity) {
                                AppSettingsDialog.Builder(view as Activity)
                            } else {
                                AppSettingsDialog.Builder(view as Fragment)
                            }
                            builder
                                    .setTitle("权限设置")
                                    .setRationale("为了您更好的使用请打开定位权限。\n打开app设置界面勾选定位权限")
                                    .setRequestCode(settingRequestCode)
                                    .setThemeResId(R.style.Base_Theme_AppCompat_Light_Dialog)
                                    .build()
                                    .show()
                        }
                    }
                }.bindToLifecycle(view)
    }
}