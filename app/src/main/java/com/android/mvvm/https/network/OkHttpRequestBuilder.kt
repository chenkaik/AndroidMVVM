package com.android.mvvm.https.network

import com.android.mvvm.https.NetWorkManager
import com.android.mvvm.https.response.NetworkOkHttpResponse
import okhttp3.Headers
import okhttp3.Request
import java.util.*

/**
 * date: 2019/2/13
 * desc: 不带param的base request body
 */
@Suppress("UNCHECKED_CAST")
abstract class OkHttpRequestBuilder<T : OkHttpRequestBuilder<T>>(request: NetWorkManager) {

    protected lateinit var mUrl: String
    protected var mTag: Any? = null
    protected var mHeaders: MutableMap<String, String>? = null
    protected var mNetManager: NetWorkManager = request

    /**
     * 异步执行
     *
     * @param requestCode    区分请求的code
     * @param okHttpResponse 自定义回调
     */
    abstract fun enqueue(requestCode: Int, okHttpResponse: NetworkOkHttpResponse)

    /**
     * set url
     *
     * @param url url
     * @return T
     */
    fun url(url: String): T {
        mUrl = url
        return this as T
    }

    /**
     * set tag
     *
     * @param tag tag
     * @return T
     */
    fun tag(tag: Any?): T {
        mTag = tag
        return this as T
    }

    /**
     * set headers
     *
     * @param headers headers
     * @return T
     */
    fun headers(headers: MutableMap<String, String>): T {
        mHeaders = headers
        return this as T
    }

    /**
     * set one header
     *
     * @param key header key
     * @param value header val
     * @return T
     */
    fun addHeader(key: String, value: String): T {
        if (mHeaders == null) {
            mHeaders = LinkedHashMap()
        }
//        mHeaders!![key] = value
        mHeaders?.let {
            it[key] = value
        }
        return this as T
    }

    /**
     * append headers into builder
     *
     * @param builder Request.Builder
     * @param headers head参数
     */
    fun appendHeaders(
        builder: Request.Builder,
        headers: Map<String, String>?
    ) {
        if (headers == null || headers.isEmpty()) {
            return
        }
        val headerBuilder = Headers.Builder()
        for (key in headers.keys) {
            headers[key]?.let { headerBuilder.add(key, it) }
        }
        builder.headers(headerBuilder.build())
    }


}