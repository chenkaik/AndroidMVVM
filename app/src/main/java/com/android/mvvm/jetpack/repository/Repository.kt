package com.android.mvvm.jetpack.repository

import com.android.mvvm.entity.request.LoginRequest
import com.android.mvvm.jetpack.api.ApiHelper

/**
 * date: 2021/5/29
 * desc: 仓库层
 */
class Repository(private val apiHelper: ApiHelper) {

    suspend fun login(loginRequest: LoginRequest) = apiHelper.login(loginRequest)

}