package com.android.mvvm.https.config

/**
 * date: 2019/1/30
 * desc: 接口地址
 */
interface URLConfig {
    companion object {
        /**
         * 登录
         */
        const val login = "login"

        /**
         * 编号
         */
        const val number = "api-user/file/reference/{model}"
    }
}