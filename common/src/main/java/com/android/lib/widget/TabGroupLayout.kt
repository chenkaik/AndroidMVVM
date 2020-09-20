package com.android.lib.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * date: 2019/1/30
 * desc: 底部导航
 */
class TabGroupLayout : LinearLayout {

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    fun select(index: Int) {
        val count = childCount
        for (i in 0 until count) {
            val v = getChildAt(i)
            v.isSelected = i == index
        }
    }

    override fun setOnClickListener(l: OnClickListener?) {
        val count = childCount
        for (i in 0 until count) {
            val v = getChildAt(i)
            v.setOnClickListener(l)
        }
    }

}