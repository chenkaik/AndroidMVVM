package com.android.mvvm

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.android.mvvm.activity.BaseActivity
import com.android.mvvm.adapter.BaseFragmentAdapter
import com.android.mvvm.fragment.DataFragment
import com.android.mvvm.fragment.HomeFragment
import com.android.mvvm.fragment.MeFragment
import com.android.mvvm.fragment.WorkFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), ViewPager.OnPageChangeListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var mPageAdapter: BaseFragmentAdapter<Fragment>
    private lateinit var mBadgeView: TextView

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun getLayoutId() = R.layout.activity_main

    override fun initView() {
        homeViewPager.addOnPageChangeListener(this)
        homeNavigationView.itemIconTintList = null // 不使用图标默认变色
        homeNavigationView.setOnNavigationItemSelectedListener(this)
//        homeNavigationView.setItemIconSize(55); // 设置菜单项图标的大小
//        homeNavigationView.menu.getItem(0).isChecked = true
        // 添加角标，先获取整个的BottomNavigationMenuView
        val menuView = homeNavigationView.getChildAt(0) as BottomNavigationMenuView
        // 获取所添加的Tab或者叫Menu
        val itemView = menuView.getChildAt(1) as BottomNavigationItemView
        // 加载角标View
        val badgeView =
            LayoutInflater.from(this).inflate(R.layout.tab_badge_layout, menuView, false)
        // 添加到Tab上
        itemView.addView(badgeView)
        mBadgeView = badgeView.findViewById(R.id.badge_number)
        mBadgeView.text = "10"
//        mBadgeView.visibility = View.GONE
    }

    fun setBadgeNumber(number: String) { // 设置角标数量
        mBadgeView.visibility = View.VISIBLE
        mBadgeView.text = number
    }

    fun setBadgeNumber(number: Int) { // 设置角标数量
        mBadgeView.visibility = View.VISIBLE
        mBadgeView.text = number.toString()
    }

    fun hideBadgeNumber() { // 隐藏角标数量
        mBadgeView.visibility = View.GONE
    }

    override fun initData() {
        mPageAdapter = BaseFragmentAdapter(this)
        mPageAdapter.addFragment(HomeFragment.newInstance())
        mPageAdapter.addFragment(WorkFragment.newInstance())
        mPageAdapter.addFragment(DataFragment.newInstance())
        mPageAdapter.addFragment(MeFragment.newInstance())
        homeViewPager.adapter = mPageAdapter
        homeViewPager.offscreenPageLimit = mPageAdapter.count
        homeViewPager.currentItem = 0
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        when (position) {
            0 -> homeNavigationView.selectedItemId = R.id.menu_home
            1 -> homeNavigationView.selectedItemId = R.id.home_work
            2 -> homeNavigationView.selectedItemId = R.id.home_data
            3 -> homeNavigationView.selectedItemId = R.id.home_me
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_home -> {
                // 如果切换的是相邻之间的 Item 就显示切换动画，如果不是则不要动画
                homeViewPager.setCurrentItem(0, homeViewPager.currentItem == 1)
                return true
            }
            R.id.home_work -> {
                homeViewPager.setCurrentItem(
                    1,
                    homeViewPager.currentItem == 0 || homeViewPager.currentItem == 2
                )
                handleCurrentItemAnimator(item)
                return true
            }
            R.id.home_data -> {
                homeViewPager.setCurrentItem(
                    2,
                    homeViewPager.currentItem == 1 || homeViewPager.currentItem == 3
                )
                handleCurrentItemAnimator(item)
                return true
            }
            R.id.home_me -> {
                homeViewPager.setCurrentItem(3, homeViewPager.currentItem == 2)
                return true
            }
        }
        return false
    }

    private fun handleCurrentItemAnimator(item: MenuItem){
        val menuItemView = findViewById<BottomNavigationItemView>(homeNavigationView.menu.findItem(item.itemId).itemId)
        val menuIconView = menuItemView.findViewById<ImageView>(R.id.icon)
        handleWithAnimatorSet(
            handleBottomNavScaleAnimator(menuIconView, "T_SCALE_X"),
            handleBottomNavScaleAnimator(menuIconView, "T_SCALE_Y"),
            400
        )
    }

    private fun handleBottomNavScaleAnimator(viewTarget: View, propertyName: String): ObjectAnimator {
        return ObjectAnimator.ofFloat(viewTarget, propertyName, 1f, 1.1f, 0.9f, 1f)
    }

    private fun handleWithAnimatorSet(
        playAnimator: ObjectAnimator,
        afterAnimator: ObjectAnimator,
        duration: Long
    ) {
        val animSet = AnimatorSet()
        animSet.play(playAnimator)
            .with(afterAnimator)
        animSet.duration = duration
        animSet.start()
    }


    override fun onDestroy() {
        homeViewPager.removeOnPageChangeListener(this)
        homeViewPager.adapter = null
        homeNavigationView.setOnNavigationItemSelectedListener(null)
        super.onDestroy()
    }


}
