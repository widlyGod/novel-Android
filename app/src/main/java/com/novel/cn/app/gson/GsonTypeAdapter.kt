package com.novel.cn.app.gson

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.jess.arms.utils.LogUtils

class CustomTypeAdapterFactory<T> : TypeAdapterFactory {
    override fun <T> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        val rawType = type.rawType as Class<T>
        LogUtils.warnInfo("==========================$rawType")
        return when (rawType) {
            String::class -> StringAdapter() as TypeAdapter<T>
            Boolean::class -> BooleanAdapter() as TypeAdapter<T>
            else -> null
        }

    }
}


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
        LogUtils.warnInfo("============${reader.nextName()},=============")
        if (reader.peek() == JsonToken.NULL) {
            reader.nextNull()
            return false
        }else if (reader.peek() == JsonToken.NUMBER){
            return reader.nextInt() == 1
        }
        return reader.nextBoolean()
    }
}
