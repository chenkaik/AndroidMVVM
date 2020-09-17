package com.android.lib.util

import android.util.Base64
import java.nio.charset.StandardCharsets

/**
 * date: 2020/8/5
 * desc: Base64加密与解密字符串
 */
object Base64Util {
    /**
     * 加密
     *
     * @param str 待加密字符串
     * @return 加密结果
     */
    fun getBase64(str: String): String {
        var result = ""
        try {
            result = String(
                Base64.encode(
                    str.toByteArray(StandardCharsets.UTF_8),
                    Base64.NO_WRAP
                ), StandardCharsets.UTF_8
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    /**
     * 解密
     *
     * @param str 待解密字符串
     * @return 解密结果
     */
    fun getFromBase64(str: String): String {
        var result = ""
        try {
            result = String(
                Base64.decode(str, Base64.NO_WRAP),
                StandardCharsets.UTF_8
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

}