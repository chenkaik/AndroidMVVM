package com.android.mvvm

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.lib.Logger
import com.android.lib.banner.RecyclerViewBannerBaseView
import com.android.lib.util.InputTextHelper
import com.android.mvvm.util.Person
import com.android.mvvm.util.UserConfig
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private val json = "{\"name\": \"test\",\"age\": 66}"

    private val test = "t"
    private lateinit var person: Person

    private lateinit var inputTextHelper: InputTextHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewRes: MutableList<String> =
            ArrayList()
        viewRes.add("http://www.3goodsoft.net/sys/static/sanhao/h5/img/banner3.png")
        viewRes.add("http://www.3goodsoft.net/sys/static/sanhao/h5/img/banner1.png?v=1")
        viewRes.add("http://www.3goodsoft.net/sys/static/sanhao/h5/img/banner0.png")
        viewRes.add("http://www.3goodsoft.net/sys/static/sanhao/h5/img/banner4.png")
        banner.initBannerImageView(viewRes,
            object : RecyclerViewBannerBaseView.OnBannerItemClickListener {
                override fun onItemClick(position: Int) {
                    Toast.makeText(this@MainActivity, position.toString(), Toast.LENGTH_SHORT).show()
                }
            })

        UserConfig.putToken("this is 哈哈")
//        isDarkTheme(this)
//        startActivity<MainActivity>(this)
//        if ("hello" beginsWith "he"){
//
//        }
//        "t".showToast(this)
//        R.string.app_name.showToast(this)
//        val dialog = CommonDialog(this)
        inputTextHelper = InputTextHelper(button)
        inputTextHelper.addViews(editText1, editText2)
        button.setOnClickListener {
            //            dialog.showProgress("hh")
//            dialog.showAlertDialog("提示","这是内容", View.OnClickListener {
//                "确定".showToast()
//            }, View.OnClickListener {
//                "取消".showToast()
//            })
//            dialog.showOneAlertDialog("提示1","这是内容1",View.OnClickListener {
//                "确定了".showToast()
//            })
//            person.age = 29
//            Logger.e(TAG, DoubleCalculation.div(1.6, 1.3, 2).toString())
//            val downloadTask = externalCacheDir?.let { it1 ->
//                FileDownloadTask(object : FileDownloadListener {
//                    override fun onProgress(downloadLength: Long, contentLength: Long) {
//                        com.android.lib.Logger.e(TAG, downloadLength.toString())
//                    }
//
//                    override fun onProgressUpdate(
//                        progress: Int,
//                        contentLength: Long,
//                        percentage: Int
//                    ) {
//                        com.android.lib.Logger.e(TAG, progress.toString())
//                    }
//
//                    override fun onSuccess(file: File?) {
//                        file?.path?.let { it2 -> com.android.lib.Logger.e(TAG, it2) }
//                    }
//
//                    override fun onFailed() {
//                        com.android.lib.Logger.e(TAG, "onFailed")
//                    }
//
//                }, it1)
//            }
//            downloadTask?.execute("http://download.taobaocdn.com/wireless/xiami-android-spark/latest/xiami-android-spark_701287.apk")

//            GetPathFromUri.instance.getPath()


//            if (NetworkUtil.isNetworkAvailable(this)) {
//                Logger.e(TAG, "1")
//            } else {
//                Logger.e(TAG, "2")
//            }

            UserConfig.token?.let { it1 -> Logger.e(TAG, it1) }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        inputTextHelper.removeViews()
    }

}
