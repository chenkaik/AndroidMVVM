package com.android.mvvm.util

import com.android.lib.util.SharedPreferencesUtil
import com.android.mvvm.MyApplication

/**
 * date: 2019/1/30
 * desc: 该SP是和用户信息相关的
 */
object UserConfig : SharedPreferencesUtil(MyApplication.context) {

    private const val TOKEN = "token"

    fun clearToken() {
        put(TOKEN, "")
    }

    fun putToken(token: String?) {
        put(TOKEN, token)
    }

    val token: String?
        get() = getString(TOKEN, "")

}