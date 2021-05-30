package com.android.mvvm.jetpack.http

import com.android.mvvm.jetpack.api.ApiService

/**
 * date: 2021/5/29
 * desc: 获取定义的接口服务
 */
object NetServiceCreatorHelper {

        val apiService = NetServiceCreator.create<ApiService>()

}