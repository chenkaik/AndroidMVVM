package com.android.mvvm.ui

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.lib.util.kotlin.startActivity
import com.android.mvvm.R
import com.android.mvvm.activity.BaseActivity
import com.android.mvvm.util.UserConfig
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.common_head_layout.*

class LoginActivity : BaseActivity() {

    companion object {
        private const val TAG = "LoginActivity"
        fun actionStart(activity: FragmentActivity, isPutStack: Boolean) {
            startActivity<LoginActivity>(activity, isPutStack)
        }
    }

    private val viewModel by lazy { ViewModelProvider(this)[LoginViewModel::class.java] }

    override fun getLayoutId() = R.layout.activity_login

    override fun initView() {
        navigationBar.setTitle("登录")
        login.setOnClickListener {
            UserConfig.clearToken()
            viewModel.login("lixiangbin", "shjacf")
        }
        viewModel.liveData.observe(this, Observer {
            val loginResponse = it.getOrNull()
            if (loginResponse != null) {
                Toast.makeText(this, "登录成功${loginResponse.MSG_BODY.token}", Toast.LENGTH_SHORT)
                    .show()
//                loginResponse.SYS_HEAD?.RET?.RET_MSG
            } else {
                Toast.makeText(
                    this,
                    "登录失败 ${loginResponse?.SYS_HEAD?.RET?.RET_MSG}",
                    Toast.LENGTH_SHORT
                ).show()
                it.exceptionOrNull()?.printStackTrace()
            }
        })
    }

    override fun initData() {
    }

}