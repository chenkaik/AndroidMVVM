package com.android.mvvm.jetpack.http

import com.android.mvvm.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * date: 2021/5/29
 * desc: Retrofit动态代理对象获取封装
 */
object NetServiceCreator {

    private const val CONNECT_TIME_OUT = 15L

    private const val READ_TIME_OUT = 20L

    /**
     * LazyThreadSafetyMode.NONE不会对任何访问和初始化上锁，也就是说完全放任，官方是这么描述的
    如果你确定初始化将总是发生在单个线程，那么你可以使用LazyThreadSafetyMode.NONE 模式，
    它不会有任何线程安全的保证以及相关的开销。
     */
    private val BODY by lazy(mode = LazyThreadSafetyMode.NONE) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    private val NONE by lazy(mode = LazyThreadSafetyMode.NONE) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    /**
     * 首先是默认的SYNCHRONIZED，上锁为了保证只有一条线程可去初始化lazy属性。
     * 也就是说同时多线程进行访问该延迟属性时，一旦没有初始化好，其他线程将无法访问。
     * LazyThreadSafetyMode.PUBLICATION
     * 对于还没有被初始化的lazy对象，初始化的方法可以被不同的线程调用很多次，直到有一个线程初始化先完成，那么其他的线程都将使用这个初始化完成的值。
     */
    private val okHttpClient by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)         // 连接超时
            .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)               // 读取超时
            .addInterceptor(if (BuildConfig.DEBUG) BODY else NONE)      // 请求日志拦截器
//            .addInterceptor(ChuckInterceptor(BaseApplication.context))  // 请求日志拦截器(UI)
            .retryOnConnectionFailure(true)       // 失败重连
            .build()
    }

    private val retrofit by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASEURL)
            .addConverterFactory(GsonConverterFactory.create())     // Gson转换器
            .client(okHttpClient)
            .build()
    }


    /**
     * 获取service动态代理对象
     * @param serviceClass 接口Class对象
     */
    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    /**
     * 获取service动态代理对象
     * 范型实化
     */
    inline fun <reified T> create(): T = create(T::class.java)

}