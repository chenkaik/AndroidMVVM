package com.android.mvvm.https.builder

import com.android.lib.Logger.e
import com.android.mvvm.https.callback.OkHttpCallback
import com.android.mvvm.https.network.NetWorkRequest
import com.android.mvvm.https.network.OkHttpRequestBuilderHasParam
import com.android.mvvm.https.response.NetworkOkHttpResponse
import okhttp3.Request

/**
 * date: 2019/2/13
 * desc: delete请求
 */
class DeleteBuilder(request: NetWorkRequest) :
    OkHttpRequestBuilderHasParam<DeleteBuilder>(request) {

    companion object {
        private const val TAG = "DeleteBuilder"
    }

    override fun enqueue(
        requestCode: Int,
        okHttpResponse: NetworkOkHttpResponse
    ) {
        try {
//            require(!(mUrl == null || mUrl.length == 0)) { "url can not be null !" }
            val builder = Request.Builder().url(mUrl).delete()
            appendHeaders(builder, mHeaders) // 根据需要添加head
            if (mTag != null) {
                builder.tag(mTag)
            }
            val deleteRequest = builder.build()
            request.okHttpClient
                .newCall(deleteRequest)
                .enqueue(OkHttpCallback(requestCode, okHttpResponse))
        } catch (e: Exception) {
            e(
                TAG,
                "Delete enqueue error:" + e.message
            )
            okHttpResponse.onDataFailure(requestCode, 0, e.message, false)
        }
    }

}