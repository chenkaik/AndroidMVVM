package com.android.mvvm

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.android.mvvm.activity.BaseActivity
import com.android.mvvm.adapter.BaseFragmentAdapter
import com.android.mvvm.databinding.ActivityMainBinding
import com.android.mvvm.fragment.DataFragment
import com.android.mvvm.fragment.HomeFragment
import com.android.mvvm.fragment.MeFragment
import com.android.mvvm.fragment.WorkFragment
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : BaseActivity(), ViewPager.OnPageChangeListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var baseFragmentAdapter: BaseFragmentAdapter<Fragment>
    private lateinit var badgeView: TextView

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun getLayoutView(): View {
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        return activityMainBinding.root
    }

    override fun initView() {
        activityMainBinding.homeViewPager.addOnPageChangeListener(this)
        activityMainBinding.homeNavigationView.itemIconTintList = null // 不使用图标默认变色
        activityMainBinding.homeNavigationView.setOnNavigationItemSelectedListener(this)
//        activityMainBinding.homeNavigationView.setItemIconSize(55); // 设置菜单项图标的大小
//        activityMainBinding.homeNavigationView.menu.getItem(0).isChecked = true
        // 添加角标，先获取整个的BottomNavigationMenuView
        val menuView =
            activityMainBinding.homeNavigationView.getChildAt(0) as BottomNavigationMenuView
        // 获取所添加的Tab或者叫Menu
        val itemView = menuView.getChildAt(1) as BottomNavigationItemView
        // 加载角标View
        val badgeViewLayout =
            LayoutInflater.from(this).inflate(R.layout.tab_badge_layout, menuView, false)
        // 添加到Tab上
        itemView.addView(badgeViewLayout)
        badgeView = badgeViewLayout.findViewById(R.id.badge_number)
        badgeView.text = "10"
//        badgeView.visibility = View.GONE
    }

    fun setBadgeNumber(number: String) { // 设置角标数量
        badgeView.visibility = View.VISIBLE
        badgeView.text = number
    }

    fun setBadgeNumber(number: Int) { // 设置角标数量
        badgeView.visibility = View.VISIBLE
        badgeView.text = number.toString()
    }

    fun hideBadgeNumber() { // 隐藏角标数量
        badgeView.visibility = View.GONE
    }

    override fun initData() {
        baseFragmentAdapter = BaseFragmentAdapter(this)
        baseFragmentAdapter.addFragment(HomeFragment.newInstance())
        baseFragmentAdapter.addFragment(WorkFragment.newInstance())
        baseFragmentAdapter.addFragment(DataFragment.newInstance())
        baseFragmentAdapter.addFragment(MeFragment.newInstance())
        activityMainBinding.homeViewPager.adapter = baseFragmentAdapter
        activityMainBinding.homeViewPager.offscreenPageLimit = baseFragmentAdapter.count
        activityMainBinding.homeViewPager.currentItem = 0
    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        when (position) {
            0 -> activityMainBinding.homeNavigationView.selectedItemId = R.id.menu_home
            1 -> activityMainBinding.homeNavigationView.selectedItemId = R.id.home_work
            2 -> activityMainBinding.homeNavigationView.selectedItemId = R.id.home_data
            3 -> activityMainBinding.homeNavigationView.selectedItemId = R.id.home_me
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_home -> {
                // 如果切换的是相邻之间的 Item 就显示切换动画，如果不是则不要动画
                activityMainBinding.homeViewPager.setCurrentItem(
                    0,
                    activityMainBinding.homeViewPager.currentItem == 1
                )
                return true
            }
            R.id.home_work -> {
                activityMainBinding.homeViewPager.setCurrentItem(
                    1,
                    activityMainBinding.homeViewPager.currentItem == 0 || activityMainBinding.homeViewPager.currentItem == 2
                )
//                handleCurrentItemAnimator(item)
                return true
            }
            R.id.home_data -> {
                activityMainBinding.homeViewPager.setCurrentItem(
                    2,
                    activityMainBinding.homeViewPager.currentItem == 1 || activityMainBinding.homeViewPager.currentItem == 3
                )
//                handleCurrentItemAnimator(item)
                return true
            }
            R.id.home_me -> {
                activityMainBinding.homeViewPager.setCurrentItem(
                    3,
                    activityMainBinding.homeViewPager.currentItem == 2
                )
                return true
            }
        }
        return false
    }

    fun test(){
        activityMainBinding.homeViewPager.setCurrentItem(
            2,
            activityMainBinding.homeViewPager.currentItem == 1 || activityMainBinding.homeViewPager.currentItem == 3
        )
    }

//    private fun handleCurrentItemAnimator(item: MenuItem) {
//        val menuItemView =
//            findViewById<BottomNavigationItemView>(homeNavigationView.menu.findItem(item.itemId).itemId)
//        val menuIconView = menuItemView.findViewById<ImageView>(R.id.icon)
//        handleWithAnimatorSet(
//            handleBottomNavScaleAnimator(menuIconView, "T_SCALE_X"),
//            handleBottomNavScaleAnimator(menuIconView, "T_SCALE_Y"),
//            400
//        )
//    }
//
//    private fun handleBottomNavScaleAnimator(
//        viewTarget: View,
//        propertyName: String
//    ): ObjectAnimator {
//        return ObjectAnimator.ofFloat(viewTarget, propertyName, 1f, 1.1f, 0.9f, 1f)
//    }
//
//    private fun handleWithAnimatorSet(
//        playAnimator: ObjectAnimator,
//        afterAnimator: ObjectAnimator,
//        duration: Long
//    ) {
//        val animSet = AnimatorSet()
//        animSet.play(playAnimator)
//            .with(afterAnimator)
//        animSet.duration = duration
//        animSet.start()
//    }

    override fun onDestroy() {
        activityMainBinding.homeViewPager.removeOnPageChangeListener(this)
        activityMainBinding.homeViewPager.adapter = null
        activityMainBinding.homeNavigationView.setOnNavigationItemSelectedListener(null)
        super.onDestroy()
    }

}
