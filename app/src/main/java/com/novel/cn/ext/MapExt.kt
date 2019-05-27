@file:Suppress("NOTHING_TO_INLINE")

package com.novel.cn.ext

/**
 * Created by hy on 2018/11/8
 */
public inline fun <K, V> Map<K, V>.get(key: K, default: V): V {
    return get(key) ?: default
}

public inline fun <K, V> MutableMap<K, V>.find(getDefault: () -> Pair<K, V>, condition: (key: K) -> Boolean): V {
    forEach {
        if (condition(it.key))
            return it.value
    }
    val default = getDefault()
    put(default.first, default.second)
    return default.second
}

public inline fun <K, V> MutableMap<K, V>.find(condition: (key: K) -> Boolean): V? {
    forEach {
        if (condition(it.key))
            return it.value
    }
    return null
}

public inline fun <K, V> MutableMap<K, V>.contains(condition: (key: K) -> Boolean): Boolean {
    forEach {
        if (condition(it.key))
            return true
    }
    return false
}