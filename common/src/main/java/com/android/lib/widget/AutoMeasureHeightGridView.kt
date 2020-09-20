package com.android.lib.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.GridView

/**
 * date: 2019/1/30
 * desc: 根据item多少，自适应高度的列表
 */
class AutoMeasureHeightGridView : GridView {

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle) {
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        /**
         * int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
         * super.onMeasure(widthMeasureSpec, expandSpec);
         */
        val expandSpec =
            MeasureSpec.makeMeasureSpec(Int.MAX_VALUE shr 2, MeasureSpec.AT_MOST)
        //        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
//        int widthSize = getMeasuredWidth();
//        heightMeasureSpec =
//                widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, expandSpec)
    }

}