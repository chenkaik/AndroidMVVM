package com.android.lib.util

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import java.util.*

/**
 * date: 2019/5/20
 * desc: 文本输入辅助类，通过管理多个 EditText 输入是否为空来启用或者禁用按钮的点击事件
 */
class InputTextHelper (
    view: View,
    alpha: Boolean = false
) : TextWatcher {

    private val mView : View = view // 操作按钮的View
    private val isAlpha: Boolean = alpha // 是否禁用后设置半透明
    private var mViewSet: MutableList<EditText>? = null // EditText集合

    /**
     * 添加 EditText
     *
     * @param views 传入单个或者多个 EditText
     */
    fun addViews(vararg views: EditText) {
        if (mViewSet == null) {
            mViewSet = ArrayList(views.size - 1)
        }
        for (view in views) {
            view.addTextChangedListener(this)
            mViewSet?.add(view)
        }
        afterTextChanged(null)
    }

    /**
     * 移除 EditText 监听，避免内存泄露
     */
    fun removeViews() {
        if (mViewSet == null) {
            return
        }
        for (view in mViewSet!!) {
            view.removeTextChangedListener(this)
        }
        mViewSet?.clear()
        mViewSet = null
    }

    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
    }

    override fun afterTextChanged(s: Editable?) {
        if (mViewSet == null) {
            return
        }
        for (view in mViewSet!!) {
            if ("" == view.text.toString().trim { it <= ' ' }) {
                setEnabled(false)
                return
            }
        }
        setEnabled(true)
    }

    /**
     * 设置 View 的事件
     *
     * @param enabled 启用或者禁用 View 的事件
     */
    private fun setEnabled(enabled: Boolean) {
        if (enabled == mView.isEnabled) {
            return
        }
        if (enabled) {
            mView.isEnabled = true // 启用View的事件
            if (isAlpha) {
                mView.alpha = 1f // 设置不透明
            }
        } else {
            mView.isEnabled = false // 禁用View的事件
            if (isAlpha) {
                mView.alpha = 0.5f // 设置半透明
            }
        }
    }

}