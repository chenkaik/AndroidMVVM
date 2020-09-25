package com.android.mvvm.entity

import com.android.mvvm.https.response.BaseResponse

/**
 * date: 2020/9/25
 * desc:
 */
data class NumberResponse(
    val MSG_BODY: MSGBODY
) : BaseResponse(){

    data class MSGBODY(
        val Result: String
    )

}

