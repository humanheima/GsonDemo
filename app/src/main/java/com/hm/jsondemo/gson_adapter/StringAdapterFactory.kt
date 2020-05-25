package com.hm.jsondemo.gson_adapter

import android.util.Log
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

/**
 * Created by dumingwei on 2020/5/23.
 *
 * Desc:
 */
class StringAdapterFactory : TypeAdapterFactory {

    val TAG: String? = "StringAdapterFactory"

    override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        val rawType: Class<in T> = type.rawType

        if (rawType != String::class.java) {
            return null
        }
        Log.d(TAG, "create: return StringAdapter")
        return StringAdapter() as (TypeAdapter<T>)

    }

    /**
     * Created by dumingwei on 2020/5/23.
     *
     * Desc: 在将json字符串反序列化为model类的时候，如果String字段为null，那么就用空字符串（""）替换，然后将空字符串（""）赋值给model类的字段
     */
    class StringAdapter : TypeAdapter<String>() {

        override fun write(out: JsonWriter, value: String?) {
            out.value(value)
        }

        override fun read(reader: JsonReader): String {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull()
                return ""
            }
            return reader.nextString()
        }
    }
}