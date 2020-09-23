package com.android.lib.adapter

import android.annotation.SuppressLint
import android.text.TextUtils
import android.util.SparseArray
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * date: 2019/1/30
 * desc: recyclerView公用的ViewHolder
 */
@Suppress("UNCHECKED_CAST")
class RecyclerViewHolder(private val mConvertView: View) :
    RecyclerView.ViewHolder(mConvertView) {

    private val mViews: SparseArray<View?> = SparseArray()

    /**
     * 通过id获得控件
     *
     * @param id  id
     * @param <K> k
     * @return 控件
    </K> */
    fun <K : View> findView(id: Int): K {
        return mConvertView.findViewById<View>(id) as K
    }

    /**
     * 通过id获得控件
     *
     * @param viewId id
     * @param <T>    控件
     * @return 控件
    </T> */
    fun <T : View> getView(viewId: Int): T {
        var view = mViews[viewId]
        if (view == null) {
            view = mConvertView.findViewById(viewId)
            mViews.put(viewId, view)
        }
        return view as T
    }

    /**
     * 绑定对应值
     *
     * @param viewId 控件id
     * @param text   文本内容
     */
    fun setText(viewId: Int, text: String?) {
        val textView = getView<TextView>(viewId)
        if (TextUtils.isEmpty(text)) {
            textView.text = ""
        } else {
            textView.text = text
        }
    }

    /**
     * 绑定对应值
     *
     * @param viewId 控件id
     * @param text   文本内容
     * @param value  其它
     */
    @SuppressLint("SetTextI18n")
    fun setText(viewId: Int, text: String, value: String) {
        val textView = getView<TextView>(viewId)
        if (TextUtils.isEmpty(value)) {
            textView.text = text
        } else { //            StringBuilder builder = new StringBuilder();
//            builder.append(text);
//            builder.append(value);
//            textView.setText(builder.toString());
            textView.text = text + value
        }
    }

}