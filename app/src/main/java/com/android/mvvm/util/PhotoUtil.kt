package com.android.mvvm.util

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * date: 2019/1/30
 * desc: 选择系统相册图片及拍照的工具类
 */
object PhotoUtil {

    private var mPhotoPath: String? = null
    const val CAMERA_CODE_PHOTO = 10000 // 相机请求码
    const val ALBUM_CODE_SELECT_PICTURE = 10001 // 相册请求码

    /**
     * 打开系统相册
     *
     * @param activity act
     */
    fun openAlbum(activity: AppCompatActivity) {
        val intent: Intent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
            } else {
                intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            }
        } else {
            intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
        }
        activity.startActivityForResult(intent, ALBUM_CODE_SELECT_PICTURE)
    }

    /**
     * 打开系统相机
     *
     * @param activity act
     */
    @SuppressLint("SimpleDateFormat")
    fun openCamera(activity: AppCompatActivity) {
        val date =
            SimpleDateFormat("yyyy_MMdd_hhmmss").format(Date())
        mPhotoPath = createImagePath(activity, date)
        if (TextUtils.isEmpty(mPhotoPath)) {
            return
        }
        val file = File(mPhotoPath)
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { // Android7.0以上URI
// 添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            // 通过FileProvider创建一个content类型的Uri activity.getPackageName()+".fileProvider"
            val uri = FileProvider.getUriForFile(
                activity,
                activity.packageName + ".fileProvider",
                file
            )
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
        }
        try {
            activity.startActivityForResult(intent, CAMERA_CODE_PHOTO)
        } catch (anf: Exception) {
//            Toast.makeText(activity, "摄像头未准备好", Toast.LENGTH_SHORT).show()
            //ActivityNotFoundException
            anf.printStackTrace()
        }
    }

    /**
     * 创建图片的存储路径
     *
     * @param activity  act
     * @param imageName 图片名称
     * @return 图片路径
     */
    private fun createImagePath(
        activity: AppCompatActivity,
        imageName: String
    ): String {
        val cacheDir = activity.externalCacheDir
        return if (cacheDir != null) {
            val dir = cacheDir.path
            val destDir = File(dir)
            if (!destDir.exists()) {
                destDir.mkdirs()
            }
            var file: File? = null
            if (!TextUtils.isEmpty(imageName)) {
                file = File(dir, "$imageName.jpg")
            }
            if (file == null) "" else file.absolutePath
        } else {
            ""
        }
    }

    /**
     * 获取拍照后的图片路径
     *
     * @return 图片路径
     */
    val path: String
        get() = if (mPhotoPath == null) "" else mPhotoPath!!
}