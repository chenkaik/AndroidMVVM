package com.android.mvvm.https.builder

import com.android.lib.Logger.e
import com.android.mvvm.https.callback.OkHttpCallback
import com.android.mvvm.https.network.NetWorkRequest
import com.android.mvvm.https.network.OkHttpRequestBuilderHasParam
import com.android.mvvm.https.response.NetworkOkHttpResponse
import okhttp3.Request

/**
 * date: 2019/2/13
 * desc: get请求
 */
class GetBuilder(request: NetWorkRequest) :
    OkHttpRequestBuilderHasParam<GetBuilder>(request) {

    companion object {
        private const val TAG = "GetBuilder"
    }

    override fun enqueue(
        requestCode: Int,
        okHttpResponse: NetworkOkHttpResponse
    ) {
        try {
//            require(!(mUrl == null || mUrl!!.isEmpty())) { "url can not be null !" }
            if (mParams != null && mParams!!.isNotEmpty()) {
                mUrl = appendParams(mUrl, mParams!!) // 拼接参数
            }
            val builder = Request.Builder().url(mUrl).get()
            appendHeaders(builder, mHeaders) // 根据需要添加head
            if (mTag != null) {
                builder.tag(mTag)
            }
            val getRequest = builder.build()
            request.okHttpClient
                .newCall(getRequest)
                .enqueue(OkHttpCallback(requestCode, okHttpResponse))
        } catch (e: Exception) {
            e(TAG, "Get enqueue error:" + e.message)
            okHttpResponse.onDataFailure(requestCode, 0, e.message, false)
        }
    }

}