package com.android.lib.util

import android.content.Context
import android.os.Environment
import java.io.File
import java.math.BigDecimal

/**
 * date: 2019/1/30
 * desc: 清除相关缓存
 */
object DataCleanManager {
    /**
     * * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * *
     *
     * @param context 上下文
     */
    fun cleanInternalCache(context: Context) {
        deleteFilesByDirectory(context.cacheDir)
    }

    /**
     * * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * *
     *
     * @param context 上下文
     */
    fun cleanDatabase(context: Context) {
        deleteFilesByDirectory(
            File(
                "/data/data/"
                        + context.packageName + "/databases"
            )
        )
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) *
     *
     * @param context 上下文
     */
    fun cleanSharedPreference(context: Context) {
        deleteFilesByDirectory(
            File(
                "/data/data/"
                        + context.packageName + "/shared_prefs"
            )
        )
    }

    /**
     * * 按名字清除本应用数据库 * *
     *
     * @param context 上下文
     * @param dbName  数据库名
     */
    fun cleanDatabaseByName(context: Context, dbName: String) {
        context.deleteDatabase(dbName)
    }

    /**
     * * 清除/data/data/com.xxx.xxx/files下的内容 * *
     *
     * @param context 上下文
     */
    fun cleanFiles(context: Context) {
        deleteFilesByDirectory(context.filesDir)
    }

    /**
     * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
     *
     * @param context 上下文
     */
    fun cleanExternalCache(context: Context) {
        if (Environment.getExternalStorageState() ==
            Environment.MEDIA_MOUNTED
        ) {
            context.externalCacheDir?.let { deleteFilesByDirectory(it) }
        }
    }

    /**
     * * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * *
     *
     * @param filePath 文件目录
     */
    fun cleanCustomCache(filePath: String) {
        deleteFilesByDirectory(File(filePath))
    }

    /**
     * * 清除本应用所有的数据
     *
     * @param context  上下文
     * @param filepath 路径
     */
    fun cleanApplicationData(
        context: Context,
        vararg filepath: String
    ) {
        cleanInternalCache(context)
        cleanExternalCache(context)
        cleanDatabase(context)
        cleanSharedPreference(context)
        cleanFiles(context)
//        if (filepath == null) {
//            return
//        }
        for (filePath in filepath) {
            cleanCustomCache(filePath)
        }
    }

    /**
     * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * *
     *
     * @param directory 文件夹路径
     */
    private fun deleteFilesByDirectory(directory: File) {
        if (directory.exists() && directory.isDirectory) {
            val file = directory.listFiles()
            if (file != null) {
                for (item in file) {
                    item.delete()
                }
            }
        }
    }

    // 获取文件
//Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
//Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    @Throws(Exception::class)
    fun getFolderSize(file: File): Long {
        var size: Long = 0
        try {
            val fileList = file.listFiles()
            if (fileList != null) {
                for (aFileList in fileList) { // 如果下面还有文件
                    size = if (aFileList.isDirectory) {
                        size + getFolderSize(aFileList)
                    } else {
                        size + aFileList.length()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return size
    }

    /**
     * 删除文件夹
     */
    private fun deleteDir(dir: File): Boolean {
        if (dir.isDirectory) {
            val children = dir.list() ?: return false
            for (i in children.indices) {
                val success =
                    deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
        }
        return dir.delete()
    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param filePath       路径
     * @param deleteThisPath 是否删除
     * getExternalCacheDir();
     * getExternalFilesDir("res");
     */
    fun deleteFolderFile(filePath: String, deleteThisPath: Boolean) {
        try {
            val file = File(filePath)
            if (file.isDirectory) { // 如果下面还有文件
                val files = file.listFiles() ?: return
                for (file1 in files) {
                    deleteFolderFile(file1.absolutePath, true)
                }
            }
            if (deleteThisPath) {
                if (!file.isDirectory) { // 如果是文件，删除
                    file.delete()
                } else { // 目录
                    val f = file.listFiles() ?: return
                    if (f.isEmpty()) { // 目录下没有文件或者目录，删除
                        file.delete()
                    }
                }
            }
        } catch (e: Exception) { // TODO Auto-generated catch block
            e.printStackTrace()
        }
    }

    /**
     * 格式化单位
     *
     * @param size 数据大小
     * @return 格式化后的数据
     */
    fun getFormatSize(size: Double): String {
        val kiloByte = size / 1024
        if (kiloByte < 1) {
            return size.toString() + "KB" // Byte
        }
        val megaByte = kiloByte / 1024
        if (megaByte < 1) {
            val result1 =
                BigDecimal(kiloByte.toString())
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB"
        }
        val gigaByte = megaByte / 1024
        if (gigaByte < 1) {
            val result2 =
                BigDecimal(megaByte.toString())
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB"
        }
        val teraBytes = gigaByte / 1024
        if (teraBytes < 1) {
            val result3 =
                BigDecimal(gigaByte.toString())
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB"
        }
        val result4 = BigDecimal(teraBytes)
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
    }

    /**
     * @param file 路径
     * @return 缓存的大小
     * @throws Exception e
     */
    @Throws(Exception::class)
    fun getCacheSize(file: File): String {
        return getFormatSize(getFolderSize(file).toDouble())
    }

}