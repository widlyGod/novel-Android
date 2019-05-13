package com.novel.cn.app.gson

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.jess.arms.utils.LogUtils


class StringAdapter : TypeAdapter<String>() {
    override fun write(writer: JsonWriter?, value: String?) {
        if (value == null) {
            writer?.nullValue();

            return;
        }
        writer?.value(value);
    }

    override fun read(reader: JsonReader): String {
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return ""
        }
        return reader.nextString()
    }
}


class BooleanAdapter : TypeAdapter<Boolean>() {
    override fun write(writer: JsonWriter?, value: Boolean?) {
        if (value == null) {
            writer?.nullValue()
            return
        }
        writer?.value(value)
    }

    override fun read(reader: JsonReader): Boolean {
        val peek = reader.peek()
        if (peek == JsonToken.NULL) {
            //服务器返回null 按false处理
            reader.nextNull()
            return false
        } else if (peek == JsonToken.NUMBER) {
            //服务器返回number类型1 按true处理
            return reader.nextInt() == 1
        } else if (peek == JsonToken.STRING) {
            //服务器返回string类型 "1" 或者 "true" 按true处理
            return reader.nextString() in setOf("1", "true")
        }
        return reader.nextBoolean()
    }
}
