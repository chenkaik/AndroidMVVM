package com.android.mvvm.new

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider

/**
 * date: 2021/5/1
 * desc:
 */
abstract class BaseBVMActivity<B : ViewDataBinding, VM : BaseViewModel> : BaseBindingActivity<B>(),
    ViewBehavior {

    protected lateinit var viewModel: VM

    override fun init(savedInstanceState: Bundle?) {
        injectViewModel()
        initialize(savedInstanceState)
        initInternalObserver()
    }

    protected fun injectViewModel() {
        val vm = createViewModel()
        viewModel = ViewModelProvider(this, BaseViewModel.createViewModelFactory(vm))
            .get(vm::class.java)
        viewModel.application = application
        lifecycle.addObserver(viewModel)
    }

    fun getActivityViewModel(): VM {
        return viewModel
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.unbind()
        lifecycle.removeObserver(viewModel)
    }

    protected fun initInternalObserver() {
        viewModel._loadingEvent.observeNonNull(this, {
            showLoadingUI(it)
        })
        viewModel._emptyPageEvent.observeNonNull(this, {
            showEmptyUI(it)
        })
        viewModel._toastEvent.observeNonNull(this, {
            showToast(it)
        })
        viewModel._pageNavigationEvent.observeNonNull(this, {
            navigate(it)
        })
        viewModel._backPressEvent.observeNullable(this, {
            backPress(it)
        })
        viewModel._finishPageEvent.observeNullable(this, {
            finishPage(it)
        })
    }

    protected abstract fun createViewModel(): VM

    /**
     *  初始化操作
     */
    protected abstract fun initialize(savedInstanceState: Bundle?)


}