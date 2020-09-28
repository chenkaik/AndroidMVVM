package com.android.mvvm.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.lib.Logger.e
import com.android.lib.adapter.BaseRecyclerViewAdapter
import com.android.lib.adapter.RecyclerViewHolder
import com.android.mvvm.R

/**
 * date: 2020/9/27
 * desc:
 */
class BaseRecyclerAdapter(
    context: Context,
    data: List<String>?,
    onViewClickListener: OnViewClickListener? = null,
    onMenuClickLister: OnMenuClickLister? = null
) : BaseRecyclerViewAdapter<String>(
    context,
    data,
    R.layout.adapter_base_recycler,
    onViewClickListener,
    onMenuClickLister
) {

    init {
        e("BaseRecyclerAdapter", "data大小= ${data?.size}");
    }

    override fun onBindData(holder: RecyclerView.ViewHolder, bean: String, position: Int) {
        holder as RecyclerViewHolder
        val textView = holder.getView<TextView>(R.id.tv_test_item)
        textView.text = bean
        textView.setOnClickListener(ViewListener(mOnViewClickListener, position, 1))
        mContext
        holder.getView<TextView>(R.id.delete)
            .setOnClickListener(MenuListener(mOnMenuClickLister, position, 1))
        holder.getView<TextView>(R.id.add)
            .setOnClickListener(MenuListener(mOnMenuClickLister, position, 2))
    }

    inner class ViewListener(
        private val viewClickListener: OnViewClickListener?,
        private val position: Int,
        private val type: Int
    ) : View.OnClickListener {

        override fun onClick(v: View) {
            viewClickListener?.onViewClick(position, type)
        }

    }

    inner class MenuListener(
        private val onMenuClickLister: OnMenuClickLister?,
        private val position: Int,
        private val type: Int
    ) : View.OnClickListener {

        override fun onClick(v: View) {
            onMenuClickLister?.onMenuClick(v, position, type)
        }

    }

}