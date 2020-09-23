package com.android.mvvm.https.config

/**
 * date: 2019/1/30
 * desc: 配置服务端地址
 */
object HttpConfig {
    var INDEX_URL = "https://www.lixiaotech.com/#/mobileInterface"
    // 正式环境
    var BASE_URL = "https://www.shjacf.com/server/"
    //    public static String INDEX_URL = "https://www.shjacf.com/#/mobileInterface";
    var READ_TIMEOUT = 15L
    var WRITE_TIMEOUT = 15L
    var CONNECT_TIMEOUT = 15L
    const val SUCCESS = "000000" // 成功
    const val TOKENERROR = "100403" // 登录失效
    const val FAILED = "error" // 失败
}