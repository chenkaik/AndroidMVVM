package com.android.mvvm.util

import android.widget.Toast
import com.android.mvvm.MyApplication

/**
 * date: 2020/9/9
 * desc: 简化toast的使用
 */
fun String.showToast(duration: Int = Toast.LENGTH_SHORT) { // "this is Toast".showToast()
    Toast.makeText(MyApplication.context, this, duration).show()
}

fun Int.showToast(duration: Int = Toast.LENGTH_SHORT) { // R.string.app_name.showToast()
    Toast.makeText(MyApplication.context, this, duration).show()
}