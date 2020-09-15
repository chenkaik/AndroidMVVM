package com.android.lib.util

import android.app.Activity
import android.content.Context
import android.os.IBinder
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.android.lib.R

/**
 * date: 2019/1/30
 * desc: 管理fragment
 */
object FragmentManagerUtil {

    private val tag = FragmentManagerUtil::class.java.simpleName

    /**
     * @param fm        FragmentManager
     * @param resId     Fragment要显示的view空间id
     * @param fragment1 当前的Fragment，如果没有那么传入null
     * @param fragment2 要跳转的下一个Fragment
     */
    @JvmOverloads
    fun add(
        fm: FragmentManager,
        resId: Int,
        fragment1: Fragment?,
        fragment2: Fragment,
        hideSoftInput: Boolean = true
    ) {
        val transaction = fm.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.push_left_in,
            R.anim.push_left_out,
            R.anim.push_right_in,
            R.anim.push_right_out
        )
        transaction.add(resId, fragment2, fragment2.javaClass.simpleName)
        if (null != fragment1) {
            transaction.hide(fragment1)
            transaction.addToBackStack(null)
            if (hideSoftInput) {
                hideSoftInput(fragment1.activity)
            }
        }
        transaction.commit()
    }

    fun hideSoftInput(activity: Activity?) {
        val iBinder = getWindowToken(activity)
        if (null != iBinder) {
            val imm =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(iBinder, 0)
        }
    }

    fun showSoftInput(activity: Activity) {
        val view = activity.currentFocus
        if (null != view) {
            val imm =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(view, 0)
        }
    }

    private fun getWindowToken(activity: Activity?): IBinder? {
        if (null == activity) {
            return null
        }
        val view = activity.currentFocus ?: return null
        return view.windowToken
    }

    @JvmOverloads
    fun replace(
        fm: FragmentManager,
        resId: Int,
        fragment: Fragment,
        isBack: Boolean = false
    ) {
        val transaction = fm.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.push_left_in,
            R.anim.push_left_out,
            R.anim.push_right_in,
            R.anim.push_right_out
        )
        transaction.replace(resId, fragment, fragment.javaClass.simpleName)
        if (isBack) {
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    fun addNoAnimation(
        fm: FragmentManager,
        resId: Int,
        fragment1: Fragment?,
        fragment2: Fragment,
        hideSoftInput: Boolean
    ) {
        val transaction = fm.beginTransaction()
        if (null != fragment1) {
            transaction.hide(fragment1)
            transaction.addToBackStack(null)
            if (hideSoftInput) {
                hideSoftInput(fragment1.activity)
            }
        }
        transaction.add(resId, fragment2, fragment2.javaClass.simpleName)
        transaction.commit()
    }

    fun addNoAnimation(
        fm: FragmentManager,
        resId: Int,
        fragment2: Fragment
    ) {
        val transaction = fm.beginTransaction()
        transaction.add(resId, fragment2, fragment2.javaClass.simpleName)
        transaction.commit()
    }

    fun removeAdd(
        fm: FragmentManager,
        resId: Int,
        fragment1: Fragment?,
        fragment2: Fragment
    ) {
        val transaction = fm.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.push_left_in,
            R.anim.push_left_out,
            R.anim.push_right_in,
            R.anim.push_right_out
        )
        transaction.add(resId, fragment2, fragment2.javaClass.simpleName)
        if (null != fragment1) {
            transaction.remove(fragment1)
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    fun replaceNoAnimation(
        fm: FragmentManager,
        resId: Int,
        fragment: Fragment
    ) {
        val transaction = fm.beginTransaction()
        transaction.replace(resId, fragment, fragment.javaClass.simpleName)
        transaction.commit()
    }
}