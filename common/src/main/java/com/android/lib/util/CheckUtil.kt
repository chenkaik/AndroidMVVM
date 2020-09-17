package com.android.lib.util

import android.text.TextUtils
import java.util.regex.Pattern

/**
 * date: 2020/8/5
 * desc: 校验工具类
 */
object CheckUtil {

    /**
     * 密码不能小于8位且大小写字母、数字、字符至少包含3种
     *
     * @param password 密码
     * @return 匹配上返回true
     */
    fun rexCheckPassword(password: String): Boolean {
        return if (TextUtils.isEmpty(password)) {
            false
        } else { // 8-20 位，字母、数字、字符
            val regStr =
                "^(?![a-zA-Z]+$)(?![A-Z0-9]+$)(?![A-Z\\W_]+$)(?![a-z0-9]+$)(?![a-z\\W_]+$)(?![0-9\\W_]+$)[a-zA-Z0-9\\W_]{8,20}$"
            val regex = Pattern.compile(regStr)
            val matcher = regex.matcher(password)
            matcher.matches()
        }
    }
    //    /**
//     * 校验邮箱
//     *
//     * @param email 邮箱
//     * @return 匹配上返回true
//     */
//    public static boolean rexCheckEmail(String email) {
//        if (TextUtils.isEmpty(email)) {
//            return false;
//        } else {
//            String regStr = "^([a-zA-Z]|[0-9])(\\\\w|\\\\-)+@[a-zA-Z0-9]+\\\\.([a-zA-Z]{2,4})$";
//            return email.matches(regStr);
//        }
//    }
    /**
     * 校验邮箱
     *
     * @param email 邮箱
     * @return 结果
     */
    fun checkEmail(email: String): Boolean {
        var flag = false
        flag = try {
            val check =
                "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"
            val regex = Pattern.compile(check)
            val matcher = regex.matcher(email)
            matcher.matches()
        } catch (e: Exception) {
            false
        }
        return flag
    }

}

