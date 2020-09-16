package com.android.lib.util

import android.content.Context
import android.content.SharedPreferences

/**
 * date: 2019/1/30.
 * desc: 保存用户信息
 * SharedPreference 相关修改使用 apply 方法进行提交会先写入内存，然后异步写入
 * 磁盘，commit 方法是直接写入磁盘。如果频繁操作的话 apply 的性能会优于 commit，
 * apply 会将最后修改内容写入磁盘。但是如果希望立刻获取存储操作的结果，并据此
 * 做相应的其他操作，应当使用 commit
 */
open class SharedPreferencesUtil(context: Context) {

    /**
     *  by lazy代码块 懒加载技术 第一次调用才会执行 最后一行代码作为返回值
     */
    private val sharedPreferences by lazy {
        context.getSharedPreferences("user_config", Context.MODE_PRIVATE)
    }

    // internal 同一模块中的类可见
    internal interface Executable {
        fun execute(editor: SharedPreferences.Editor)
    }

    private fun executeWithEditor(ex: Executable): Boolean {
        val editor = sharedPreferences.edit()
        ex.execute(editor)
        return editor.commit()
    }

    fun put(key: String, value: Boolean): Boolean {
        return executeWithEditor(object : Executable {
            override fun execute(editor: SharedPreferences.Editor) {
                editor.putBoolean(
                    key,
                    value
                )
            }
        })
    }

    fun put(key: String, value: Int): Boolean {
        return executeWithEditor(object : Executable {
            override fun execute(editor: SharedPreferences.Editor) {
                editor.putInt(
                    key,
                    value
                )
            }
        })
    }

    fun put(key: String, value: Long): Boolean {
        return executeWithEditor(object : Executable {
            override fun execute(editor: SharedPreferences.Editor) {
                editor.putLong(
                    key,
                    value
                )
            }
        })
    }

    fun put(key: String, value: Float): Boolean {
        return executeWithEditor(object : Executable {
            override fun execute(editor: SharedPreferences.Editor) {
                editor.putFloat(
                    key,
                    value
                )
            }
        })
    }

    fun put(key: String, value: String?): Boolean {
        return executeWithEditor(object : Executable {
            override fun execute(editor: SharedPreferences.Editor) {
                editor.putString(
                    key,
                    value
                )
            }
        })
    }

    fun remove(key: String) {
        executeWithEditor(object : Executable {
            override fun execute(editor: SharedPreferences.Editor) {
                editor.remove(
                    key
                )
            }
        })
    }

    fun clear() {
        executeWithEditor(object : Executable {
            override fun execute(editor: SharedPreferences.Editor) {
                editor.clear()
            }
        })
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }

    fun getString(key: String, defaultValue: String): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

}