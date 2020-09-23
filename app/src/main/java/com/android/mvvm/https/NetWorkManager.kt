package com.android.mvvm.https

import android.os.Handler
import android.os.Looper
import com.android.lib.Logger.e
import com.android.lib.util.NetErrStringUtil
import com.android.mvvm.https.builder.*
import com.android.mvvm.https.config.HttpConfig
import com.android.mvvm.https.network.OkHttpInterceptor
import com.android.mvvm.https.response.BaseResponse
import com.android.mvvm.https.response.NetworkResponse


import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

/**
 * date: 2019/1/30
 * desc: retrofit调用接口，支持网络请求缓存，自动添加和删除缓存，也可以手动cancel请求
 */
class NetWorkManager private constructor() {
    private val mRequestMap: MutableMap<String, MutableMap<Int, Call<*>>> =
        ConcurrentHashMap()
    private lateinit var mRetrofit: Retrofit
    private lateinit var mOkHttpClient: OkHttpClient

    private object NetWorkHolder {
        val sInstance = NetWorkManager()
    }

    companion object {
        private val TA = NetWorkManager::class.java.simpleName
        var mHandler = Handler(Looper.getMainLooper())
        val instance: NetWorkManager
            get() = NetWorkHolder.sInstance
    }

    /**
     * 初始化Retrofit
     *
     * @param baseURL 接口地址
     */
    fun init(baseURL: String) {
        synchronized(this@NetWorkManager) {
            mOkHttpClient =
                OkHttpClient.Builder() //                    .cache(new Cache(new File(context.getExternalCacheDir(), "http_cache"), 1024 * 1024 * 100))
                    .readTimeout(HttpConfig.READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(HttpConfig.WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .connectTimeout(
                        HttpConfig.CONNECT_TIMEOUT,
                        TimeUnit.SECONDS
                    ) //                    .cookieJar(new CookieManager())
//                    .authenticator(new AuthenticatorManager())
                    .addInterceptor(OkHttpInterceptor())
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()
            mRetrofit = Retrofit.Builder()
                .baseUrl(baseURL) // 接口地址须以"/"结尾
                .addConverterFactory(GsonConverterFactory.create()) // 支持Gson转换器
                .addConverterFactory(ScalarsConverterFactory.create()) // 支持返回值为String
                .client(mOkHttpClient)
                .build()
        }
    }

    fun <T> create(tClass: Class<T>): T {
        return mRetrofit.create(tClass)
    }

//    fun clearCookie() {
//        (mOkHttpClient!!.cookieJar() as CookieManager).clearCookie()
//    }

    val okHttpClient: OkHttpClient
        get() = mOkHttpClient

    /**
     * 异步请求
     *
     * @param TAG              区分不同页面的请求
     * @param requestCode      用于区分相同页面的不同请求
     * @param requestCall      动态代理 泛型为返回实体 必须继承BaseResponseEntity
     * @param responseListener 接口回调 泛型为返回实体 必须继承BaseResponseEntity（可根据需要进行修改）
     * @param isShow           是否显示加载框
     * @param <T>              泛型类型
    </T> */
    fun <T : BaseResponse> asyncNetWork(
        TAG: String?,
        requestCode: Int,
        requestCall: Call<T>,
        responseListener: NetworkResponse<T>?,
        isShow: Boolean
    ) {
        if (responseListener == null) {
            return
        }
        if (isShow) {
            responseListener.showLoading("")
        }
        val call: Call<T> = if (requestCall.isExecuted) {
            requestCall.clone()
        } else {
            requestCall
        }
        addCall(TAG, requestCode, call)
        call.enqueue(object : Callback<T> {
            override fun onResponse(
                call: Call<T>,
                response: Response<T>
            ) {
                e(TA, "响应码:" + response.code())
                if (isShow) {
                    responseListener.dismissLoading()
                }
                cancelCall(TAG, requestCode)
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result == null) {
                        responseListener.onDataError(requestCode, response.code(), "数据加载失败", false)
                        return
                    }
//                    if (result.SYS_HEAD != null && result.SYS_HEAD.RET != null) {
                        when (result.SYS_HEAD.RET.RET_CODE) {
                            HttpConfig.SUCCESS -> {
                                result.requestCode = requestCode // 区分接口请求
                                responseListener.onDataReady(result)
                            }
                            HttpConfig.TOKENERROR -> {
                                responseListener.onDataError(requestCode, response.code(), "登录失效", true)
                            }
                            else -> {
                                responseListener.onDataError(
                                    requestCode,
                                    response.code(),
                                    result.SYS_HEAD.RET.RET_CODE + " " + result.SYS_HEAD.RET.RET_MSG,
                                    false
                                )
                            }
                        }
//                    }
                } else {
                    responseListener.onDataError(
                        requestCode,
                        response.code(),
                        NetErrStringUtil.getErrString(response.code()),
                        false
                    )
                }
            }

            override fun onFailure(
                call: Call<T>,
                t: Throwable
            ) {
                if (isShow) {
                    responseListener.dismissLoading()
                }
                cancelCall(TAG, requestCode)
                responseListener.onDataError(
                    requestCode,
                    0,
                    NetErrStringUtil.getErrString(t),
                    false
                )
            }
        })
    }

    /**
     * 同步请求(使用同步请求需单独处理子线程问题)
     *
     * @param TAG              区分不同页面的请求
     * @param requestCode      用于区分相同页面的不同请求
     * @param requestCall      动态代理 泛型为返回实体 必须继承BaseResponseEntity
     * @param responseListener 接口回调 泛型为返回实体 必须继承BaseResponseEntity（可根据需要进行修改）
     * @param isShow           是否显示加载框
     * @param <T>              泛型类型
    </T> */
    fun <T : BaseResponse> syncNetWork(
        TAG: String?,
        requestCode: Int,
        requestCall: Call<T>,
        responseListener: NetworkResponse<T>?,
        isShow: Boolean
    ) {
        if (responseListener == null) {
            return
        }
        if (isShow) {
            responseListener.showLoading("")
        }
        val call: Call<T>
        try {
            call = if (requestCall.isExecuted) {
                requestCall.clone()
            } else {
                requestCall
            }
            val response = call.execute()
            addCall(TAG, requestCode, call)
            if (response.isSuccessful) {
                val result = response.body()
                if (result == null) {
                    responseListener.onDataError(requestCode, response.code(), "数据加载失败", false)
                    return
                }
                //                result.requestCode = requestCode;
//                result.serverTip = response.message();
//                result.responseCode = response.code();
                responseListener.onDataReady(result)
            } else {
                responseListener.onDataError(
                    requestCode,
                    response.code(),
                    NetErrStringUtil.getErrString(response.code()),
                    false
                )
            }
        } catch (e: IOException) {
            responseListener.onDataError(requestCode, 0, NetErrStringUtil.getErrString(e), false)
        } finally {
            if (isShow) {
                responseListener.dismissLoading()
            }
            cancelCall(TAG, requestCode)
        }
    }

    /**
     * 添加call到Map
     *
     * @param TAG  tag
     * @param call call
     */
    private fun addCall(TAG: String?, code: Int, call: Call<*>) {
        if (TAG == null) {
            return
        }
        if (mRequestMap[TAG] == null) {
            val map: MutableMap<Int, Call<*>> =
                ConcurrentHashMap()
            map[code] = call
            mRequestMap[TAG] = map
        } else {
            mRequestMap[TAG]!![code] = call
        }
    }

    /**
     * 取消整个tag请求，关闭页面时调用
     *
     * @param TAG
     */
    fun cancelTagCall(TAG: String?) {
        cancelCall(TAG, null)
    }

    /**
     * 取消某个call
     *
     * @param TAG  tag
     * @param code code
     */
    fun cancelCall(TAG: String?, code: Int?): Boolean {
        if (TAG == null) {
            return false
        }
        val map = mRequestMap[TAG] ?: return false
        if (code == null) { // 取消整个context请求
            val iterator: Iterator<*> = map.keys.iterator()
            while (iterator.hasNext()) {
                val key = iterator.next() as Int
                val call = map[key] ?: continue
                call.cancel()
            }
            mRequestMap.remove(TAG)
            return false
        } else { // 取消一个请求
            if (map.containsKey(code)) {
                val call = map[code]
                call?.cancel()
                map.remove(code)
            }
            if (map.isEmpty()) {
                mRequestMap.remove(TAG)
                return false
            }
        }
        return true
    }

//    /**
//     * 重置
//     */
//    fun release() {
//        mOkHttpClient = null
//        mRetrofit = null
//    }

    /**
     * okHttp get请求
     *
     * @return 构建get请求
     */
    fun get(): GetBuilder {
        return GetBuilder(this)
    }

    /**
     * okHttp post请求
     *
     * @return 构建post请求
     */
    fun post(isNotForm: Boolean): PostBuilder {
        return PostBuilder(this, isNotForm)
    }

    /**
     * okHttp patch请求
     *
     * @return 构建patch请求
     */
    fun patch(): PatchBuilder {
        return PatchBuilder(this)
    }

    /**
     * okHttp delete请求
     *
     * @return 构建delete请求
     */
    fun delete(): DeleteBuilder {
        return DeleteBuilder(this)
    }

    /**
     * okHttp put请求
     *
     * @return 构建put请求
     */
    fun put(): PutBuilder {
        return PutBuilder(this)
    }

    /**
     * okHttp post上传文件
     *
     * @return 构建上传文件请求
     */
    fun upload(): UploadBuilder {
        return UploadBuilder(this)
    }

    /**
     * okHttp 下载文件
     *
     * @return 构建下载文件请求
     */
    fun download(): DownloadBuilder {
        return DownloadBuilder(this)
    }

    /**
     * okHttp 根据tag取消请求
     *
     * @param tag tag
     */
    fun cancel(tag: Any) {
        val dispatcher = okHttpClient.dispatcher
        for (call in dispatcher.queuedCalls()) {
            if (tag == call.request().tag()) {
                call.cancel()
            }
        }
        for (call in dispatcher.runningCalls()) {
            if (tag == call.request().tag()) {
                call.cancel()
            }
        }
    }


}