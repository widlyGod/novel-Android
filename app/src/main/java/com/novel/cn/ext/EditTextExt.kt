package com.novel.cn.ext

import android.widget.EditText
import com.jess.arms.utils.DeviceUtils

/**
 * Created by hy on 2018/8/4
 */

/**
 * 设置编辑文本框获取焦点时不显示hint
 */
fun EditText.setOnFocusedDontShowHint(defaultHint: String = hint.toString()) {
    setOnFocusChangeListener {_, hasFocus ->
        if (hasFocus) {
            hint = ""
            // 保证一定会弹出键盘
            DeviceUtils.showSoftKeyboard(context, this)
        } else {
            val text = text.toString()
            if ("" == text) {
                hint = defaultHint
            }
        }
    }
}