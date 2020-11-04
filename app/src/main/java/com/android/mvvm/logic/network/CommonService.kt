package com.android.mvvm.logic.network

import com.android.mvvm.entity.LoginResponse
import com.android.mvvm.https.config.URLConfig
import com.android.mvvm.logic.model.Login
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * date: 2020/11/4
 * desc:
 */
interface CommonService {

    /**
     * 登录
     */
    @POST(URLConfig.login)
    fun login(@Body login: Login): Call<LoginResponse>

}