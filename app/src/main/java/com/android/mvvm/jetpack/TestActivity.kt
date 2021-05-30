package com.android.mvvm.jetpack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.android.lib.util.kotlin.startActivity
import com.android.mvvm.R
import com.android.mvvm.activity.BaseActivity
import com.android.mvvm.activity.HttpActivity
import com.android.mvvm.databinding.ActivityTestBinding
import com.android.mvvm.entity.request.LoginRequest
import com.android.mvvm.jetpack.api.ApiHelper
import com.android.mvvm.jetpack.base.ViewModelFactory
import com.android.mvvm.jetpack.http.NetServiceCreatorHelper
import com.android.mvvm.jetpack.utils.Status
import com.android.mvvm.jetpack.viewmodel.TestViewModel
import com.android.mvvm.util.Person

class TestActivity : BaseActivity() {

    private lateinit var activityTestBinding: ActivityTestBinding

    companion object {
        private const val TAG = "TestActivity"
        fun actionStart(activity: FragmentActivity, isPutStack: Boolean) {
            startActivity<TestActivity>(activity, isPutStack)
        }
    }

    private val testViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(NetServiceCreatorHelper.apiService))
        )[TestViewModel::class.java]
    }

    override fun getLayoutView(): View {
        activityTestBinding = ActivityTestBinding.inflate(layoutInflater)
        return activityTestBinding.root
    }

    override fun initView() {
        activityTestBinding.btnTest.setOnClickListener {
            testViewModel.login(LoginRequest("shjacf", "lixiangbi")).observe(this, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            activityTestBinding.progressBar.visibility = View.GONE
                            resource.data?.let { loginResponse ->
                                Toast.makeText(
                                    this,
                                    loginResponse.MSG_BODY.token,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        Status.ERROR -> {
                            activityTestBinding.progressBar.visibility = View.GONE
                            resource.message?.let { msg ->
                                Toast.makeText(
                                    this,
                                    msg,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        Status.LOADING -> {
                            activityTestBinding.progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            })
        }
    }

    override fun initData() {
    }

}