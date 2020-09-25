package com.android.mvvm.https.builder

import android.text.TextUtils
import com.android.lib.Logger.e
import com.android.mvvm.https.NetWorkManager
import com.android.mvvm.https.callback.OkHttpCallback
import com.android.mvvm.https.network.OkHttpRequestBuilderHasParam
import com.android.mvvm.https.response.OkHttpResponse
import okhttp3.Request

/**
 * date: 2019/2/13
 * desc: get请求
 */
class GetBuilder(request: NetWorkManager) :
    OkHttpRequestBuilderHasParam<GetBuilder>(request) {

    companion object {
        private const val TAG = "GetBuilder"
    }

    override fun enqueue(
        requestCode: Int,
        okHttpResponse: OkHttpResponse
    ) {
        try {
            // 参数为false时 抛出 IllegalArgumentException
            require(!TextUtils.isEmpty(mUrl)) { "url can not be null !" }
//            if (TextUtils.isEmpty(mUrl)){
//                throw IllegalArgumentException("url can not be null !")
//            }
            if (mParams != null && mParams!!.isNotEmpty()) {
                mUrl = appendParams(mUrl, mParams) // 拼接参数
            }
            val builder = Request.Builder().url(mUrl).get()
            appendHeaders(builder, mHeaders) // 根据需要添加head
            if (mTag != null) {
                builder.tag(mTag)
            }
            val getRequest = builder.build()
            mNetManager.okHttpClient
                .newCall(getRequest)
                .enqueue(OkHttpCallback(requestCode, okHttpResponse))
        } catch (e: Exception) {
            e(TAG, "Get enqueue error:" + e.message)
            okHttpResponse.onDataFailure(requestCode, 0, e.message + "Get enqueue error", false)
        }
    }

}