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
 * desc: patch请求
 */
class PatchBuilder(request: NetWorkRequest) :
    OkHttpRequestBuilder<PatchBuilder>(request) {

    companion object {
        private const val TAG = "PatchBuilder"
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
            builder.patch("".toRequestBody(mediaType))
            val patchRequest = builder.build()
            request.okHttpClient
                .newCall(patchRequest)
                .enqueue(OkHttpCallback(requestCode, okHttpResponse))
        } catch (e: Exception) {
            e(TAG, "Patch enqueue error:" + e.message)
            okHttpResponse.onDataFailure(requestCode, 0, e.message, false)
        }
    }

}