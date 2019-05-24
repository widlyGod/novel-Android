package com.novel.cn.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

/**
 * Created by hy on 2018/8/14
 */
object GsonUtil {

    val gson by lazy { Gson() }
    val exposeGson by lazy { GsonBuilder().excludeFieldsWithoutExposeAnnotation().create() }
    val prettyGson by lazy { GsonBuilder().setPrettyPrinting().create() }

    fun toJson(obj: Any?, isPretty: Boolean = false): String {
        return if (isPretty)
            prettyGson.toJson(obj)
        else
            gson.toJson(obj)
    }

    fun toPrettyJson(obj: Any?): String {
        return toJson(obj, true)
    }

    inline fun <reified T> copy(bean: T): T {
        return fromJson(toJson(bean))
    }

    inline fun <reified T> fromJson(json: String): T {
        return gson.fromJsonTypeToken(json)
    }
}

inline fun <reified T> Gson.fromJsonTypeToken(json: String) = this.fromJson<T>(json, object: TypeToken<T>() {}.type)
inline fun <reified T> String.fromJson() = GsonUtil.fromJson<T>(this)
inline fun Any.toJson() = GsonUtil.toJson(this)
inline fun Any.toJson(isPretty: Boolean) = GsonUtil.toJson(this, isPretty)