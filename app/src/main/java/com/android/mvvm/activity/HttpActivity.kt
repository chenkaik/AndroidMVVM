package com.android.mvvm.activity

import android.view.View
import android.widget.Toast
import com.android.lib.Logger
import com.android.lib.util.GsonUtil
import com.android.mvvm.R
import com.android.mvvm.entity.LoginResponse
import com.android.mvvm.entity.NumberResponse
import com.android.mvvm.entity.request.LoginRequest
import com.android.mvvm.https.ApiManager
import com.android.mvvm.https.NetWorkManager
import com.android.mvvm.https.response.BaseResponse
import com.android.mvvm.https.response.OkHttpResponse
import com.android.mvvm.https.response.RetrofitResponse
import com.android.mvvm.util.UserConfig
import com.android.mvvm.util.showToast
import kotlinx.android.synthetic.main.activity_http.*
import kotlinx.android.synthetic.main.common_head_layout.*
import org.json.JSONObject

class HttpActivity : BaseActivity(), View.OnClickListener, RetrofitResponse, OkHttpResponse {

    companion object {
        private const val TAG = "HttpActivity"
    }

    override fun getLayoutId() = R.layout.activity_http

    override fun initView() {
        navigationBar.setTitle("接口调用")
        okHttp.setOnClickListener(this)
        okHttpOther.setOnClickListener(this)
        retrofit.setOnClickListener(this)
        retrofitOther.setOnClickListener(this)
    }

    override fun initData() {
//        okHttp.setOnClickListener {
//            okHttp.text = "哈哈"
//        }
    }

    override fun onResume() {
        super.onResume()
//        okHttp.text = "哈哈"
//        navigationBar.setTitle("接口调用")
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.okHttp -> {
                UserConfig.clearToken()
                val map = HashMap<String, String>()
                map["username"] = "lixiangbin"
                map["password"] = "shjacf"
                NetWorkManager.instance
                    .post(true)
                    .url("https://www.shjacf.com/server/login")
                    .jsonParams(JSONObject(map as Map<*, *>).toString())
                    .tag(this)
                    .enqueue(1, this)
            }
            R.id.okHttpOther -> {
                NetWorkManager.instance
                    .get()
                    .url("https://www.shjacf.com/server/api-user/file/reference/PW")
                    .tag(this)
                    .enqueue(2, this)
            }
            R.id.retrofit -> {
                UserConfig.clearToken()
                NetWorkManager.instance.asyncNetWork(
                    TAG, 1, ApiManager
                        .service()
                        .login(LoginRequest("shjacf", "lixiangbin")), this, true
                )
            }
            R.id.retrofitOther -> {
                NetWorkManager.instance.asyncNetWork(
                    TAG, 2, ApiManager
                        .service()
                        .getNumber("PW"), this, true
                )
            }
        }
    }

    override fun onDataReady(response: BaseResponse) {
        when (response.requestCode) {
            1 -> {
                val loginResponse = response as LoginResponse
                Logger.e(TAG, loginResponse.MSG_BODY.token)
                UserConfig.putToken(loginResponse.MSG_BODY.token)
                Toast.makeText(
                    this,
                    "Retrofit登录成功 ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            2 -> {
                val numberResponse = response as NumberResponse
                Logger.e(TAG, numberResponse.MSG_BODY.Result)
                Toast.makeText(
                    this,
                    "Retrofit请求 " + numberResponse.MSG_BODY.Result,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDataError(
        requestCode: Int,
        responseCode: Int,
        message: String,
        isOverdue: Boolean
    ) {
        message.showToast()
    }

    override fun showLoading(msg: String?) {

    }

    override fun dismissLoading() {

    }

    override fun onDataSuccess(requestCode: Int, obj: Any?, json: String) {
        when (requestCode) {
            1 -> {
                val loginResponse = GsonUtil.fromJson(json, LoginResponse::class.java)
                if (loginResponse != null) {
                    Logger.e(TAG, loginResponse.MSG_BODY.token)
                    UserConfig.putToken(loginResponse.MSG_BODY.token)
                    Toast.makeText(
                        this,
                        "OkHttp登录成功 ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            2 -> {
                val numberResponse = GsonUtil.fromJson(json, NumberResponse::class.java)
                if (numberResponse != null) {
                    Logger.e(TAG, numberResponse.MSG_BODY.Result)
                    Toast.makeText(
                        this,
                        "OkHttp请求 " + numberResponse.MSG_BODY.Result,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDataFailure(
        requestCode: Int,
        responseCode: Int,
        message: String,
        isOverdue: Boolean
    ) {
        message.showToast()
    }

}
