package com.android.lib.util.kotlin

import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.android.lib.util.ActivityCollector

/**
 * date: 2020/8/3
 * desc: 封装的页面跳转函数
 * 泛型实化 必须inline关键字声明 声明泛型的地方必须加上reified关键字来表示该泛型要进行实化
 */
inline fun <reified T> startActivity(activity: FragmentActivity, isPutStack: Boolean) {
    val intent = Intent(activity, T::class.java)
    ActivityCollector.startPage(activity, intent, isPutStack)
}

inline fun <reified T> startActivity(
        activity: FragmentActivity, isPutStack: Boolean,
        block: Intent.() -> Unit
) {
    val intent = Intent(activity, T::class.java)
    intent.block()
    ActivityCollector.startPage(activity, intent, isPutStack)
}

inline fun <reified T> startActivity(
        activity: FragmentActivity,
        isPutStack: Boolean,
        requestCode: Int
) {
    val intent = Intent(activity, T::class.java)
    ActivityCollector.startPage(activity, intent, isPutStack, requestCode)
}

inline fun <reified T> startActivity(
        activity: FragmentActivity,
        isPutStack: Boolean, requestCode: Int,
        block: Intent.() -> Unit
) {
    val intent = Intent(activity, T::class.java)
    intent.block()
    ActivityCollector.startPage(activity, intent, isPutStack, requestCode)
}