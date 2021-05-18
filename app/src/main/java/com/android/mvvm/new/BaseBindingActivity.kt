package com.android.mvvm.new

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


/**
 * date: 2021/5/1
 * desc:
 */
abstract class BaseBindingActivity<B : ViewDataBinding> : AppCompatActivity(), ViewBehavior {

    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDataBinding()
        init(savedInstanceState)
    }

    protected open fun injectDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
    }

    protected abstract @LayoutRes
    fun getLayoutId(): Int

    /**
     *  初始化操作
     */
    protected abstract fun init(savedInstanceState: Bundle?)


}