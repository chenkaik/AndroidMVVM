package com.android.lib.dialog

import android.app.Activity
import android.view.View
import com.android.lib.R
import com.android.lib.dialog.CommonProgressDialog

/**
 * date: 2019/1/30
 * desc: 弹框辅助类
 */
class CommonDialog(private val mActivity: Activity) {

    private var mCommonProgressDialog: CommonProgressDialog? = null

    /**
     * 显示CommonProgressDialog
     */
    fun showProgress(msg: String?) {
        mCommonProgressDialog = CommonProgressDialog.Builder(mActivity)
            .setTheme(R.style.commonProgressDialog)
            .setCancelable(false)
            .cancelTouchOutside(false)
            .setMessage(msg)
            .build()
        mCommonProgressDialog!!.show()
    }

    /**
     * 取消CommonProgressDialog
     */
    fun dismissProgress() {
        if (mCommonProgressDialog != null) {
            mCommonProgressDialog?.dismiss()
            mCommonProgressDialog = null
            //            if (commonProgressDialog.isShowing()) {
//                commonProgressDialog.dismiss();
//                commonProgressDialog = null;
//            } else {
//                commonProgressDialog = null;
//            }
        }
    }

    /**
     * 公用的弹框
     *
     * @param title      标题
     * @param message    内容
     * @param okListener 确定按钮的监听
     * @param noListener 取消按钮的监听
     */
    fun showAlertDialog(
        title: String?,
        message: String?,
        okListener: View.OnClickListener?,
        noListener: View.OnClickListener?
    ) {
        CommonAlertDialog.Builder(mActivity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(View.OnClickListener { v: View? ->
                okListener?.onClick(v)
            })
            .setNegativeButton(View.OnClickListener { v: View? ->
                noListener?.onClick(v)
            })
            .setCancelable(false)
            .setCanceledOnTouchOutside(false)
            .show()
    }

    /**
     * 公用的单个按钮弹框
     *
     * @param title      标题
     * @param message    内容
     * @param okListener 确定按钮的监听
     */
    fun showOneAlertDialog(
        title: String?,
        message: String?,
        okListener: View.OnClickListener?
    ) {
        CommonAlertDialog.Builder(mActivity)
            .setTitle(title)
            .setMessage(message)
            .setShowOneButton(View.OnClickListener { v: View? ->
                okListener?.onClick(v)
            })
            .setCancelable(false)
            .setCanceledOnTouchOutside(false)
            .show()
    }

}