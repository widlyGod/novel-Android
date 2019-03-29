package com.novel.cn.app

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Base64
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty


object Preference {

    lateinit var sharedPreferences: SharedPreferences
    fun init(context: Context, name: String = "config") {
        sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    @SuppressLint("ApplySharedPref")
    fun clean() {
        sharedPreferences.edit().clear().commit()
    }

    @SuppressLint("ApplySharedPref")
    fun <T> put(key: String, value: T) {
        sharedPreferences.edit().apply {
            when (value) {
                is Long -> putLong(key, value)
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                is Any -> saveDeviceData(key, value)
                else -> throw IllegalArgumentException("This type can be saved into Preferences")
            }

        }.apply()
    }

    private fun <T> saveDeviceData(key: String, value: T) {
        val baos = ByteArrayOutputStream()
        try {   //Device为自定义类
            // 创建对象输出流，并封装字节流
            val oos = ObjectOutputStream(baos)
            // 将对象写入字节流
            oos.writeObject(value)
            // 将字节流编码成base64的字符串
            val oAuth_Base64 = String(Base64.encode(baos
                    .toByteArray(), Base64.DEFAULT))
            put(key, oAuth_Base64)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    fun getString(key: String, default: String? = "") = sharedPreferences.getString(key, default)

    fun getInt(key: String, default: Int = 0) = sharedPreferences.getInt(key, default)

    fun getBoolean(key: String, default: Boolean = false) = sharedPreferences.getBoolean(key, default)

    fun <T> getDeviceData(key: String): T? {
        var device: T? = null
        val productBase64 = getString(key, null) ?: return null
        // 读取字节
        val base64 = Base64.decode(productBase64.toByteArray(), Base64.DEFAULT)

        // 封装到字节流
        val bais = ByteArrayInputStream(base64)
        try {
            // 再次封装
            val bis = ObjectInputStream(bais)
            // 读取对象
            device = bis.readObject() as T

        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }

        return device
    }
}



