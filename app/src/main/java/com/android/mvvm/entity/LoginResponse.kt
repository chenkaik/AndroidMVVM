package com.android.mvvm.entity

import com.android.mvvm.https.response.BaseResponse

/**
 * date: 2020/9/24
 * desc:
 */
data class LoginResponse(
    val MSG_BODY: MSGBODY
) : BaseResponse()

data class MSGBODY(
    val acctNo: String,
    val orgId: String,
    val token: String,
    val userName: String,
    val userType: String
)