package com.android.lib.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

/**
 * date: 2019/1/30
 * desc: 包装adapter(RecyclerView添加头部与底部使用)
 */
class RecyclerWrapAdapter(
    mHeaderViews: ArrayList<View>?,
    mFootViews: ArrayList<View>?,
    override val wrappedAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), WrapperAdapter {

    private var mHeaderView: ArrayList<View>? = null
    private var mFootView: ArrayList<View>? = null
    private var mCurrentPosition = 0
    private val headersCount: Int
        get() = mHeaderView!!.size

    private val footersCount: Int
        get() = mFootView!!.size

    companion object {
        private val EMPTY_INFO_LIST =
            ArrayList<View>()
    }

    init {
        if (mHeaderViews == null) {
            mHeaderView = EMPTY_INFO_LIST
        } else {
            this.mHeaderView = mHeaderViews
        }
        if (mFootViews == null) {
            this.mFootView = EMPTY_INFO_LIST
        } else {
            this.mFootView = mFootViews
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        if (viewType == RecyclerView.INVALID_TYPE) {
            return HeaderViewHolder(mHeaderView!![0])
        } else if (viewType == RecyclerView.INVALID_TYPE - 1) {
            return HeaderViewHolder(mFootView!![0])
        }
        return wrappedAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val numHeaders = headersCount
        if (position < numHeaders) {
            return
        }
        val adjPosition = position - numHeaders
        var adapterCount = 0
        if (wrappedAdapter != null) {
            adapterCount = wrappedAdapter.itemCount
            if (adjPosition < adapterCount) {
                this.wrappedAdapter.onBindViewHolder(holder, adjPosition)
                return
            }
        }
    }

    override fun getItemCount(): Int { // 对Adapter的count进行了重新计算
        return if (wrappedAdapter != null) {
            headersCount + footersCount + wrappedAdapter.itemCount
        } else {
            headersCount + footersCount
        }
    }

    /**
     * 如果当前 position 的位置比 HeaderView 的数量小，
     * 那么返回的就是 HeaderView 的 对应的 View，
     * 否则再判断 原 Adapter 的 count 与当前 position 的差值来比较，
     * 是调用原 Adapter 的 getView 方法，还是获取 footView 的 view；
     * 目的就是添加了头部和尾部 View。
     *
     * @param position 当前位置
     * @return type
     */
    override fun getItemViewType(position: Int): Int {
        mCurrentPosition = position
        val numHeaders = headersCount
        if (position < numHeaders) {
            return RecyclerView.INVALID_TYPE
        }
        val adjPosition = position - numHeaders
        var adapterCount = 0
        if (wrappedAdapter != null) {
            adapterCount = wrappedAdapter.itemCount
            if (adjPosition < adapterCount) {
                return wrappedAdapter.getItemViewType(adjPosition)
            }
        }
        return RecyclerView.INVALID_TYPE - 1
    }

    override fun getItemId(position: Int): Long {
        val numHeaders = headersCount
        if (wrappedAdapter != null && position >= numHeaders) {
            val adjPosition = position - numHeaders
            val adapterCount = wrappedAdapter.itemCount
            if (adjPosition < adapterCount) {
                return wrappedAdapter.getItemId(adjPosition)
            }
        }
        return -1
    }

    private class HeaderViewHolder(itemView: View?) :
        RecyclerView.ViewHolder(itemView!!)


}