package com.android.lib.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.android.lib.R

/**
 * date: 2019/1/30
 * desc: 公用的弹框
 */
class CommonAlertDialog {

    class Builder(private val mContext: Context) {

        private var mDialog: Dialog? = null
        private var mViewHolder: ViewHolder? = null
        private lateinit var mView: View
        private var hasPos = false
        private var hasNeg = false

        init {
            initView()
        }
        /**
         * 设置title
         *
         * @param title 标题
         * @return builder
         */
        fun setTitle(title: CharSequence?): Builder {
            mViewHolder!!.tvTitle.text = title
            return this
        }

        /**
         * @param title 显示的文字
         * @param color 显示的文字的颜色
         * @return builder
         */
        fun setTitle(
            title: CharSequence?,
            color: Int
        ): Builder {
            mViewHolder!!.tvTitle.text = title
            mViewHolder!!.tvTitle.setTextColor(ContextCompat.getColor(mContext, color))
            return this
        }

        /**
         * 设置title
         *
         * @param resId 显示的文字id
         * @return builder
         */
        fun setTitle(resId: Int): Builder {
            mViewHolder!!.tvTitle.setText(resId)
            return this
        }

        /**
         * 设置title
         *
         * @param resId 显示的文字id
         * @param color 显示的文字的颜色
         * @return builder
         */
        fun setTitle(resId: Int, color: Int): Builder {
            mViewHolder!!.tvTitle.setText(resId)
            mViewHolder!!.tvTitle.setTextColor(ContextCompat.getColor(mContext, color))
            return this
        }

        /**
         * 设置显示内容
         *
         * @param title 显示内容
         * @return builder
         */
        fun setMessage(title: CharSequence?): Builder {
            mViewHolder!!.tvMessage.text = title
            return this
        }

        /**
         * 设置显示内容
         *
         * @param title 显示内容
         * @param color 显示内容颜色
         * @return builder
         */
        fun setMessage(
            title: CharSequence?,
            color: Int
        ): Builder {
            mViewHolder!!.tvMessage.text = title
            mViewHolder!!.tvMessage.setTextColor(ContextCompat.getColor(mContext, color))
            return this
        }

        /**
         * 设置显示内容
         *
         * @param resId 显示内容id
         * @return builder
         */
        fun setMessage(resId: Int): Builder {
            mViewHolder!!.tvMessage.setText(resId)
            return this
        }

        /**
         * 设置显示内容
         *
         * @param resId 显示内容id
         * @param color 显示内容颜色
         * @return builder
         */
        fun setMessage(resId: Int, color: Int): Builder {
            mViewHolder!!.tvMessage.setText(resId)
            mViewHolder!!.tvMessage.setTextColor(ContextCompat.getColor(mContext, color))
            return this
        }

        /**
         * 确定按钮
         *
         * @param listener 按钮监听
         * @return builder
         */
        fun setPositiveButton(listener: View.OnClickListener?): Builder {
            mViewHolder!!.tvCenterButton.visibility = View.GONE
            mViewHolder!!.tvPositiveButton.visibility = View.VISIBLE
            hasPos = true
            mViewHolder!!.tvPositiveButton.setOnClickListener { view: View? ->
                dismiss()
                listener?.onClick(view)
            }
            return this
        }

        /**
         * 确定按钮
         *
         * @param text     按钮文字
         * @param listener 按钮监听
         * @return builder
         */
        fun setPositiveButton(
            text: CharSequence?,
            listener: View.OnClickListener?
        ): Builder {
            mViewHolder!!.tvCenterButton.visibility = View.GONE
            mViewHolder!!.tvPositiveButton.visibility = View.VISIBLE
            hasPos = true
            mViewHolder!!.tvPositiveButton.text = text
            mViewHolder!!.tvPositiveButton.setOnClickListener { view: View? ->
                dismiss()
                listener?.onClick(view)
            }
            return this
        }

        /**
         * 确定按钮
         *
         * @param text     按钮文字
         * @param listener 按钮监听
         * @param color    按钮文字颜色
         * @return builder
         */
        fun setPositiveButton(
            text: CharSequence?,
            listener: View.OnClickListener?,
            color: Int
        ): Builder {
            mViewHolder!!.tvCenterButton.visibility = View.GONE
            mViewHolder!!.tvPositiveButton.visibility = View.VISIBLE
            hasPos = true
            mViewHolder!!.tvPositiveButton.text = text
            mViewHolder!!.tvPositiveButton.setTextColor(ContextCompat.getColor(mContext, color))
            mViewHolder!!.tvPositiveButton.setOnClickListener { view: View? ->
                dismiss()
                listener?.onClick(view)
            }
            return this
        }

        /**
         * 取消按钮
         *
         * @param listener 按钮监听
         * @return builder
         */
        fun setNegativeButton(listener: View.OnClickListener?): Builder {
            mViewHolder!!.tvCenterButton.visibility = View.GONE
            mViewHolder!!.tvNegativeButton.visibility = View.VISIBLE
            hasNeg = true
            mViewHolder!!.tvNegativeButton.setOnClickListener { view: View? ->
                dismiss()
                listener?.onClick(view)
            }
            return this
        }

        /**
         * 取消按钮
         *
         * @param text     按钮文字
         * @param listener 按钮监听
         * @return builder
         */
        fun setNegativeButton(
            text: CharSequence?,
            listener: View.OnClickListener?
        ): Builder {
            mViewHolder!!.tvCenterButton.visibility = View.GONE
            mViewHolder!!.tvNegativeButton.visibility = View.VISIBLE
            hasNeg = true
            mViewHolder!!.tvNegativeButton.text = text
            mViewHolder!!.tvNegativeButton.setOnClickListener { view: View? ->
                dismiss()
                listener?.onClick(view)
            }
            return this
        }

        /**
         * 取消按钮
         *
         * @param text     按钮文字
         * @param listener 按钮监听
         * @param color    按钮文字颜色
         * @return builder
         */
        fun setNegativeButton(
            text: CharSequence?,
            listener: View.OnClickListener?,
            color: Int
        ): Builder {
            mViewHolder!!.tvCenterButton.visibility = View.GONE
            mViewHolder!!.tvNegativeButton.visibility = View.VISIBLE
            hasNeg = true
            mViewHolder!!.tvNegativeButton.text = text
            mViewHolder!!.tvNegativeButton.setTextColor(ContextCompat.getColor(mContext, color))
            mViewHolder!!.tvNegativeButton.setOnClickListener { view: View? ->
                dismiss()
                listener?.onClick(view)
            }
            return this
        }

        /**
         * 显示一个按钮的弹窗
         *
         * @param listener 按钮监听
         * @return builder
         */
        fun setShowOneButton(listener: View.OnClickListener?): Builder {
            mViewHolder!!.tvNegativeButton.visibility = View.GONE
            mViewHolder!!.tvPositiveButton.visibility = View.GONE
            mViewHolder!!.line2.visibility = View.GONE
            mViewHolder!!.tvCenterButton.visibility = View.VISIBLE
            mViewHolder!!.tvCenterButton.setOnClickListener { v: View? ->
                dismiss()
                listener?.onClick(v)
            }
            return this
        }

        /**
         * 显示一个按钮的弹窗
         *
         * @param text     按钮文字
         * @param listener 按钮监听
         * @return builder
         */
        fun setShowOneButton(
            text: CharSequence?,
            listener: View.OnClickListener?
        ): Builder {
            mViewHolder!!.tvNegativeButton.visibility = View.GONE
            mViewHolder!!.tvPositiveButton.visibility = View.GONE
            mViewHolder!!.line2.visibility = View.GONE
            mViewHolder!!.tvCenterButton.visibility = View.VISIBLE
            mViewHolder!!.tvCenterButton.text = text
            mViewHolder!!.tvCenterButton.setOnClickListener { v: View? ->
                dismiss()
                listener?.onClick(v)
            }
            return this
        }

        /**
         * 显示一个按钮的弹窗
         *
         * @param text     按钮文字
         * @param listener 按钮监听
         * @param color    按钮文字颜色
         * @return builder
         */
        fun setShowOneButton(
            text: CharSequence?,
            listener: View.OnClickListener?,
            color: Int
        ): Builder {
            mViewHolder!!.tvNegativeButton.visibility = View.GONE
            mViewHolder!!.tvPositiveButton.visibility = View.GONE
            mViewHolder!!.line2.visibility = View.GONE
            mViewHolder!!.tvCenterButton.visibility = View.VISIBLE
            mViewHolder!!.tvCenterButton.text = text
            mViewHolder!!.tvCenterButton.setTextColor(ContextCompat.getColor(mContext, color))
            mViewHolder!!.tvCenterButton.setOnClickListener { v: View? ->
                dismiss()
                listener?.onClick(v)
            }
            return this
        }

        /**
         * 返回键是否可取消
         *
         * @param flag true & false
         * @return builder
         */
        fun setCancelable(flag: Boolean): Builder {
            mDialog!!.setCancelable(flag)
            return this
        }

        /**
         * 点击其它空白位置是否可取消
         *
         * @param flag true & false
         * @return builder
         */
        fun setCanceledOnTouchOutside(flag: Boolean): Builder {
            mDialog!!.setCanceledOnTouchOutside(flag)
            return this
        }

        fun create(): Dialog? {
            return mDialog
        }

        /**
         * 显示dialog
         */
        fun show() {
            if (mDialog != null) {
                if (hasPos || hasNeg) {
                    mViewHolder!!.line1.visibility = View.VISIBLE
                }
                if (hasPos && hasNeg) {
                    mViewHolder!!.line2.visibility = View.VISIBLE
                }
                mDialog!!.show()
            }
        }

        /**
         * 取消dialog
         */
        fun dismiss() {
            if (mDialog != null) {
                mDialog!!.dismiss()
                mDialog = null
            }
        }

        /**
         * 初始化dialog布局及控件
         */
        @SuppressLint("InflateParams")
        private fun initView() {
            mDialog = Dialog(mContext, R.style.common_alert_dialog_style)
            mView = LayoutInflater.from(mContext).inflate(R.layout.commo_alert_dialog_layout, null)
            mViewHolder =
                ViewHolder(mView)
            mDialog!!.setContentView(mView)
            val dm = DisplayMetrics()
            val windowManager =
                mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            windowManager?.defaultDisplay?.getMetrics(dm)
            val window = mDialog!!.window
            if (window != null) {
                val lp = window.attributes
                lp.width = (dm.widthPixels * 0.8).toInt()
                //lp.height = (int) (dm.widthPixels * 0.8);
                window.attributes = lp
            }
        }

        internal class ViewHolder(view: View) {
            var tvTitle = view.findViewById<View>(R.id.dialog_title) as TextView
            var tvMessage = view.findViewById<View>(R.id.dialog_message) as TextView
            var tvPositiveButton = view.findViewById<View>(R.id.dialog_positive) as TextView
            var tvNegativeButton = view.findViewById<View>(R.id.dialog_negative) as TextView
            var tvCenterButton = view.findViewById<View>(R.id.tv_alert_one_confirm) as TextView
            //            LinearLayout vgLayout;
            var line1: View = view.findViewById(R.id.dialog_line1)
            var line2: View = view.findViewById(R.id.dialog_line2)
        }

    }

}