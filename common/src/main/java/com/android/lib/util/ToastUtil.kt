package com.android.lib.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

/**
 * date: 2019/10/11
 * desc: toast的封装
 */
object ToastUtil {
//    private var sToast: Toast? = null
//    @SuppressLint("ShowToast")
//    fun showToast(context: Context?, content: String?) {
//        if (sToast == null) {
//            sToast = Toast.makeText(context, content, Toast.LENGTH_SHORT)
//        } else {
//            sToast!!.setText(content)
//        }
//        sToast!!.show()
//    }

    private lateinit var sToast: Toast
    @SuppressLint("ShowToast")
    fun showToast(context: Context?, content: String?) {
        if (!ToastUtil::sToast.isInitialized) {
            sToast = Toast.makeText(context, content, Toast.LENGTH_SHORT)
        } else {
            sToast.setText(content)
        }
        sToast.show()
    }

}