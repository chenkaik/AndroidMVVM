package com.android.mvvm.jetpack.api

import com.android.mvvm.entity.LoginResponse
import com.android.mvvm.entity.request.LoginRequest
import com.android.mvvm.https.config.URLConfig
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * date: 2021/5/29
 * desc: 定义所用到的接口
 */
interface ApiService {

    /**
     * 登录
     */
    @POST(URLConfig.login)
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

}