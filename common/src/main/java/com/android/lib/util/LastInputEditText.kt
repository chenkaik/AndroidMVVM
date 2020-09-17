package com.android.lib.util

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.R
import androidx.appcompat.widget.AppCompatEditText

/**
 * date: 2020/9/17
 * desc: EditText光标默认在最后面,点击整栏也在最后面,点击文本光标在对应位置
 */
class LastInputEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyle) {

    init {
        setSelection(text!!.length)
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)
        // 光标首次获取焦点是在最后面，之后操作就是按照点击的位置移动光标
        if (isEnabled && hasFocus() && hasFocusable()) {
            if (selStart == 0) {
                setSelection(text!!.length)
            } else {
                setSelection(selEnd)
            }
        } else {
            setSelection(text!!.length)
        }
    }

}