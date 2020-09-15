package com.android.lib.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.provider.Settings
import com.android.lib.Logger.e

/**
 * date: 2019/1/30
 * desc: 判断是否有网络
 */
object NetworkUtil {

    private val TAG = NetworkUtil::class.java.simpleName

    /**
     * 判断网络是否可用
     *
     * @param context 上下文
     * @return true & false
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetInfo = cm.activeNetworkInfo
        return if (null == activeNetInfo || !activeNetInfo.isAvailable || !activeNetInfo.isConnected) {
            e(TAG, "NetWork is unavailable")
            false
        } else {
            true
        }
    }

    /**
     * 判断wifi 的设置 是否是一直 链接
     *
     * @return true一直链接或者当前网络不是wifi，false当前网络是wifi并且设置不是一直链接
     */
    fun isWifiAlwaysConnect(context: Context): Boolean {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (cm != null) {
            val info = cm.activeNetworkInfo
            if (info != null && info.type == ConnectivityManager.TYPE_WIFI) {
                val wifiSleepPolicy = Settings.System.getInt(
                    context.contentResolver,
                    "wifi_sleep_policy",
                    Settings.System.WIFI_SLEEP_POLICY_DEFAULT
                )
                if (wifiSleepPolicy != Settings.System.WIFI_SLEEP_POLICY_NEVER) {
                    return false
                }
            }
            true
        } else {
            false
        }
    }

    fun getNetworkName(context: Context): String {
        val cm =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return if (cm != null) {
            val info = cm.activeNetworkInfo
            if (info == null) "No network" else info.typeName
        } else {
            "No network"
        }
    }

    /**
     * 检测当的网络状态
     *
     * @param context Context
     * @return true 表示网络可用
     */
    fun isNetworkAvailables(context: Context): Boolean {
        val connectivity =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivity != null) {
            val info = connectivity.activeNetworkInfo
            if (info != null && info.isConnected) { // 当前网络是连接的
                if (info.state == NetworkInfo.State.CONNECTED) { // 当前所连接的网络可用
                    return true
                }
            }
        }
        return false
    }
}