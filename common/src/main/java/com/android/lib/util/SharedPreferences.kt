package com.android.lib.util

import android.content.SharedPreferences

/**
 * date: 2020/7/27
 * desc: 使用高阶函数简化SharedPreferences的使用
 */
fun SharedPreferences.open(block: SharedPreferences.Editor.() -> Unit) {
    val editor = edit()
//    editor.block()
    block(editor)
    editor.apply()
}

/**
 * KTX扩展库包含了SharePreferences的简化用法
 * getSharedPreferences("data",Context.MODE_PRIVATE).edit(commit = true) {
// Cannot inline bytecode built with JVM target 1.8 into bytecode that is being built with JVM target 1.6. Please specify proper '-jvm-target' option
putString("name", "Tom")
putInt("age", 25)
putBoolean("married", false)
}
 */