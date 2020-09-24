package com.android.mvvm.activity

import android.view.View
import com.android.mvvm.R
import com.android.mvvm.https.response.BaseResponse
import com.android.mvvm.https.response.NetworkOkHttpResponse
import com.android.mvvm.https.response.NetworkResponse
import kotlinx.android.synthetic.main.activity_http.*
import kotlinx.android.synthetic.main.common_head_layout.*

class HttpActivity : BaseActivity(), View.OnClickListener, NetworkResponse, NetworkOkHttpResponse {

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
                okHttp.text = "哈哈"
            }
            R.id.okHttpOther -> {

            }
            R.id.retrofit -> {

            }
            R.id.retrofitOther -> {

            }
        }
    }

    override fun onDataReady(response: BaseResponse) {

    }

    override fun onDataError(
        requestCode: Int,
        responseCode: Int,
        message: String?,
        isOverdue: Boolean
    ) {

    }

    override fun showLoading(msg: String?) {

    }

    override fun dismissLoading() {

    }

    override fun onDataSuccess(requestCode: Int, obj: Any?, json: String) {

    }

    override fun onDataFailure(
        requestCode: Int,
        responseCode: Int,
        message: String?,
        isOverdue: Boolean
    ) {

    }

}
