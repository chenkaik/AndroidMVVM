package com.android.mvvm.jetpack.api

import com.android.mvvm.entity.request.LoginRequest

/**
 * date: 2021/5/29
 * desc: 调用接口辅助类
 */
class ApiHelper(private val apiService: ApiService) {

    suspend fun login(loginRequest: LoginRequest) = apiService.login(loginRequest)

}