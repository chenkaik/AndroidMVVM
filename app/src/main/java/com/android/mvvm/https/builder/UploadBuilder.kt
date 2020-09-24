package com.android.mvvm.https.builder

import android.text.TextUtils
import com.android.lib.Logger.e
import com.android.mvvm.https.NetWorkManager
import com.android.mvvm.https.callback.OkHttpCallback
import com.android.mvvm.https.network.OkHttpRequestBuilderHasParam
import com.android.mvvm.https.response.OkHttpResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.net.URLConnection
import java.util.*

/**
 * date: 2019/2/13
 * desc: 上传文件
 */
class UploadBuilder(request: NetWorkManager) :
    OkHttpRequestBuilderHasParam<UploadBuilder>(request) {

    companion object {
        private const val TAG = "UploadBuilder"
    }

    private var mFiles: MutableMap<String, File>? = null
    private var filePath: MutableList<String>? = null

    /**
     * add upload files
     *
     * @param files files
     * @return this
     */
    fun files(files: MutableMap<String, File>): UploadBuilder {
        mFiles = files
        return this
    }

    /**
     * add one upload file
     *
     * @param key  file key
     * @param file file
     * @return this
     */
    fun addFile(key: String, file: File): UploadBuilder {
        if (mFiles == null) {
            mFiles = LinkedHashMap()
        }
//        mFiles!![key] = file
        mFiles?.let {
            it[key] = file
        }
        return this
    }

    /**
     * add upload files
     *
     * @param filePath files
     * @return this
     */
    fun files(filePath: MutableList<String>): UploadBuilder {
        this.filePath = filePath
        return this
    }

    /**
     * add one upload file
     *
     * @param file filePath
     * @return this
     */
    fun addFile(file: String): UploadBuilder {
        if (filePath == null) {
            filePath = ArrayList()
        }
        filePath?.add(file)
        return this
    }

    override fun enqueue(
        requestCode: Int,
        okHttpResponse: OkHttpResponse
    ) {
        try {
            // 参数为false时 抛出 IllegalArgumentException
            require(!TextUtils.isEmpty(mUrl)) { "url can not be null !" }
            //            if (mParams != null && mParams.size() > 0) {
//                mUrl = appendParams(mUrl, mParams); // 拼接参数(url后面)
//            }
            val builder = Request.Builder().url(mUrl)
            appendHeaders(builder, mHeaders) // 根据需要添加head
            if (mTag != null) {
                builder.tag(mTag)
            }
            val multipartBuilder =
                MultipartBody.Builder().setType(MultipartBody.FORM) // 表单类型
            appendParams(multipartBuilder, mParams) // from参数
            // 拼装需要上传的文件参数,两种拼接上传文件的参数(二选其一)
            appendMapFiles(multipartBuilder, mFiles) // file文件
            appendListFiles(multipartBuilder, filePath) // 文件的路径
            builder.post(multipartBuilder.build())
            val uploadRequest = builder.build()
            mNetManager.okHttpClient
                .newCall(uploadRequest)
                .enqueue(OkHttpCallback(requestCode, okHttpResponse))
        } catch (e: Exception) {
            e(
                TAG,
                "Post Upload File enqueue error:" + e.message
            )
            okHttpResponse.onDataFailure(requestCode, 0, e.message, false)
        }
    }

    /**
     * append files into MultipartBody builder 根据需要使用
     *
     * @param builder 构建
     * @param files   map数据源
     */
    private fun appendMapFiles(
        builder: MultipartBody.Builder,
        files: Map<String, File>?
    ) {
        if (files != null && files.isNotEmpty()) {
            for (key in files.keys) {
                val file = files[key]
                if (file != null) {
                    // File转RequestBody
                    val mediaType = "text/x-markdown; charset=utf-8".toMediaTypeOrNull()
                    val body: RequestBody = file.asRequestBody(mediaType)
                    builder.addFormDataPart(key, file.name, body)
                }
            }
        }
    }

    /**
     * append files into MultipartBody builder 根据需要使用
     *
     * @param builder  构建
     * @param filePath list数据源
     */
    private fun appendListFiles(
        builder: MultipartBody.Builder,
        filePath: List<String>?
    ) {
        if (filePath != null && filePath.isNotEmpty()) {
            for (i in filePath.indices) {
                val file = File(filePath[i])
                // MediaType.parse() 里面是上传的文件类型。
//                RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
//                RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                // File转RequestBody
                val mediaType =
                    "text/x-markdown; charset=utf-8".toMediaTypeOrNull()
                val body: RequestBody = file.asRequestBody(mediaType)
                builder.addFormDataPart("file", file.name, body)
            }
        }
    }

    /**
     * 获取mime type  image/png&image/jpg...
     *
     * @param path 文件路径
     * @return 文件类型
     */
    private fun guessMimeType(path: String): String {
        val fileNameMap = URLConnection.getFileNameMap()
        var contentTypeFor = fileNameMap.getContentTypeFor(path)
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream"
        }
        return contentTypeFor
    }

}