package com.android.mvvm.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

/**
 * date: 2019/3/8
 * desc: FragmentPagerAdapter 基类
 */
@Suppress("UNCHECKED_CAST")
class BaseFragmentAdapter<F : Fragment> : FragmentPagerAdapter {

    private val mFragmentSet: MutableList<F> = ArrayList() // Fragment集合

    /**
     * 获取当前的Fragment
     */
    private var currentFragment // 当前显示的Fragment
            : F? = null

    constructor(activity: FragmentActivity) : this(
        activity.supportFragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    )

    private constructor(fm: FragmentManager, behavior: Int) : super(
        fm,
        behavior
    )

//    constructor(fragment: Fragment) : this(fragment.childFragmentManager)
//
//    constructor(manager: FragmentManager?) : super(manager!!)



    override fun getItem(position: Int): Fragment {
        return mFragmentSet[position]
    }

    override fun getCount(): Int {
        return mFragmentSet.size
    }

    override fun setPrimaryItem(
        container: ViewGroup,
        position: Int,
        obj: Any
    ) { // 当前页面的主Item
        if (currentFragment !== obj) { // 记录当前的Fragment对象
            currentFragment = obj as F
        }
        super.setPrimaryItem(container, position, obj)
    }

    fun addFragment(fragment: F) {
        mFragmentSet.add(fragment)
    }

    /**
     * 获取Fragment集合
     */
    val allFragment: List<F>get() = mFragmentSet

}