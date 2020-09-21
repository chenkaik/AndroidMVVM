package com.android.mvvm.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * date: 2020/9/21
 * desc:
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initView()
        initData()
    }

    // 引入布局
    protected abstract fun getLayoutId(): Int

    // 初始化控件
    protected abstract fun initView()

    // 初始化数据
    protected abstract fun initData()

}