package com.android.lib.util

import android.text.TextUtils
import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

/**
 * date: 2019/1/30.
 * desc: Gson封装解析Json
 */
object GsonUtil {

    /**
     *  by lazy代码块 懒加载技术 第一次调用GSON才会执行 最后一行代码作为返回值
     */
    val GSON by lazy {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss")
        gsonBuilder.create()
    }


    /**
     * 对象转换为json字符串
     *
     * @param obj 转换的对象
     * @return 转换后的字符串
     */
    fun toJson(obj: Any): String {
        return GSON.toJson(obj)
    }

    /**
     * 解析json数据(list)
     *
     * @param json  待解析的数据
     * @param token 解析的类型
     * @param <T>   泛型
     * @return 解析后的对象
    </T>  使用方式：fromJson(json, object : TypeToken<List<Person>>() {})
     */
    fun <T> fromJson(json: String?, token: TypeToken<T>): T? {
        if (TextUtils.isEmpty(json)) {
            return null
        }
        try {
            return GSON.fromJson(json, token.type)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 解析json数据
     *
     * @param json  待解析的数据
     * @param clazz 解析的类型
     * @param <T>   泛型
     * @return 解析后的对象
    </T> */
    fun <T> fromJson(json: String?, clazz: Class<T>?): T? {
        if (TextUtils.isEmpty(json)) {
            return null
        }
        try {
            return GSON.fromJson(json, clazz)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    /**
     * 判断json数据是否可用
     *
     * @param jsonInString 数据
     * @return 结果true/false
     */
    fun isJsonValid(jsonInString: String?): Boolean {
        return if (TextUtils.isEmpty(jsonInString)) {
            false
        } else try {
            GSON!!.fromJson(jsonInString, Any::class.java)
            true
        } catch (ex: JsonSyntaxException) {
            false
        } catch (e: Exception) {
            false
        }
    }

    /**
     * 判断是否是json数据
     *
     * @param json 数据
     * @return 结果true/false
     */
    fun isGoodJson(json: String?): Boolean {
        return if (TextUtils.isEmpty(json)) {
            false
        } else try {
            JsonParser().parse(json)
            true
        } catch (e: JsonSyntaxException) {
            false
        } catch (e: JsonParseException) {
            false
        } catch (e: Exception) {
            false
        }
    }

    //    /**
//     * 解析List json数据
//     *
//     * @param json  待解析的数据
//     * @param clazz 解析的类型
//     * @param <T>   泛型
//     * @return 返回list
//    </T> */
//    fun <T> fromJsonArray(
//        json: String?,
//        clazz: Class<Array<T>?>?
//    ): List<T>? {
//        if (TextUtils.isEmpty(json)) {
//            return null
//        }
//        try {
//            val arr: Array<T> = GSON!!.fromJson(json, clazz)
//            return Arrays.asList(*arr)
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//        return null
//    }

}