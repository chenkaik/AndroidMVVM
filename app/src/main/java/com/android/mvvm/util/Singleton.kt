package com.android.mvvm.util

/**
 * date: 2021/4/19
 * desc: 静态内部类式
 */
class Singleton private constructor() {

    companion object {
        val instance = SingletonHolder.holder
    }

    private object SingletonHolder {
        val holder = Singleton()
    }

}