package com.android.lib.util

import android.content.Context
import android.widget.Toast

/**
 * date: 2020/8/8
 * desc: 简化toast的使用
 */
// String 扩展函数
fun String.showToast(
    context: Context,
    duration: Int = Toast.LENGTH_SHORT
) { // "this is Toast".showToast(context)
    Toast.makeText(context, this, duration).show()
}

// Int 扩展函数
fun Int.showToast(
    context: Context,
    duration: Int = Toast.LENGTH_SHORT
) { // R.string.app_name.showToast(context)
    Toast.makeText(context, this, duration).show()
}
