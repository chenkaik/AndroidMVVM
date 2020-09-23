package com.android.mvvm.https.body

import com.android.mvvm.https.network.DownloadResponseHandler
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import java.io.IOException

/**
 * date: 2019/2/18
 * desc: 重写responseBody 设置下载进度监听
 */
class ResponseProgressBody(
    private val mResponseBody: ResponseBody,
    downloadResponseHandler: DownloadResponseHandler
) : ResponseBody() {

    private val mDownloadResponseHandler: DownloadResponseHandler? = downloadResponseHandler
    private var mBufferedSource: BufferedSource? = null
    override fun contentType(): MediaType? {
        return mResponseBody.contentType()
    }

    override fun contentLength(): Long {
        return mResponseBody.contentLength()
    }

    override fun source(): BufferedSource {
        if (mBufferedSource == null) {
            mBufferedSource = source(mResponseBody.source()).buffer()
        }
        return mBufferedSource!!
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            var totalBytesRead: Long = 0
            @Throws(IOException::class)
            override fun read(
                sink: Buffer,
                byteCount: Long
            ): Long { // 这个的进度应该是读取response每次内容的进度，在写文件进度之前 所以暂时以写完文件的进度为准
                val bytesRead = super.read(sink, byteCount)
                totalBytesRead += if (bytesRead != -1L) bytesRead else 0
                mDownloadResponseHandler?.onProgress(
                    totalBytesRead,
                    mResponseBody.contentLength()
                )
                return bytesRead
            }
        }
    }

}