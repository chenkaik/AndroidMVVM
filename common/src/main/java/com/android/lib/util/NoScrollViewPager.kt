package com.android.lib.util

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

/**
 * date: 2019/3/8
 * desc: 禁止ViewPager滑动
 */
class NoScrollViewPager : ViewPager {

    private var noScroll = true // 是否禁止左右滑动，true为禁止，false为不禁止

    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs
    )

    constructor(context: Context) : super(context)

    fun setNoScroll(noScroll: Boolean) {
        this.noScroll = noScroll
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(arg0: MotionEvent): Boolean {
        return if (noScroll) {
            false
        } else {
            super.onTouchEvent(arg0)
        }
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent): Boolean {
        return if (noScroll) {
            false
        } else {
            super.onInterceptTouchEvent(arg0)
        }
    }

}