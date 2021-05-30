package com.android.mvvm.jetpack.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.android.mvvm.entity.request.LoginRequest
import com.android.mvvm.jetpack.repository.Repository
import com.android.mvvm.jetpack.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

/**
 * date: 2021/5/29
 * desc: 定义ViewModel
 */
class TestViewModel(private val repository: Repository) : ViewModel() {

    fun login(loginRequest: LoginRequest) = liveData(Dispatchers.IO) {
        emit(Resource.loading(null))
        try {
            val loginResponse = repository.login(loginRequest)
            if (loginResponse.SYS_HEAD?.RET?.RET_CODE == "000000") {
                emit(Resource.success(loginResponse))
            } else {
                emit(Resource.error(loginResponse.SYS_HEAD?.RET?.RET_MSG, null))
            }
        } catch (e: Exception) {
            emit(Resource.error(e.message, null))
        }
    }

}