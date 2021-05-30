package com.android.mvvm.jetpack.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.mvvm.jetpack.api.ApiHelper
import com.android.mvvm.jetpack.repository.Repository
import com.android.mvvm.jetpack.viewmodel.TestViewModel

/**
 * date: 2021/5/29
 * desc: 传参数使用的ViewModelFactory
 */
@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TestViewModel::class.java)) {
            return TestViewModel(
                Repository(apiHelper)
            ) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}