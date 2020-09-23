package com.android.mvvm.https.builder

import android.text.TextUtils
import com.android.mvvm.https.NetWorkManager
import com.android.mvvm.https.body.ResponseProgressBody
import com.android.mvvm.https.callback.DownloadCallback
import com.android.mvvm.https.network.DownloadResponseHandler
import okhttp3.Call
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import java.io.File
import java.util.*

/**
 * date: 2019/2/18
 * desc: download builder
 */
class DownloadBuilder(private val request: NetWorkManager) {

    private var mUrl = ""
    private var mTag: Any? = null
    private var mHeaders: MutableMap<String, String>? = null
    private var mFileDir = "" // 文件dir
    private var mFileName = "" // 文件名
    private var mFilePath = "" // 文件路径 （如果设置该字段则上面2个就不需要）
    private var mCompleteBytes = 0L // 已经完成的字节数 用于断点续传

    fun url(url: String): DownloadBuilder {
        mUrl = url
        return this
    }

    /**
     * set file storage dir
     *
     * @param fileDir file directory
     * @return this
     */
    fun fileDir(fileDir: String): DownloadBuilder {
        mFileDir = fileDir
        return this
    }

    /**
     * set file storage name
     *
     * @param fileName file name
     * @return this
     */
    fun fileName(fileName: String): DownloadBuilder {
        mFileName = fileName
        return this
    }

    /**
     * set file path
     *
     * @param filePath file path
     * @return this
     */
    fun filePath(filePath: String): DownloadBuilder {
        mFilePath = filePath
        return this
    }

    /**
     * set tag
     *
     * @param tag tag
     * @return this
     */
    fun tag(tag: Any): DownloadBuilder {
        mTag = tag
        return this
    }

    /**
     * set headers
     *
     * @param headers headers
     * @return this
     */
    fun headers(headers: MutableMap<String, String>): DownloadBuilder {
        mHeaders = headers
        return this
    }

    /**
     * set one header
     *
     * @param key header key
     * @param value header value
     * @return this
     */
    fun addHeader(key: String, value: String): DownloadBuilder {
        if (mHeaders == null) {
            mHeaders = LinkedHashMap()
        }
//        mHeaders!![key] = value
        mHeaders?.let {
            it[key] = value
        }
        return this
    }

    /**
     * set completed bytes (BreakPoints)
     *
     * @param completeBytes 已经完成的字节数
     * @return this
     */
    fun setCompleteBytes(completeBytes: Long): DownloadBuilder {
        if (completeBytes > 0L) {
            mCompleteBytes = completeBytes
            addHeader("RANGE", "bytes=$completeBytes-") //添加断点续传header
        }
        return this
    }

    /**
     * 异步执行
     *
     * @param downloadResponseHandler 下载回调
     */
    fun enqueue(downloadResponseHandler: DownloadResponseHandler): Call? {
        return try {
            // 参数为false时 抛出 IllegalArgumentException
            require(!TextUtils.isEmpty(mUrl)) { "url can not be null !" }
            if (mFilePath.isEmpty()) {
                mFilePath = if (mFileDir.isEmpty() || mFileName.isEmpty()) {
                    throw IllegalArgumentException("FilePath can not be null !")
                } else {
                    mFileDir + mFileName
                }
            }
            checkFilePath(mFilePath, mCompleteBytes)
            val builder = Request.Builder().url(mUrl)
            appendHeaders(builder, mHeaders)
            if (mTag != null) {
                builder.tag(mTag)
            }
            val downloadRequest = builder.build()
            // 设置拦截器
            val call = request.okHttpClient.newBuilder()
                .addNetworkInterceptor(Interceptor { chain: Interceptor.Chain ->
                    val originalResponse = chain.proceed(chain.request())
                    originalResponse.newBuilder() //                                .addHeader("token","eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyTmFtZSI6IjE1Njg2MjIxODEzIiwiZXhwIjoxNTg3MjcyMDM0LCJ1c2VySWQiOjEwfQ.DUjajZqGDmWnWiUaKFRWpISzf0zf0qN6hfE8uCDAXlk")
                        .body(
                            ResponseProgressBody(
                                originalResponse.body!!,
                                downloadResponseHandler
                            )
                        )
                        .build()
                })
                .build()
                .newCall(downloadRequest)
            call.enqueue(DownloadCallback(downloadResponseHandler, mFilePath, mCompleteBytes))
            call
        } catch (e: Exception) { //            LogUtils.e("Download enqueue error:" + e.getMessage());
            downloadResponseHandler.onFailure(e.message)
            null
        }
    }

    //检查filePath有效性
    @Throws(Exception::class)
    private fun checkFilePath(filePath: String, completeBytes: Long) {
        val file = File(filePath)
        if (file.exists()) {
            return
        }
        if (completeBytes > 0L) { //如果设置了断点续传 则必须文件存在
            throw Exception("断点续传文件" + filePath + "不存在！")
        }
        if (filePath.endsWith(File.separator)) {
            throw Exception("创建文件" + filePath + "失败，目标文件不能为目录！")
        }
        // 判断目标文件所在的目录是否存在
        if (!file.parentFile?.exists()!!) {
            if (!file.parentFile?.mkdirs()!!) {
                throw Exception("创建目标文件所在目录失败！")
            }
        }
    }

    /**
     * append headers into builder
     *
     * @param builder 构建
     * @param headers head值
     */
    private fun appendHeaders(
        builder: Request.Builder,
        headers: Map<String, String>?
    ) {
        val headerBuilder = Headers.Builder()
        if (headers == null || headers.isEmpty()) {
            return
        }
        for (key in headers.keys) {
            headerBuilder.add(key, headers[key] ?: "")
        }
        builder.headers(headerBuilder.build())
    }

}