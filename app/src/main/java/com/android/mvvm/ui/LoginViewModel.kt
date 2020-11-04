package com.android.mvvm.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.android.mvvm.logic.Repository
import com.android.mvvm.logic.model.Login

/**
 * date: 2020/11/4
 * desc:
 */
class LoginViewModel : ViewModel() {

    private val loginLiveData = MutableLiveData<Login>()

    val liveData = Transformations.switchMap(loginLiveData) { login ->
        Repository.login(login)
    }

    fun login(username: String, password: String) {
        loginLiveData.value = Login(username, password)
    }

}