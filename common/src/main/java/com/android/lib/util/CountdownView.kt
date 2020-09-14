package com.android.lib.util

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.TextView
import androidx.annotation.RequiresApi

/**
 * date: 2019/6/17
 * desc: 验证码倒计时，直接在xml布局中引用
 * <com.android.lib.util.CountdownView
    android:id="@+id/cv_register_countdown"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:padding="@dimen/space_20"
    android:text="发送验证码"
    android:textColor="@color/colorAccent" />
 */
@SuppressLint("AppCompatCustomView")
class CountdownView : TextView, Runnable {

    private var mTotalTime = 60 // 倒计时秒数
    private var mCurrentTime = 0 // 当前秒数 = 0
    private var mRecordText // 记录原有的文本
            : CharSequence? = null
    private var mFlag = false // 标记是否重置了倒计控件 = false

    companion object {
        private const val TIME_UNIT = "S" // 秒数单位文本
    }

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    /**
     * 设置倒计时总秒数
     */
    fun setTotalTime(totalTime: Int) {
        mTotalTime = totalTime
    }

    /**
     * 重置倒计时控件
     */
    fun resetState() {
        mFlag = true
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        // 设置点击的属性
        isClickable = true
    }

    override fun onDetachedFromWindow() { // 移除延迟任务，避免内存泄露
        removeCallbacks(this)
        super.onDetachedFromWindow()
    }

    override fun performClick(): Boolean {
        val click = super.performClick()
        mRecordText = text
        isEnabled = false
        mCurrentTime = mTotalTime
        post(this)
        return click
    }

    @SuppressLint("SetTextI18n")
    override fun run() {
        if (mCurrentTime == 0 || mFlag) {
            text = mRecordText
            isEnabled = true
            mFlag = false
        } else {
            mCurrentTime--
            text = mCurrentTime.toString() + "\t" + TIME_UNIT
            postDelayed(this, 1000)
        }
    }

}