package com.android.lib.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.android.lib.adapter.RecyclerWrapAdapter
import java.util.*

/**
 * date: 2019/1/30
 * desc: 可添加头部和底部的RecyclerView(注意调用适配器刷新数据要调本类的notifyDataSetChanged方法)
 */
class XRecyclerView : RecyclerView {

    private var mAdapter: Adapter<ViewHolder>? = null

    companion object {
        private val mHeaderViews = ArrayList<View>()
        private val mFootViews = ArrayList<View>()
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyle: Int
    ) : super(context, attrs, defStyle)

    fun addHeaderView(view: View) { // 添加Head
        mHeaderViews.clear()
        mHeaderViews.add(view)
        if (mAdapter != null) {
            if (mAdapter !is RecyclerWrapAdapter) {
                mAdapter = RecyclerWrapAdapter(
                    mHeaderViews,
                    mFootViews,
                    mAdapter!!
                )
                //                mAdapter.notifyDataSetChanged();
            }
        }
    }

    fun addFootView(view: View) { // 添加Foot
        mFootViews.clear()
        mFootViews.add(view)
        if (mAdapter != null) {
            if (mAdapter !is RecyclerWrapAdapter) {
                mAdapter = RecyclerWrapAdapter(
                    mHeaderViews,
                    mFootViews,
                    mAdapter!!
                )
                //                mAdapter.notifyDataSetChanged();
            }
        }
    }

    override fun setAdapter(adapter: Adapter<ViewHolder>?) {
        var adap = adapter
        if (mHeaderViews.isEmpty() && mFootViews.isEmpty()) {
            super.setAdapter(adap)
        } else { // 利用装饰者模式
            adap = RecyclerWrapAdapter(
                mHeaderViews,
                mFootViews,
                adap!!
            )
            super.setAdapter(adap)
        }
        mAdapter = adap
    }

    fun notifyDataSetChanged() {
        mAdapter?.notifyDataSetChanged()
    }

}