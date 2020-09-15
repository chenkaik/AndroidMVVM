package com.android.lib.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.TypedValue
import android.view.WindowManager
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

/**
 * date: 2019/1/30
 * desc: 系统版本信息相关
 */
object SystemInfo {

    /**
     * >=5.0 21
     */
    fun hasLollipop(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
    }

    /**
     * >=6.0 23
     */
    fun hasM(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    /**
     * >=7.0 24
     */
    fun hasN(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
    }

    /**
     * >=8.0 26
     */
    fun hasO(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
    }

    /**
     * >=9.0 28
     */
    fun hasP(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.P
    }

    /**
     * >=10.0 29
     */
    fun hasQ(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    }

    /**
     * 获得当前手机的系统版本
     *
     * @return
     */
    val osVersion: String
        get() = Build.VERSION.RELEASE

    val sDKVersionInt: Int
        get() = Build.VERSION.SDK_INT

    val sDKVersion: String
        get() = Build.VERSION.SDK

    fun dipToPX(ctx: Context, dip: Float): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dip,
            ctx.resources.displayMetrics
        ).toInt()
    }

    /**
     * 获取CPU的信息
     *
     * @return
     */
    val cpuInfo: String?
        get() {
            var cpuInfo = ""
            try {
                if (File("/proc/cpuinfo").exists()) {
                    val fr = FileReader("/proc/cpuinfo")
                    val localBufferedReader = BufferedReader(fr, 8192)
                    cpuInfo = localBufferedReader.readLine()
                    localBufferedReader.close()
                    if (cpuInfo != null) {
                        cpuInfo =
                            cpuInfo.split(":").toTypedArray()[1].trim { it <= ' ' }.split(" ")
                                .toTypedArray()[0]
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return cpuInfo
        }

    /**
     * 判断是否支持闪光灯
     */
    fun isSupportCameraLedFlash(pm: PackageManager?): Boolean {
        if (pm != null) {
            val features = pm.systemAvailableFeatures
            if (features != null) {
                for (f in features) {
                    if (f != null && PackageManager.FEATURE_CAMERA_FLASH == f.name) { //判断设备是否支持闪光灯
                        return true
                    }
                }
            }
        }
        return false
    }

    /**
     * 获取屏幕宽度
     */
    fun getScreenWidth(context: Context): Int {
        val display =
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        return display.width
    }

    fun getScreenHeight(context: Context): Int {
        val display =
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        return display.height
    }

    /**
     * 获得当前软件的版本全称
     *
     * @return 版本名称
     */
    fun getVersionName(c: Context): String {
        val manager = c.packageManager
        var verName = "1.0.0"
        verName = try {
            val info = manager.getPackageInfo(c.packageName, 0)
            info.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            return verName
        }
        return verName
    }

    /**
     * 获得当前软件的版本号
     *
     * @return 版本号
     */
    fun getVersionCode(c: Context): Int {
        val manager = c.packageManager
        var verCode = 0
        verCode = try {
            val info = manager.getPackageInfo(c.packageName, 0)
            if (hasP()) {
                info.longVersionCode.toInt()
            } else {
                val versionCode = info.versionCode
                versionCode
            }
        } catch (e: PackageManager.NameNotFoundException) {
            return verCode
        }
        return verCode
    }
}