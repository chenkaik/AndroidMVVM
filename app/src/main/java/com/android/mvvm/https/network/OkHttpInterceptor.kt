package com.android.mvvm.https.network

import com.android.lib.Logger.e
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * date: 2019/1/30
 * desc: Request拦截器，添加header--token
 */
class OkHttpInterceptor : Interceptor {

    companion object {
        private const val TAG = "OkHttpInterceptor"
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response { //        Request original = chain.request();
//        // 获取、修改请求头
//        Request.Builder builder = original.newBuilder();
//        builder.addHeader("Authorization", "Bearer " + UserConfig.getInstance().getToken());
//        Request request = builder.build();
//
//        Response response = chain.proceed(request);
//        ResponseBody body = response.body();
//        if (body != null && body.contentLength() == 0) {
//            //Log.e(TAG, "length = 0");
//        }
        val original = chain.request()
        // 获取、修改请求头
        val headers = original.headers
        val newHeader =
            headers.newBuilder() //                .add("Authorization", "Bearer " + UserConfig.getInstance().getToken())
                .add(
                    "token",
                    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6IjE1Njg2MjIxODEzIiwiZXhwIjoxNTg3MjcyMDM0LCJ1c2VySWQiOjEwfQ.DUjajZqGDmWnWiUaKFRWpISzf0zf0qN6hfE8uCDAXlk"
                )
                .build()
        val builder = original.newBuilder()
            .headers(newHeader)
        val request = builder.build()
        val response = chain.proceed(request)
        val body = response.body
        if (body != null && body.contentLength() == 0L) {
            e(TAG, "length = 0")
        }
        return response
    }

}