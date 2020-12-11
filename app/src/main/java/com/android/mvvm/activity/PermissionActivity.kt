package com.android.mvvm.activity

import android.Manifest
import android.R.attr.phoneNumber
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.android.lib.util.SystemInfo
import com.android.lib.util.kotlin.startActivity
import com.android.mvvm.MyApplication.Companion.context
import com.android.mvvm.R
import com.android.mvvm.databinding.ActivityPermissionBinding
import com.android.mvvm.databinding.CommonHeadLayoutBinding
import com.android.mvvm.util.CustomDialog
import com.permissionx.guolindev.PermissionX


class PermissionActivity : BaseActivity() {

    private lateinit var activityPermissionBinding: ActivityPermissionBinding
    private lateinit var commonHeadLayoutBinding: CommonHeadLayoutBinding

    companion object {
        private const val TAG = "PermissionActivity"
        fun actionStart(activity: FragmentActivity, isPutStack: Boolean) {
            startActivity<PermissionActivity>(activity, isPutStack)
        }
    }

    override fun getLayoutView(): View {
        activityPermissionBinding = ActivityPermissionBinding.inflate(layoutInflater)
        commonHeadLayoutBinding = activityPermissionBinding.commonHead
        return activityPermissionBinding.root
    }

    override fun initView() {
        commonHeadLayoutBinding.navigationBar.setTitle("权限申请")
    }

    override fun initData() {
        activityPermissionBinding.requestPermission.setOnClickListener {
            val permissionList = ArrayList<String>()
            permissionList.add(Manifest.permission.CALL_PHONE)
            permissionList.add(Manifest.permission.CAMERA)
            permissionList.add(Manifest.permission.READ_CONTACTS)
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION)
            if (SystemInfo.hasQ()) {
                permissionList.add(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            }
            PermissionX.init(this)
                .permissions(permissionList)
//                .setDialogTintColor(Color.parseColor("#008577"), Color.parseColor("#83eBdd")) // 第一个参数是设置浅色主题下的颜色值 第二个参数是设置深色主题下的颜色值
                // 先弹出一个对话框向用户解释自己需要哪些权限，然后才会进行权限申请
                .explainReasonBeforeRequest()
                // 用于监听那些被用户拒绝，而又可以再次去申请的权限
                .onExplainRequestReason { scope, deniedList, beforeRequest ->
//                    val message = "XXX需要您同意以下权限才能正常使用"
//                    val dialog = CustomDialog(this, message, deniedList)
//                    scope.showRequestReasonDialog(dialog)
                    scope.showRequestReasonDialog(deniedList, "XXX需要您同意以下权限才能正常使用", "确定", "取消")
                }
                // 用于监听那些被用户永久拒绝的权限。另外从方法名上就可以看出，我们可以在这里提醒用户手动去应用程序设置当中打开权限
                // 针对那些被永久拒绝的权限向用户解释为什么它们是必须的，并自动跳转到应用设置当中提醒用户手动开启权限
                .onForwardToSettings { scope, deniedList ->
//                    val message = "您需要去设置中手动开启以下权限"
//                    val dialog = CustomDialog(this, message, deniedList)
//                    scope.showForwardToSettingsDialog(dialog)
                    scope.showForwardToSettingsDialog(
                        deniedList,
                        "您需要去应用程序设置当中手动开启权限",
                        "确定",
                        "取消"
                    )
                }
                .request { allGranted, grantedList, deniedList ->
                    if (allGranted) {
//                        call()
                        Toast.makeText(this, "所有申请的权限都已通过", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "您拒绝了如下权限: $deniedList", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun call() {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:10086"))
        startActivity(intent)
    }

}