package com.android.lib.util

import android.accounts.NetworkErrorException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * date: 2019/1/30
 * desc: 处理接口调用失败
 */
object NetErrStringUtil {

    private const val ERR_404 = 404
    private const val ERR_500 = 500
    private const val ERR_502 = 502

    fun getErrString(code: Int): String {
        return when (code) {
            ERR_404 -> "无法找到指定位置的资源"
            ERR_500 -> "内部服务器错误"
            ERR_502 -> "网关错误"
            else -> "网络错误"
        }
    }

    fun getErrString(t: Throwable?): String {
        return if (t is SocketTimeoutException
            || t is ConnectException
            || t is TimeoutException
            || t is NetworkErrorException
            || t is UnknownHostException
        ) {
            "网络连接超时"
        } else if (t?.message != null && t.message.equals(
                "canceled",
                ignoreCase = true
            )
        ) {
            ""
        } else {
            "网络错误"
        }
    }

}