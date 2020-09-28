package com.android.lib.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * date: 2019/7/22
 * desc: RecyclerView滑动监听
 */
abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener() {

    // 用来标记是否正在向上滑动
    private var isSlidingUpward = false

    override fun onScrollStateChanged(
        recyclerView: RecyclerView,
        newState: Int
    ) {
        super.onScrollStateChanged(recyclerView, newState)
        val manager = recyclerView.layoutManager as LinearLayoutManager?
        if (newState == RecyclerView.SCROLL_STATE_IDLE) { // 当不滑动时
            if (manager != null) {
                // 获取当前最后一个完全显示的itemPosition
                val lastItemPosition = manager.findLastCompletelyVisibleItemPosition()
                // 得到item的总数
                val itemCount = manager.itemCount
                // 判断是否滑动到了最后一个item，并且是向上滑动
                if (lastItemPosition == itemCount - 1 && isSlidingUpward) { // 加载更多
                    onLoadMore()
                }
            }
        }
    }

    /**
     * onScrolled这个回调方法，里面有dx、dy这两个参数，当向上滑动的时候dy是大于0的，
     * 向左滑动的时候dx是大于0的，反方向滑动则小于0，
     * 所以这段代码稍稍修改一下就可以适用于横向滑动列表的监听。
     */
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
        isSlidingUpward = dy > 0
    }

    /**
     * 加载更多回调
     */
    abstract fun onLoadMore()
}