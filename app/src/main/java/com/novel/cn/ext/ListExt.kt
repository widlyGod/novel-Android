package com.xmssx.common.ext

/**
 * Created by hy on 2018/10/19
 */
public inline fun <T> List<T>.findCall(predicate: (T) -> Boolean, call: (index: Int) -> Unit): Boolean {
    for ((index, item) in withIndex()) {
        if (predicate(item)) {
            call(index)
            return true
        }
    }
    return false
}

public inline fun <T> MutableList<T>.replace(replace: T, predicate: (T) -> Boolean): Boolean {
    return findCall(predicate) {
        removeAt(it)
        add(it, replace)
    }
}