package com.novel.cn.ext

import android.app.Activity
import com.jess.arms.integration.AppManager
import com.jess.arms.utils.Preconditions
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
fun <T : Activity> AppManager.get(clazz: KClass<T>): T? {
    val result = activityList.single {
        it.javaClass == clazz.java
    } ?: return null
    return result as T
}

fun <T : Activity> AppManager.getNotNull(clazz: KClass<T>): T {
    val result = get(clazz)
    Preconditions.checkNotNull(result)
    return result!!
}