package com.android.lib.listener

/**
 * date: 2017/11/17.
 * desc: 用于权限申请回调
 */
interface PermissionListener {

    fun onGranted() // 同意权限

    fun onDenied(deniedPermissions: List<String?>?) // 拒绝权限

}