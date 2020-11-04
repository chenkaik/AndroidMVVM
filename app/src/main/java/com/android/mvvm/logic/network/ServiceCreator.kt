package com.android.mvvm.logic.network

import com.android.mvvm.https.config.HttpConfig
import com.android.mvvm.https.network.OkHttpInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * date: 2020/11/4
 * desc: 创建retrofit
 */
object ServiceCreator {

    private const val BASE_URL = "https://www.shjacf.com/server/"

    private lateinit var okHttpClient: OkHttpClient

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(buildOkHttpClient())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    // 泛型实化
    inline fun <reified T> create(): T = create(T::class.java)

    private fun buildOkHttpClient(): OkHttpClient {
        okHttpClient =
            OkHttpClient.Builder()
                .readTimeout(HttpConfig.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(HttpConfig.WRITE_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(
                    HttpConfig.CONNECT_TIMEOUT,
                    TimeUnit.SECONDS
                )
                .addInterceptor(OkHttpInterceptor())
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()
        return okHttpClient
    }

    fun getOkHttpClient() = okHttpClient

}