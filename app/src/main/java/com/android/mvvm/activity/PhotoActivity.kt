package com.android.mvvm.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.android.lib.listener.PermissionListener
import com.android.lib.util.GetPathFromUri
import com.android.lib.util.Permission
import com.android.lib.util.kotlin.startActivity
import com.android.mvvm.R
import com.android.mvvm.util.PhotoUtil
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_photo.*
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class PhotoActivity : BaseActivity(), View.OnClickListener {

    companion object {
        private const val TAG = "PhotoActivity"
        fun actionStart(activity: FragmentActivity, isPutStack: Boolean) {
            startActivity<PhotoActivity>(activity, isPutStack)
        }
    }

    override fun getLayoutId() = R.layout.activity_photo

    override fun initView() {
        camera.setOnClickListener(this)
        album.setOnClickListener(this)
    }

    override fun initData() {
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.camera -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // 处理相机权限
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        PhotoUtil.openCamera(this@PhotoActivity)
                    } else {
                        requestRuntimePermission(
                            Permission.Group.CAMERA,
                            object : PermissionListener {
                                override fun onGranted() {
                                    PhotoUtil.openCamera(this@PhotoActivity)
                                }

                                override fun onDenied(deniedPermissions: List<String?>?) { // 无权限 无法进行拍照
                                }
                            })
                    }
                } else {
                    PhotoUtil.openCamera(this)
                }
            }
            R.id.album -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 处理访问文件权限
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        PhotoUtil.openAlbum(this@PhotoActivity)
                    } else {
                        requestRuntimePermission(
                            Permission.Group.STORAGE,
                            object : PermissionListener {
                                override fun onGranted() {
                                    PhotoUtil.openAlbum(this@PhotoActivity)
                                }

                                override fun onDenied(deniedPermissions: List<String?>?) { // 无权限处理
                                }
                            })
                    }
                } else {
                    PhotoUtil.openAlbum(this@PhotoActivity)
                }
            }
        }
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PhotoUtil.CAMERA_CODE_PHOTO -> if (!TextUtils.isEmpty(PhotoUtil.path)) {
                val file = File(PhotoUtil.path)
                if (file.isFile) {
                    Glide.with(this).load(PhotoUtil.path).into(result)
                }
            }
            PhotoUtil.ALBUM_CODE_SELECT_PICTURE -> if (data != null) {
                val uri = data.data
                if (uri != null) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        val fileName = getFileNameByUrl(uri)
                        copyUriToExternalFilesDir(uri, fileName)
                        val tempDir = getExternalFilesDir("temp")
                        if (tempDir != null) {
                            Glide.with(this).load("$tempDir/$fileName").into(result);
                        } else {
                            Toast.makeText(
                                this,
                                "Copy file fail.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        val path = GetPathFromUri.instance.getPath(this, uri)
                        if (path != null) {
                            Glide.with(this).load(path).into(result)
                        } else {
                            Glide.with(this).load(uri).into(result)
                        }
                    }
                }
            }
            else -> {
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun getFileNameByUrl(uri: Uri): String {
        var fileName =
            SimpleDateFormat("yyyy_MMdd_hhmmss").format(Date()) + ".jpg"
        val cursor =
            contentResolver.query(uri, null, null, null, null)
        if (cursor != null && cursor.count > 0) {
            cursor.moveToFirst()
            fileName =
                cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME))
            cursor.close()
        }
        return fileName
    }

    private fun copyUriToExternalFilesDir(uri: Uri, fileName: String) {
        thread {
            val inputStream = contentResolver.openInputStream(uri)
            val tempDir = getExternalFilesDir("temp")
            if (inputStream != null && tempDir != null) {
                val file = File("$tempDir/$fileName")
                val fos = FileOutputStream(file)
                val bis = BufferedInputStream(inputStream)
                val bos = BufferedOutputStream(fos)
                val byteArray = ByteArray(1024)
                var bytes = bis.read(byteArray)
                while (bytes > 0) {
                    bos.write(byteArray, 0, bytes)
                    bos.flush()
                    bytes = bis.read(byteArray)
                }
                bos.close()
                fos.close()
                bis.close()
                runOnUiThread {
                    Toast.makeText(this, "Copy file into $tempDir succeeded.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}
