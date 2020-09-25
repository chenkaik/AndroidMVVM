package com.android.mvvm.https.config


import com.android.mvvm.entity.LoginResponse
import com.android.mvvm.entity.NumberResponse
import com.android.mvvm.entity.request.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * date: 2020/9/24
 * desc: 定义接口
 */
interface ApiService {

    /**
     * 登录
     */
    @POST(URLConfig.login)
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    /**
     * 获取业务编号
     */
    @GET(URLConfig.number)
    fun getNumber(@Path("model") model: String): Call<NumberResponse>


}