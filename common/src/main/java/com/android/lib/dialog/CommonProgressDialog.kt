package com.android.lib.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.android.lib.R

/**
 * date: 2019/1/30
 * desc: 请求网络加载提示框
 */
class CommonProgressDialog : Dialog {

    private var mMessage: String?
    private var cancelTouchOutside: Boolean
    private var cancel: Boolean

    constructor(builder: Builder) : super(builder.mContext) {
        mMessage = builder.mMessage
        cancelTouchOutside = builder.cancelTouchOutside
        cancel = builder.cancelable
    }

    constructor(builder: Builder, theme: Int) : super(
        builder.mContext,
        theme
    ) {
        mMessage = builder.mMessage
        cancelTouchOutside = builder.cancelTouchOutside
        cancel = builder.cancelable
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.common_progress_layout)
        setCancelable(cancel)
        setCanceledOnTouchOutside(cancelTouchOutside)
        val textView = findViewById<View>(R.id.tv_load_dialog) as TextView
        if (!TextUtils.isEmpty(mMessage)) {
            textView.text = mMessage
        }
        //        WindowManager.LayoutParams params = getWindow().getAttributes();
//        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        getWindow().setAttributes(params);
    }

    class Builder(var mContext: Context) {
        var mMessage: String? = null
        var resStyle = -1
        var cancelTouchOutside = false
        var cancelable = false
        /**
         * 设置主题
         *
         * @param resStyle style id
         * @return ProgressDialog.Builder
         */
        fun setTheme(resStyle: Int): Builder {
            this.resStyle = resStyle
            return this
        }

        /**
         * 设置提示信息
         *
         * @param message 提示信息
         * @return builder
         */
        fun setMessage(message: String?): Builder {
            mMessage = message
            return this
        }

        /**
         * 是否可以取消
         *
         * @param cancelable true&false
         * @return builder
         */
        fun setCancelable(cancelable: Boolean): Builder {
            this.cancelable = cancelable
            return this
        }

        /**
         * 设置点击dialog外部是否取消dialog
         *
         * @param cancelTouchOutside 点击外部是否取消dialog
         * @return builder
         */
        fun cancelTouchOutside(cancelTouchOutside: Boolean): Builder {
            this.cancelTouchOutside = cancelTouchOutside
            return this
        }

        fun build(): CommonProgressDialog {
            return if (resStyle != -1) {
                CommonProgressDialog(this, resStyle)
            } else {
                CommonProgressDialog(this)
            }
        }
    }

}