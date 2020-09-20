package com.android.lib.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.lib.Logger.e
import java.util.*

/**
 * date: 2019/1/30
 * desc: RecyclerView adapter基类
 * 封装了数据集合以及ItemView的点击事件回调,同时暴露 [.onBindData]
 * 用于数据与view绑定
 */
abstract class BaseRecyclerViewAdapter<T> : RecyclerView.Adapter<RecyclerViewHolder>,
    View.OnClickListener, OnLongClickListener {

    private lateinit var mContext: Context
    private lateinit var mData: List<T>
    private var mLayoutId = 0
    private var mListener: OnItemClickListener? = null
    private var mLongListener: OnItemLongClickListener? = null
    protected var mOnViewClickListener: OnViewClickListener? = null // item子view点击事件

    companion object {
        private const val TAG = "BaseRecyclerViewAdapter"
    }

    constructor(
        context: Context,
        data: List<T>?,
        layoutId: Int
    ) {
        init(context, data, layoutId)
    }

    constructor(
        context: Context,
        data: List<T>?,
        layoutId: Int,
        onViewClickListener: OnViewClickListener?
    ) {
        init(context, data, layoutId)
        mOnViewClickListener = onViewClickListener
    }

    /**
     * 初始化
     *
     * @param context  上下文
     * @param data     数据源
     * @param layoutId 布局id
     */
    private fun init(
        context: Context,
        data: List<T>?,
        layoutId: Int
    ) {
        mContext = context
        mData = data ?: ArrayList<T>()
        mLayoutId = layoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val itemView =
            LayoutInflater.from(mContext).inflate(mLayoutId, parent, false)
        if (mListener != null) {
            itemView.setOnClickListener(this)
        }
        if (mLongListener != null) {
            itemView.setOnLongClickListener(this)
        }
        e(TAG, "onCreateViewHolder")
        return RecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: RecyclerViewHolder,
        position: Int
    ) {
        e(TAG, "onBindViewHolder $position");
        holder.itemView.tag = position // 点击事件的位置
        val bean = mData[position]
        onBindData(holder, bean, position)
    }

    override fun getItemCount() = mData.size

    override fun onClick(v: View) {
        mListener?.onItemClick(this, v, v.tag as Int)
    }

    override fun onLongClick(v: View): Boolean {
        mLongListener?.onItemLongClick(this, v, v.tag as Int)
        return true
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        mListener = onItemClickListener
    }

    fun setOnItemLongClickListener(onItemLongClickListener: OnItemLongClickListener) {
        mLongListener = onItemLongClickListener
    }

    /**
     * 数据绑定，由实现类实现
     */
    protected abstract fun onBindData(
        holder: RecyclerViewHolder,
        bean: T,
        position: Int
    )

    /**
     * item点击监听器
     */
    interface OnItemClickListener {
        fun onItemClick(
            adapter: RecyclerView.Adapter<*>,
            v: View,
            position: Int
        )
    }

    /**
     * item长按监听器
     */
    interface OnItemLongClickListener {
        fun onItemLongClick(
            adapter: RecyclerView.Adapter<*>,
            v: View,
            position: Int
        )
    }

    /**
     * item中子view的点击事件（回调）
     */
    interface OnViewClickListener {
        /**
         * @param position item position
         * @param type     点击的view的类型，调用时根据不同的view传入不同的值加以区分
         */
        fun onViewClick(position: Int, type: Int)
    }

    override fun onViewAttachedToWindow(holder: RecyclerViewHolder) {
        super.onViewAttachedToWindow(holder)
        // 当Item进入这个页面的时候调用
    }

    override fun onViewDetachedFromWindow(holder: RecyclerViewHolder) {
        super.onViewDetachedFromWindow(holder)
        // 当Item离开这个页面的时候调用
    }

    override fun onViewRecycled(holder: RecyclerViewHolder) {
        super.onViewRecycled(holder)
        // 当Item被回收的时候调用
    }

}