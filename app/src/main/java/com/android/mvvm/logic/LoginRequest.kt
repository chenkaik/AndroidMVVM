package com.android.mvvm.logic

import java.io.Serializable

/**
 * date: 2019/2/12
 * desc: 登录body
 */
class LoginRequest : Serializable {
    var userName: String? = null
    var password: String? = null
}