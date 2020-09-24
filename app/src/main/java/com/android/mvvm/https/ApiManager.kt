package com.android.mvvm.https

/**
 * date: 2020/9/21
 * desc: 接口初始化
 */
object ApiManager {

    fun init(baseURL: String) {
        NetWorkManager.instance.init(baseURL)
    }

    fun service() = NetWorkManager.instance.service

}