package com.android.mvvm.https.callback

import android.os.Handler
import android.os.Looper
import com.android.mvvm.https.network.DownloadResponseHandler
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.RandomAccessFile

/**
 * date: 2019/2/18
 * desc: 下载进度回调
 */
class DownloadCallback(
    private val mDownloadResponseHandler: DownloadResponseHandler?,
    private val mFilePath: String,
    private var mCompleteBytes: Long
) : Callback {

    companion object {
        private const val TAG = "DownloadCallback"
        private val mHandler = Handler(Looper.getMainLooper())
    }

    override fun onFailure(call: Call, e: IOException) {
        e.message
        //        LogUtils.e("onFailure", e);
        mHandler.post {
            mDownloadResponseHandler?.onFailure(e.toString())
        }
    }

    @Throws(IOException::class)
    override fun onResponse(call: Call, response: Response) {
        val body = response.body
        body.use { body ->
            if (response.isSuccessful) { //开始
                mHandler.post {
                    if (mDownloadResponseHandler != null && body != null) {
                        mDownloadResponseHandler.onStart(body.contentLength())
                    }
                }
                try {
                    if (response.header("Content-Range") == null || response.header("Content-Range")!!.length == 0) { //返回的没有Content-Range 不支持断点下载 需要重新下载
                        mCompleteBytes = 0L
                    }
                    saveFile(response, mFilePath, mCompleteBytes)
                    val file = File(mFilePath)
                    mHandler.post {
                        mDownloadResponseHandler?.onFinish(file)
                    }
                } catch (e: Exception) {
                    if (call.isCanceled()) { //判断是主动取消还是别动出错
                        mHandler.post {
                            mDownloadResponseHandler?.onCancel()
                        }
                    } else { //                        LogUtils.e("onResponse saveFile fail", e);
                        mHandler.post {
                            mDownloadResponseHandler?.onFailure("onResponse saveFile fail.$e")
                        }
                    }
                }
            } else { //                LogUtils.e("onResponse fail status=" + response.code());
                mHandler.post {
                    mDownloadResponseHandler?.onFailure("fail status=" + response.code)
                }
            }
        }
    }

    // 保存文件
    @Throws(Exception::class)
    private fun saveFile(
        response: Response,
        filePath: String,
        completeBytes: Long
    ) {
        var `is`: InputStream? = null
        val buf = ByteArray(4 * 1024) //每次读取4kb
        var len: Int
        var file: RandomAccessFile? = null
        try {
            `is` = response.body!!.byteStream()
            file = RandomAccessFile(filePath, "rwd")
            if (completeBytes > 0L) {
                file.seek(completeBytes)
            }
            var complete_len: Long = 0
            val total_len = response.body!!.contentLength()
            while (`is`.read(buf).also { len = it } != -1) {
                file.write(buf, 0, len)
                complete_len += len.toLong()
                //已经下载完成写入文件的进度
                val final_complete_len = complete_len
                mHandler.post {
                    mDownloadResponseHandler?.onProgress(final_complete_len, total_len)
                }
            }
        } finally {
            try {
                `is`?.close()
                file?.close()
            } catch (e: IOException) {
            }
            //            try {
//                if (file != null) file.close();
//            } catch (IOException e) {
//            }
        }
    }

}