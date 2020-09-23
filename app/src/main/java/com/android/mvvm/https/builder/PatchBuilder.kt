package com.android.mvvm.https.builder

import android.text.TextUtils
import com.android.lib.Logger.e
import com.android.mvvm.https.NetWorkManager
import com.android.mvvm.https.callback.OkHttpCallback
import com.android.mvvm.https.network.OkHttpRequestBuilder
import com.android.mvvm.https.response.NetworkOkHttpResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * date: 2019/2/13
 * desc: patch请求
 */
class PatchBuilder(request: NetWorkManager) :
    OkHttpRequestBuilder<PatchBuilder>(request) {

    companion object {
        private const val TAG = "PatchBuilder"
    }

    override fun enqueue(
        requestCode: Int,
        okHttpResponse: NetworkOkHttpResponse
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
            builder.patch("".toRequestBody(mediaType))
            val patchRequest = builder.build()
            mNetManager.okHttpClient
                .newCall(patchRequest)
                .enqueue(OkHttpCallback(requestCode, okHttpResponse))
        } catch (e: Exception) {
            e(TAG, "Patch enqueue error:" + e.message)
            okHttpResponse.onDataFailure(requestCode, 0, e.message, false)
        }
    }

}