package com.android.mvvm.new

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * date: 2021/5/1
 * desc: Obserser相关的扩展
 */
/**
 * 简化LiveData的订阅操作，参数可为null
 */
fun <T> LiveData<T>.observeNullable(owner: LifecycleOwner, block: (T) -> Unit) {
    this.observe(owner, Observer {
        block(it)
    })
}

/**
 * 简化LiveData的订阅操作，参数不为null
 */
fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, block: (T) -> Unit) {
    this.observe(owner, Observer {
        if (it != null) {
            block(it)
        }
    })
}