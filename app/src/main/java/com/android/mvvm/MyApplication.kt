package com.android.mvvm

import android.app.Application
import android.content.Context
import com.android.lib.Logger
import com.android.lib.util.CrashHandler
import com.android.mvvm.https.ApiManager
import com.android.mvvm.https.config.HttpConfig


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
        Logger.LOG_ENABLE = true
        CrashHandler.instance.init(context)
        ApiManager.init(BuildConfig.BASEURL)
    }

}