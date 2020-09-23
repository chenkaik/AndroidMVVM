package com.android.mvvm.https.network

import com.android.mvvm.https.NetWorkManager
import okhttp3.FormBody
import okhttp3.MultipartBody
import java.util.*

/**
 * date: 2019/2/13
 * desc: 带有param的base request body
 */
@Suppress("UNCHECKED_CAST")
abstract class OkHttpRequestBuilderHasParam<T : OkHttpRequestBuilderHasParam<T>>(
    request: NetWorkManager
) : OkHttpRequestBuilder<T>(request) {

    protected var mParams: MutableMap<String, String>? = null

    /**
     * set Map params
     *
     * @param params 参数
     * @return T
     */
    fun params(params: MutableMap<String, String>): T {
        mParams = params
        return this as T
    }

    /**
     * add param
     *
     * @param key param key
     * @param value param val
     * @return T
     */
    fun addParam(key: String, value: String): T {
        if (mParams == null) {
            mParams = LinkedHashMap()
        }
//        mParams!![key] = value
        mParams?.let {
            it[key] = value
        }
        return this as T
    }

    /**
     * append params to url
     *
     * @param url    接口路径
     * @param params 参数
     * @return 拼接后的url
     */
    fun appendParams(
        url: String,
        params: Map<String, String>?
    ): String {
        var sb = StringBuilder()
        sb.append("$url?")
        if (params != null && params.isNotEmpty()) {
            for (key in params.keys) {
                sb.append(key).append("=").append(params[key]).append("&")
            }
        }
        sb = sb.deleteCharAt(sb.length - 1)
        return sb.toString()
    }

    /**
     * append params to form builder
     *
     * @param builder 构建
     * @param params  参数
     */
    fun appendParams(
        builder: FormBody.Builder,
        params: Map<String, String>?
    ) {
        if (params != null && params.isNotEmpty()) {
            for (key in params.keys) {
                params[key]?.let { builder.add(key, it) }
            }
        }
    }

    /**
     * append params into MultipartBody builder
     *
     * @param builder 构建
     * @param params  参数(表单提交)
     */
    fun appendParams(
        builder: MultipartBody.Builder,
        params: Map<String, String>?
    ) {
        if (params != null && params.isNotEmpty()) {
            for (key in params.keys) {
                params[key]?.let { builder.addFormDataPart(key, it) }
            }
        }
    }

}