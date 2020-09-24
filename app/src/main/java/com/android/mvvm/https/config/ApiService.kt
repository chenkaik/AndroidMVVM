package com.android.mvvm.https.config


import com.android.mvvm.entity.LoginResponse
import com.android.mvvm.entity.request.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * date: 2020/9/24
 * desc: 定义接口
 */
interface ApiService {

    @POST(URLConfig.login)
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

}