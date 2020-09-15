package com.android.lib.util

import android.os.AsyncTask
import com.android.lib.Logger
import com.android.lib.listener.FileDownloadListener
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.io.RandomAccessFile

/**
 * date: 2019/1/30
 * desc: 文件下载
 */
class FileDownloadTask(// 下载监听
    private val mListener: FileDownloadListener, // 下载文件存放的目录
    private val mDownloadDir: File
) :
    AsyncTask<String?, Int?, Int>() {
    private var mLastProgress = 0
    private lateinit var mIs: InputStream
    private lateinit var mSaveFile: RandomAccessFile
    private lateinit var mFile: File
    private var mContentLength: Long = 0 // 文件大小


    companion object {
        private const val SUCCESS = 0
        private const val FAILED = 1
    }

    override fun doInBackground(vararg params: String?): Int {
        try {
            val downloadLength: Long = 0 // 记录已下载文件的长度（配合断点下载使用）
            val downloadUrl = params[0]
//            val fileName = downloadUrl!!.substring(downloadUrl.lastIndexOf("/"))
//            val directory: String = Environment.getExternalStorageDirectory().getPath()
//            mFile = File(directory + fileName)
            mFile = File(mDownloadDir.path + "/version_Update.apk")
            if (mFile.exists()) {
                mFile.delete()
//                downloadLength = mFile.length() // 断点下载使用
            }
            mContentLength = downloadUrl?.let { getFileLength(it) }!! // 得到下载文件大小
            if (mContentLength == 0L) { // 当下载文件的长度为0时处理
                Logger.e("FileDownloadTask", "1")
                return FAILED
            }
//            else if (mContentLength == downloadLength) { // 已下载的字节和文件的总字节相等，说明已经下载完成（断点下载使用）
//                return SUCCESS
//            }
            else {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .addHeader("RANGE", "bytes=$downloadLength-") // 指定从哪个字节开始下载
                    .url(downloadUrl)
                    .build()
                val response = client.newCall(request).execute()
                val responseBody = response.body();
                return if (responseBody != null) {
                    mIs = responseBody.byteStream()
                    mSaveFile = RandomAccessFile(mFile, "rw")
                    // saveFile.seek(downloadLength); // 跳过已下载的字节（断点下载使用）
                    val b = ByteArray(1024)
                    var total = 0
                    var len: Int
                    while (mIs.read(b).also { len = it } != -1) { // also函数返回的是传入对象的本身
                        total += len
                        mSaveFile.write(b, 0, len)
                        val progress =
                            ((total + downloadLength) * 100 / mContentLength).toInt() // 计算已下载的百分比
                        publishProgress(mFile.length().toInt(), progress) // 已下载文件的大小
                    }
                    responseBody.close()
                    SUCCESS
                } else {
                    Logger.e("FileDownloadTask", "2")
                    FAILED
                }
            }
        } catch (e: Exception) {
            e.printStackTrace();
        } finally {
            try {
//                if (mIs != null) {
//                    mIs!!.close()
//                }
                mIs.close()
//                if (mSaveFile != null) {
//                    mSaveFile!!.close()
//                }
                mSaveFile.close()
            } catch (e: Exception) {
                e.printStackTrace();
            }
        }
        Logger.e("FileDownloadTask", "3")
        return FAILED
    }

    override fun onProgressUpdate(vararg values: Int?) { // 后台任务中调用publishProgress(Progress...)后，此方法很快就会被回调
        val progress = values[0]
        val percentage = values[1]
        if (progress != null && percentage != null) {
            if (progress > mLastProgress && percentage > 0) {
                mListener.onProgressUpdate(progress, mContentLength, percentage)
                mLastProgress = progress
            }
        }
    }

    override fun onPostExecute(status: Int) {
        when (status) {
            SUCCESS -> if (mFile.isFile) {
                mListener.onSuccess(mFile)
            } else {
                Logger.e("FileDownloadTask", "4")
                mListener.onFailed()
            }
            FAILED -> mListener.onFailed()
            else -> {
                mListener.onFailed()
            }
        }
    }

    /**
     * 获取待下载文件的大小
     *
     * @param downloadUrl 下载地址
     * @return 待下载文件的大小
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun getFileLength(downloadUrl: String): Long {
        val client = OkHttpClient();
        val request = Request.Builder()
            .url(downloadUrl)
            .build();
        val response = client.newCall(request).execute();
        if (response.isSuccessful) {
            val responseBody = response.body();
            return if (responseBody != null) {
                val contentLength = responseBody.contentLength();
                response.close();
                contentLength;
            } else {
                0
            }

        }
        return 0;
//        try {
//            val url = URL(downloadUrl)
//            val httpURLConnection =
//                url.openConnection() as HttpURLConnection
//            //            httpURLConnection.setRequestProperty("Accept-Encoding", "identity");
//            httpURLConnection.connect()
//            //            int length = httpURLConnection.getContentLength();
//            val b = httpURLConnection.getHeaderField("Accept-Length")
//            return if (!TextUtils.isEmpty(b)) {
//                b.toLong()
//            } else {
//                0
//            }
//        } catch (e: Exception) { //            e.printStackTrace();
//        }
//        return 0
    }

}