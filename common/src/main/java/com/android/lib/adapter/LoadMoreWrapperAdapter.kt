package com.android.lib.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.GridLayoutManager.SpanSizeLookup
import androidx.recyclerview.widget.RecyclerView
import com.android.lib.R


/**
 * date: 2019/1/30
 * desc: 封装上拉加载更多adapter
 */
class LoadMoreWrapperAdapter(private val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TAG = "LoadMoreWrapperAdapter"
    }

    // HeadView
    private var mHeaderView: View? = null
    // 头布局
    private val mTypeHeader = 0
    // 普通布局
    private val mTypeItem = 1
    // 脚布局
    private val mTypeFooter = 2
    // 正在加载
    val loading = 1
    // 加载完成
    val loadingComplete = 2
    // 加载到底
    val loadingEnd = 3
    // 当前加载状态，默认为加载完成
    private var loadState = 2

    fun addHeaderView(headerView: View) {
        mHeaderView = headerView
        notifyItemInserted(0) // 通知增加单条数据
    }

    override fun getItemViewType(position: Int): Int {
        return if (mHeaderView != null && position == 0) { // 第一个item设置为HeadView
            mTypeHeader
        } else if (position + 1 == itemCount) { // 最后一个item设置为FooterView
            mTypeFooter
        } else { // 普通View
            mTypeItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder { // 通过判断显示类型，来创建不同的View
        return if (viewType == mTypeHeader && mHeaderView != null) {
            HeadViewHolder(mHeaderView!!)
        } else if (viewType == mTypeFooter) {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_refresh_footer, parent, false)
            FootViewHolder(view)
        } else {
            adapter.onCreateViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        var pos = position
        when {
            getItemViewType(pos) == mTypeHeader -> {
                return
            }
            holder is FootViewHolder -> {
                when (loadState) {
                    loading -> { // 加载中
                        holder.pbLoading.visibility = View.VISIBLE
                        holder.tvLoading.visibility = View.VISIBLE
                        holder.llEnd.visibility = View.GONE
                    }
                    loadingComplete -> { // 加载完成
                        holder.pbLoading.visibility = View.INVISIBLE
                        holder.tvLoading.visibility = View.INVISIBLE
                        holder.llEnd.visibility = View.GONE
                    }
                    loadingEnd -> { // 加载到底
                        holder.pbLoading.visibility = View.GONE
                        holder.tvLoading.visibility = View.GONE
                        holder.llEnd.visibility = View.VISIBLE
                    }
                    else -> {
                    }
                }
            }
            else -> {
                if (mHeaderView != null && pos > 0) {
                    pos--
                }
                adapter.onBindViewHolder(holder, pos)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (mHeaderView != null) {
            adapter.itemCount + 2
        } else {
            adapter.itemCount + 1
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val manager = recyclerView.layoutManager
        if (manager is GridLayoutManager) { // 首先判断当前是否为网格布局
            // GridLayoutManager设置一个SpanSizeLookup，这是一个抽象类，里面有一个抽象方法getSpanSize，这个方法的返回值决定了每个Item占据的单元格数。
            manager.spanSizeLookup = object : SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int { //return getItemViewType(position) == TYPE_FOOTER ? gridManager.getSpanCount() : 1;
                    return when {
                        getItemViewType(position) == mTypeHeader -> { // 如果当前是HeaderView则占所有列，否则只占自己列
                            manager.spanCount
                        }
                        getItemViewType(position) == mTypeFooter -> { // 如果当前是footer的位置，那么该item占据2个单元格，正常情况下占据1个单元格
                            manager.spanCount
                        }
                        else -> { // 正常
                            1
                        }
                    }
                }
            }
        }
    }

    private inner class HeadViewHolder
        (itemView: View) : RecyclerView.ViewHolder(itemView)

    private inner class FootViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var pbLoading = itemView.findViewById<View>(R.id.pb_loading) as ProgressBar
        var tvLoading = itemView.findViewById<View>(R.id.tv_loading) as TextView
        var llEnd = itemView.findViewById<View>(R.id.ll_end) as LinearLayout
    }

    /**
     * 设置上拉加载状态
     *
     * @param loadState 1.正在加载 2.加载完成 3.加载到底
     */
    fun setLoadState(loadState: Int) {
        this.loadState = loadState
        notifyDataSetChanged()
    }

}