package com.novel.cn.ext

import com.novel.cn.log.Timber
import com.novel.cn.log.error

/**
 * Created by hy on 2018/1/20.
 */
inline fun try2(block: () -> Unit) {
    return try {
        block()
    } catch (e: Throwable) {
        Timber.error(e) { e.message }
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun Any.classKey(key: String): String {
    return "${this::class.java.simpleName}__$key"
}