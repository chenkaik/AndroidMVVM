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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return getLayoutView(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }


    // 引入布局
    protected abstract fun getLayoutView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): View

    // 初始化控件
    protected abstract fun initView()

    // 初始化数据
    protected abstract fun initData()

}