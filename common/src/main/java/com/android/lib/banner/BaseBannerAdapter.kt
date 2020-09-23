package com.android.lib.banner

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * date: 2019/1/30
 * desc: banner使用的baseAdapter
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseBannerAdapter<VH : RecyclerView.ViewHolder>(
    protected var mContext: Context,
    protected var mUrlList: List<String>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return createCustomViewHolder(parent, viewType)
    }

    /**
     * 创建自定义的ViewHolder
     *
     * @param parent   父类容器
     * @param viewType view类型[.getItemViewType]
     * @return ImgHolder
     */
    protected abstract fun createCustomViewHolder(parent: ViewGroup?, viewType: Int): VH

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        bindCustomViewHolder(holder as VH, position)
    }

    /**
     * 绑定自定义的ViewHolder
     *
     * @param holder   ImgHolder
     * @param position 位置
     */
    abstract fun bindCustomViewHolder(holder: VH, position: Int)

    override fun getItemCount(): Int { // 如果只有一张图片就不进行滑动了
        return if (mUrlList.size < 2) 1 else Int.MAX_VALUE
    }

}