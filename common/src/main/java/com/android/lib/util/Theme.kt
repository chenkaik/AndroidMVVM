package com.android.lib.util

import android.content.Context
import android.content.res.Configuration

/**
 * date: 2020/8/9
 * desc: 判断当前系统是否是深色主题
 * and=&  or=|  xor=^
 * Kotlin取消了按位运算符的写法，改成了使用英文关键字，
 * 因此上述代码中的and关键字其实就对应了Java中的&运算符，
 * 而Kotlin中的or关键字对应了Java中的|运算符，
 * xor关键字对应了Java中的^运算符
 */
fun isDarkTheme(context: Context): Boolean {
    val flag = context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return flag == Configuration.UI_MODE_NIGHT_YES
}