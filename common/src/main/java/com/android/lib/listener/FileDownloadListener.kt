package com.android.lib.listener

import java.io.File

/**
 * date: 2019/1/30
 * desc: 文件下载的接口监听回调
 */
interface FileDownloadListener {
    /**
     * 获取断点已下载文件的大小
     *
     * @param downloadLength 已下载文件的大小
     * @param contentLength  文件的大小
     */
    fun onProgress(downloadLength: Long, contentLength: Long)

    /**
     * 当前下载进度
     *
     * @param progress      已下载的进度
     * @param contentLength 文件的大小
     * @param percentage    下载的百分比
     */
    fun onProgressUpdate(
        progress: Int,
        contentLength: Long,
        percentage: Int
    )

    /**
     * 下载成功
     *
     * @param file 文件
     */
    fun onSuccess(file: File?)

    /**
     * 下载失败
     */
    fun onFailed()
}