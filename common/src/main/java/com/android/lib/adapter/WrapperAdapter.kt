package com.android.lib.adapter

import androidx.recyclerview.widget.RecyclerView

/**
 * date: 2019/1/30
 * desc: 包装的adapter(RecyclerView添加头部与底部使用)
 */
interface WrapperAdapter {
    val wrappedAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
}