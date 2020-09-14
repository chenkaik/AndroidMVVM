package com.android.lib

import android.util.Log

/**
 * date: 2020/8/9
 * desc: 统一日志类
 */
object Logger {

    var LOG_ENABLE = true

    private const val VERBOSE = 1

    private const val DEBUG = 2

    private const val INFO = 3

    private const val WARN = 4

    private const val ERROR = 5

    private var level = VERBOSE

    fun v(tag: String, msg: String) {
        if (LOG_ENABLE && level <= VERBOSE) {
            Log.v(tag, msg)
        }
    }

    fun d(tag: String, msg: String) {
        if (LOG_ENABLE && level <= DEBUG) {
            Log.d(tag, msg)
        }
    }

    fun i(tag: String, msg: String) {
        if (LOG_ENABLE && level <= INFO) {
            Log.i(tag, msg)
        }
    }

    fun w(tag: String, msg: String) {
        if (LOG_ENABLE && level <= WARN) {
            Log.w(tag, msg)
        }
    }

    fun e(tag: String, msg: String) {
        if (LOG_ENABLE && level <= ERROR) {
            Log.e(tag, msg)
        }
    }

    fun e(tag: String, msg: String, tr: Throwable) {
        if (LOG_ENABLE && level <= ERROR) {
            Log.e(tag, msg, tr)
        }
    }

}