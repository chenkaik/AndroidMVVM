package com.android.lib.util.kotlin

/**
 * date: 2020/7/15
 * desc: String扩展函数
 */
fun String.lettersCount(): Int { // 计算字符串中包含多少个字母
    var count = 0
    for (char in this) {
        if (char.isLetter()) {
            count++
        }
    }
    return count
}

//operator fun String.times(n: Int): String {
//    val builder = StringBuilder()
//    repeat(n) {
//        builder.append(this)
//    }
//    return builder.toString()
//}

/**
 * 重载times函数 可以让字符串和数字相乘
 * Kotlin中的String类已经提供了一个用于将字符串重复N次的repeat()函数
 * 精简编写如下
 */
operator fun String.times(n: Int) = repeat(n)

