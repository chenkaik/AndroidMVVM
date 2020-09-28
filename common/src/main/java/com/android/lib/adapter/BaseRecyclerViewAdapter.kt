package com.android.lib.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.lib.Logger.e

/**
 * date: 2019/1/30
 * desc: RecyclerView adapter基类
 * 封装了数据集合以及ItemView的点击事件回调,同时暴露 [.onBindData]
 * 用于数据与view绑定
 */
abstract class BaseRecyclerViewAdapter<T>(
    val context: Context,
    data: List<T>?,
    private val layoutId: Int,
    onViewClickListener: OnViewClickListener? = null,
    onMenuClickLister: OnMenuClickLister? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    View.OnClickListener, OnLongClickListener {

    protected var mContext: Context = context
    private var mData: List<T> = data ?: ArrayList<T>()
    private var mListener: OnItemClickListener? = null
    private var mLongListener: OnItemLongClickListener? = null
    protected var mOnViewClickListener: OnViewClickListener? = onViewClickListener // item子view点击事件
    protected var mOnMenuClickLister: OnMenuClickLister? = onMenuClickLister // 侧滑菜单点击事件

    companion object {
        private const val TAG = "BaseRecyclerViewAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView =
            LayoutInflater.from(mContext).inflate(layoutId, parent, false)
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
        holder: RecyclerView.ViewHolder,
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
        holder: RecyclerView.ViewHolder,
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

    /**
     * item的侧滑菜单
     */
    interface OnMenuClickLister {
        /**
         * @param view click view
         * @param position item position
         * @param type menu type
         */
        fun onMenuClick(view: View?, position: Int, type: Int)
    }

//    override fun onViewAttachedToWindow(holder: RecyclerViewHolder) {
//        super.onViewAttachedToWindow(holder)
//        // 当Item进入这个页面的时候调用
//    }
//
//    override fun onViewDetachedFromWindow(holder: RecyclerViewHolder) {
//        super.onViewDetachedFromWindow(holder)
//        // 当Item离开这个页面的时候调用
//    }
//
//    override fun onViewRecycled(holder: RecyclerViewHolder) {
//        super.onViewRecycled(holder)
//        // 当Item被回收的时候调用
//    }

}