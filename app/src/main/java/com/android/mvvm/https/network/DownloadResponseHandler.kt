package com.android.mvvm.https.network

import java.io.File

/**
 * date: 2019/2/18
 * desc: 下载文件回调
 */
abstract class DownloadResponseHandler {
    fun onStart(totalBytes: Long) {}
    fun onCancel() {}
    abstract fun onFinish(downloadFile: File?)
    abstract fun onProgress(currentBytes: Long, totalBytes: Long)
    abstract fun onFailure(error_msg: String?)
}