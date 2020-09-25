package com.android.mvvm.https.builder

import android.text.TextUtils
import com.android.lib.Logger.e
import com.android.mvvm.https.NetWorkManager
import com.android.mvvm.https.callback.OkHttpCallback
import com.android.mvvm.https.network.OkHttpRequestBuilder
import com.android.mvvm.https.response.OkHttpResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * date: 2019/2/13
 * desc: put请求
 */
class PutBuilder(request: NetWorkManager) :
    OkHttpRequestBuilder<PutBuilder>(request) {

    companion object {
        private const val TAG = "PutBuilder"
    }

    override fun enqueue(
        requestCode: Int,
        okHttpResponse: OkHttpResponse
    ) {
        try {
            // 参数为false时 抛出 IllegalArgumentException
            require(!TextUtils.isEmpty(mUrl)) { "url can not be null !" }
            val builder = Request.Builder().url(mUrl)
            appendHeaders(builder, mHeaders) // 根据需要添加head
            if (mTag != null) {
                builder.tag(mTag)
            }
            val mediaType = "text/plain;charset=utf-8".toMediaTypeOrNull()
            builder.put("".toRequestBody(mediaType))
            val putRequest = builder.build()
            mNetManager.okHttpClient
                .newCall(putRequest)
                .enqueue(OkHttpCallback(requestCode, okHttpResponse))
        } catch (e: Exception) {
            e(TAG, "Put enqueue error:" + e.message)
            okHttpResponse.onDataFailure(requestCode, 0, "Put enqueue error", false)
        }
    }

}