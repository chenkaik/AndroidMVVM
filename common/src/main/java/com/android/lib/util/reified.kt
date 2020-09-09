package com.android.lib.util

import android.content.Context
import android.content.Intent

/**
 * date: 2020/8/3
 * desc: 泛型实化 必须inline关键字声明 声明泛型的地方必须加上reified关键字来表示该泛型要进行实化
 */
inline fun <reified T> startActivity(context: Context) {
    val intent = Intent(context, T::class.java)
    context.startActivity(intent)
}

inline fun <reified T> startActivity(context: Context, block: Intent.() -> Unit) {
    val intent = Intent(context, T::class.java)
    intent.block()
    context.startActivity(intent)
}