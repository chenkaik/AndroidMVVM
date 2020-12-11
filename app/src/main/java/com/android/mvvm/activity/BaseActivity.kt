package com.android.mvvm.activity

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.lib.listener.PermissionListener
import java.util.*

/**
 * date: 2020/9/21
 * desc: 基类
 */
abstract class BaseActivity : AppCompatActivity() {

    private lateinit var mPermissionListener: PermissionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutView())
        initView()
        initData()
    }

    // 引入布局
    protected abstract fun getLayoutView(): View

    // 初始化控件
    protected abstract fun initView()

    // 初始化数据
    protected abstract fun initData()


    /**
     * 申请运行时权限
     *
     * @param permissions 申请的权限名
     * @param listener    权限授权的回调
     */
    open fun requestRuntimePermission(
        permissions: Array<String>,
        listener: PermissionListener
    ) {
        mPermissionListener = listener
        val permissionList: MutableList<String> =
            ArrayList()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                permissionList.add(permission)
            }
        }
        if (permissionList.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionList.toTypedArray(), 1)
        } else {
            mPermissionListener.onGranted() // 全部授权
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty()) {
                val deniedPermissions: MutableList<String> =
                    ArrayList()
                for (i in grantResults.indices) {
                    val grantResult = grantResults[i] // 判断是否授权
                    val permission = permissions[i] // 请求权限的名字
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        deniedPermissions.add(permission)
                    }
                }
                if (deniedPermissions.isEmpty()) {
                    mPermissionListener.onGranted()
                } else {
                    mPermissionListener.onDenied(deniedPermissions)
                }
            }
        }
    }

}