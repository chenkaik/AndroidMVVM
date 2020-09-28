package com.android.mvvm.activity

import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.lib.adapter.BaseRecyclerViewAdapter
import com.android.lib.adapter.LoadMoreWrapperAdapter
import com.android.lib.listener.EndlessRecyclerOnScrollListener
import com.android.lib.util.kotlin.startActivity
import com.android.mvvm.R
import com.android.mvvm.adapter.BaseRecyclerAdapter
import com.android.mvvm.util.showToast
import kotlinx.android.synthetic.main.activity_base_recycler_view.*
import kotlinx.android.synthetic.main.common_head_layout.*
import java.util.*
import kotlin.collections.ArrayList

class BaseRecyclerViewActivity : BaseActivity(), BaseRecyclerViewAdapter.OnItemClickListener,
    BaseRecyclerViewAdapter.OnItemLongClickListener, BaseRecyclerViewAdapter.OnViewClickListener,
    BaseRecyclerViewAdapter.OnMenuClickLister {

    private val list: MutableList<String> = ArrayList()
    private lateinit var loadMoreWrapperAdapter: LoadMoreWrapperAdapter
    private lateinit var baseRecyclerAdapter: BaseRecyclerAdapter
    private val total = 100

    companion object {
        private const val TAG = "BaseRecyclerViewActivity"
        fun actionStart(activity: FragmentActivity, isPutStack: Boolean) {
            startActivity<BaseRecyclerViewActivity>(activity, isPutStack)
        }
    }

    override fun getLayoutId() = R.layout.activity_base_recycler_view

    override fun initView() {
        navigationBar.setTitle("RecyclerView的基本使用")
        baseRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun initData() {
        getData()
        baseRecyclerAdapter = BaseRecyclerAdapter(this, list, this, this)
        baseRecyclerAdapter.setOnItemClickListener(this)
        baseRecyclerAdapter.setOnItemLongClickListener(this)
        loadMoreWrapperAdapter = LoadMoreWrapperAdapter(baseRecyclerAdapter)
        baseRecyclerView.adapter = loadMoreWrapperAdapter
        baseRecyclerView.addOnScrollListener(object : EndlessRecyclerOnScrollListener() {
            override fun onLoadMore() {
                if (list.size < total) {
                    loadMoreWrapperAdapter.setLoadState(loadMoreWrapperAdapter.loading)
                    Timer().schedule(object : TimerTask() {
                        override fun run() {
                            runOnUiThread {
                                getData()
                                loadMoreWrapperAdapter.setLoadState(loadMoreWrapperAdapter.loadingComplete)
                            }
                        }
                    }, 1000)
                } else {
                    loadMoreWrapperAdapter.setLoadState(loadMoreWrapperAdapter.loadingEnd)
                }
            }
        })
        baseRefreshLayout.setColorSchemeColors(
            ContextCompat.getColor(
                this,
                R.color.colorButtonPressed
            )
        )
        baseRefreshLayout.setOnRefreshListener {
            list.clear()
            getData()
            loadMoreWrapperAdapter.notifyDataSetChanged()
            baseRefreshLayout.postDelayed({
                baseRefreshLayout.isRefreshing = false
            }, 1000)
        }
    }

    private fun getData() {
        for (t in 0 until 20) {
            list.add("测试数据$t")
        }
    }

    override fun onItemClick(adapter: RecyclerView.Adapter<*>, v: View, position: Int) {
        "点击了$position".showToast()
    }

    override fun onItemLongClick(adapter: RecyclerView.Adapter<*>, v: View, position: Int) {
        "长按了$position".showToast()
    }

    override fun onViewClick(position: Int, type: Int) {
        "子View点击了$position".showToast()
    }

    override fun onMenuClick(view: View?, position: Int, type: Int) {
        when (type) {
            1 -> {
                "侧滑删除$position".showToast()
            }
            2 -> {
                "侧滑添加$position".showToast()
            }
        }
        baseRecyclerView.closeMenu()
    }

}
