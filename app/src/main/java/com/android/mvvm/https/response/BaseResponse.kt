package com.android.mvvm.https.response

/**
 * date: 2020/9/23
 * desc: 根据需要自由定制修改
 */
open class BaseResponse(
    val APP_HEAD: APPHEAD? = null,
    val SYS_HEAD: SYSHEAD? = null
) {
    // 接口请求requestCode,用于区分多个请求同时发起的情况
    var requestCode = 0
}

data class APPHEAD(
    val PAGE_SIZE: Int,
    val START_ROW: Int,
    val TOTAL_COUNT: Int
)

data class SYSHEAD(
    val RET: RET
)

data class RET(
    val RET_CODE: String,
    val RET_MSG: String,
    val RET_STATUS: String
)
