package com.novel.cn.app

import com.jess.arms.utils.LogUtils

object Constant {


    const val SESSION_ID = "SESSION_ID"
    const val LOGIN_INFO = "LOGIN_INFO"

    //列表每页的数量
    const val PAGE_SIZE = 10


    //评论来源
    val DEVICE_TYPE = HashMap<String, String>()


    init {
        LogUtils.warnInfo("====================>>>init")
        DEVICE_TYPE[""] = "来自PC端"
        DEVICE_TYPE["normal"] = "来自PC端"
        DEVICE_TYPE["mobile"] = "来自手机端"
        DEVICE_TYPE["tablet"] = "来自平板"
        DEVICE_TYPE["unknown"] = "未知来源"
    }
}