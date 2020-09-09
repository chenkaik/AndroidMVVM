package com.android.lib.util

import android.content.ContentValues

/**
 * date: 2020/7/27
 * desc:
 * vararg 对应的是java里面的可变参数列表
 * Any是Kotlin中所有类的共同基类
 * Pair是一种键值对的数据结构 需要通过泛型来指定键和值分别对应什么类型的数据
 */
fun cvOf(vararg pairs: Pair<String, Any?>) = ContentValues().apply {
    for (pair in pairs) {
        val key = pair.first
        val value = pair.second
        when (value) {
            is Int -> put(key, value)
            is Long -> put(key, value)
            is Short -> put(key, value)
            is Float -> put(key, value)
            is Double -> put(key, value)
            is Boolean -> put(key, value)
            is String -> put(key, value)
            is Byte -> put(key, value)
            is ByteArray -> put(key, value)
            null -> putNull(key)
        }
    }
}
/**
 *             // 自己实现的方式
//            val values = cvOf(
//                "name" to "Game of thrones",
//                "author" to "George Martin",
//                "pages" to 720,
//                "price" to 20.88
//            )
//            db.insert("Book", null, values)

// KTX库中自带的
val values = contentValuesOf(
"name" to "Game of thrones",
"author" to "George Martin",
"pages" to 720,
"price" to 20.88
)
db.insert("Book", null, values)
 */

