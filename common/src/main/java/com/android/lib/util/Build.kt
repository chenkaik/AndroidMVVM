package com.android.lib.util

/**
 * date: 2020/7/28
 * desc: 仿apply函数的实现
 */
fun <T> T.build(block: T.() -> Unit): T {
    block()
    return this
}