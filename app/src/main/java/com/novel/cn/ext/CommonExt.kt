package com.xmssx.common.ext

import com.xmssx.common.log.Timber
import com.xmssx.common.log.error

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