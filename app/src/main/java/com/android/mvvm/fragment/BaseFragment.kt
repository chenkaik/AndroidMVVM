package com.android.mvvm.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * date: 2020/9/21
 * desc: fragment基类
 */
abstract class BaseFragment : Fragment() {

    private var isOk = false // 是否完成View初始化
    private var isFirst = true // 是否为第一次加载

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getLayoutView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
        isOk = true // 完成initView后改变view的初始化状态为完成
    }

    override fun onResume() {
        super.onResume()
        initLoadData()
    }

    private fun initLoadData() {
        if (isOk && isFirst) { // 加载数据时判断是否完成view的初始化，以及是不是第一次加载此数据
            loadData()
            isFirst = false // 加载第一次数据后改变状态，后续不再重复加载
        }
    }

    // 引入布局
    protected abstract fun getLayoutView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View

    // 初始化控件
    protected abstract fun initView()

    // 初始化数据
    protected abstract fun initData()

    // 子fragment实现懒加载的方法
    protected abstract fun loadData()

}