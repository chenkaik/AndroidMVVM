package com.android.lib.banner

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * date: 2019/1/30
 * desc: banner使用的View
 */
class RecyclerViewBannerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerViewBannerBaseView<LinearLayoutManager, BannerRecyclerAdapter>(
    context,
    attrs,
    defStyleAttr
) {

    override fun onBannerScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int
    ) { // 解决连续滑动时指示器不更新的问题
        if (bannerSize < 2) {
            return
        }
        val firstReal = mLayoutManager!!.findFirstVisibleItemPosition()
        val viewFirst = mLayoutManager?.findViewByPosition(firstReal)
        val width = width.toFloat()
        if (width != 0f && viewFirst != null) {
            val right = viewFirst.right.toFloat()
            val ratio = right / width
            if (ratio > 0.8) {
                if (currentIndex != firstReal) {
                    currentIndex = firstReal
                    refreshIndicator()
                }
            } else if (ratio < 0.2) {
                if (currentIndex != firstReal + 1) {
                    currentIndex = firstReal + 1
                    refreshIndicator()
                }
            }
        }
    }

    override fun onBannerScrollStateChanged(
        recyclerView: RecyclerView,
        newState: Int
    ) {
        val first = mLayoutManager!!.findFirstVisibleItemPosition()
        val last = mLayoutManager!!.findLastVisibleItemPosition()
        if (currentIndex != first && first == last) {
            currentIndex = first
            refreshIndicator()
        }
    }

    override fun getLayoutManager(
        context: Context,
        orientation: Int
    ): LinearLayoutManager {
        return LinearLayoutManager(context, orientation, false)
    }

    override fun getAdapter(
        context: Context,
        list: List<String>,
        onBannerItemClickListener: OnBannerItemClickListener?
    ): BannerRecyclerAdapter {
        return BannerRecyclerAdapter(context, list, onBannerItemClickListener)
    }
}