package com.android.mvc

import android.app.Application
import android.content.Context
import com.android.lib.Logger

/**
 * date: 2020/9/7
 * desc: 程序入口
 */
class MyApplication : Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        Logger.LOG_ENABLE = false
    }

}