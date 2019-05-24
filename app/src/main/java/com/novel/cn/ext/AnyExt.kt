package com.xmssx.common.ext

import com.jess.arms.mvp.IView
import com.novel.cn.utils.GsonUtil
import com.xmssx.common.log.Timber
import com.xmssx.common.log.error
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException

/**
 * Created by hy on 2018/10/19
 */

/**
 * 深度拷贝，解决kotlin copy有些字段指向同一引用问题，使用gson实现，字段需要支持json序列化
 */
inline fun <reified T> T.copyDeep(): T {
    return GsonUtil.copy(this)
}

inline fun <T> T?.println() = kotlin.io.println(this)

fun <T> T.handleError(t: Throwable, view: IView?, defaultMsg: String) {
    Timber.error(t) { t.message }
    if (t is TimeoutException || t is SocketTimeoutException) {
        view?.showMessage("请求超时")
        return
    }
    view?.showMessage(defaultMsg)
}