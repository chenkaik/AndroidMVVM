package com.android.mvvm.https.builder

import com.android.lib.Logger.e
import com.android.mvvm.https.callback.OkHttpCallback
import com.android.mvvm.https.network.NetWorkRequest
import com.android.mvvm.https.network.OkHttpRequestBuilderHasParam
import com.android.mvvm.https.response.NetworkOkHttpResponse
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

/**
 * date: 2019/2/13
 * desc: post请求
 * https://blog.csdn.net/qq_19306415/article/details/102954712?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522158659699019726867824966%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&request_id=158659699019726867824966&biz_id=0&utm_source=distribute.pc_search_result.none-task-blog-soetl_so_first_rank_v2_rank_v25-1
 */
class PostBuilder(request: NetWorkRequest, private val isForm: Boolean) :
    OkHttpRequestBuilderHasParam<PostBuilder>(request) {

    private var mJsonParams = ""

    companion object {
        private const val TAG = "PostBuilder"
    }

    /**
     * json格式参数(优先)
     *
     * @param json 提交的json数据
     * @return this
     */
    fun jsonParams(json: String): PostBuilder {
        mJsonParams = json
        return this
    }

    override fun enqueue(requestCode: Int, okHttpResponse: NetworkOkHttpResponse) {
        try {
            val builder: Request.Builder = Request.Builder().url(mUrl)
            appendHeaders(builder, mHeaders) // 根据需要添加head
            if (mTag != null) {
                builder.tag(mTag)
            }
            if (mJsonParams.isNotEmpty()) { // 优先提交json格式参数
                val mediaType = "application/json;charset=utf-8".toMediaTypeOrNull()
                val body: RequestBody = mJsonParams.toRequestBody(mediaType)
                builder.post(body)
            } else {
                if (isForm) {
                    val multipartBuilder =
                        MultipartBody.Builder().setType(MultipartBody.FORM) // 表单类型
                    appendParams(multipartBuilder, mParams!!) // from参数
                    builder.post(multipartBuilder.build())
                } else { // 普通kv参数
                    val encodingBuilder = FormBody.Builder()
                    appendParams(encodingBuilder, mParams!!)
                    builder.post(encodingBuilder.build())
                }
            }
            val postRequest = builder.build()
            request.okHttpClient
                .newCall(postRequest)
                .enqueue(OkHttpCallback(requestCode, okHttpResponse))
        } catch (e: Exception) {
            e(TAG, "Post enqueue error:" + e.message)
            okHttpResponse.onDataFailure(requestCode, 0, e.message, false)
        }
    }

}
