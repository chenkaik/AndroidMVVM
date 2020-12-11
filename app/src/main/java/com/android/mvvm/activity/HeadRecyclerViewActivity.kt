package com.android.mvvm.activity

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.lib.adapter.BaseRecyclerViewAdapter
import com.android.lib.util.kotlin.startActivity
import com.android.mvvm.R
import com.android.mvvm.adapter.BaseRecyclerAdapter
import com.android.mvvm.databinding.ActivityHeadRecyclerViewBinding
import com.android.mvvm.databinding.CommonHeadLayoutBinding
import com.android.mvvm.util.showToast

class HeadRecyclerViewActivity : BaseActivity(), BaseRecyclerViewAdapter.OnItemClickListener {

    private lateinit var activityHeadRecyclerViewBinding: ActivityHeadRecyclerViewBinding
    private lateinit var commonHeadLayoutBinding: CommonHeadLayoutBinding
    private val list: MutableList<String> = ArrayList()
    private lateinit var baseRecyclerAdapter: BaseRecyclerAdapter

    companion object {
        private const val TAG = "HeadRecyclerViewActivity"
        fun actionStart(activity: FragmentActivity, isPutStack: Boolean) {
            startActivity<HeadRecyclerViewActivity>(activity, isPutStack)
        }
    }

    override fun getLayoutView(): View {
        activityHeadRecyclerViewBinding = ActivityHeadRecyclerViewBinding.inflate(layoutInflater)
        commonHeadLayoutBinding = activityHeadRecyclerViewBinding.commonHead
        return activityHeadRecyclerViewBinding.root
    }

    override fun initView() {
        commonHeadLayoutBinding.navigationBar.setTitle("RecyclerView添加Head")
        activityHeadRecyclerViewBinding.headRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    @SuppressLint("InflateParams")
    override fun initData() {
        getData()
        baseRecyclerAdapter = BaseRecyclerAdapter(this, list)
        baseRecyclerAdapter.setOnItemClickListener(this)
        val headView = LayoutInflater.from(this).inflate(R.layout.add_head_layout, null)
        headView.findViewById<Button>(R.id.btn_test1).setOnClickListener {
            "测试1".showToast()
        }
        headView.findViewById<Button>(R.id.btn_test2).setOnClickListener {
            "测试2".showToast()
        }
        val footView = LayoutInflater.from(this).inflate(R.layout.add_foot_layout, null)
        footView.findViewById<Button>(R.id.btn_test3).setOnClickListener {
            "测试3".showToast()
        }
        footView.findViewById<Button>(R.id.btn_test4).setOnClickListener {
            "测试4".showToast()
        }
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        headView.layoutParams = params
        footView.layoutParams = params
        activityHeadRecyclerViewBinding.headRecyclerView.addHeaderView(headView)
        activityHeadRecyclerViewBinding.headRecyclerView.addFootView(footView)
        activityHeadRecyclerViewBinding.headRecyclerView.adapter = baseRecyclerAdapter
    }

    private fun getData() {
        for (t in 0 until 10) {
            list.add("测试数据$t")
        }
    }

    override fun onItemClick(adapter: RecyclerView.Adapter<*>, v: View, position: Int) {
        "点击了$position".showToast()
    }

}
