package com.android.mvvm.https.builder

import com.android.lib.Logger.e
import com.android.mvvm.https.callback.OkHttpCallback
import com.android.mvvm.https.network.NetWorkRequest
import com.android.mvvm.https.network.OkHttpRequestBuilder
import com.android.mvvm.https.response.NetworkOkHttpResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * date: 2019/2/13
 * desc: put请求
 */
class PutBuilder(request: NetWorkRequest) :
    OkHttpRequestBuilder<PutBuilder>(request) {

    companion object {
        private const val TAG = "PutBuilder"
    }

    override fun enqueue(
        requestCode: Int,
        okHttpResponse: NetworkOkHttpResponse
    ) {
        try {
//            require(!(mUrl == null || mUrl.length == 0)) { "url can not be null !" }
            val builder = Request.Builder().url(mUrl)
            appendHeaders(builder, mHeaders) // 根据需要添加head
            if (mTag != null) {
                builder.tag(mTag)
            }
            val mediaType = "text/plain;charset=utf-8".toMediaTypeOrNull()
            builder.put("".toRequestBody(mediaType))
            val putRequest = builder.build()
            request.okHttpClient
                .newCall(putRequest)
                .enqueue(OkHttpCallback(requestCode, okHttpResponse))
        } catch (e: Exception) {
            e(TAG, "Put enqueue error:" + e.message)
            okHttpResponse.onDataFailure(requestCode, 0, e.message, false)
        }
    }

}