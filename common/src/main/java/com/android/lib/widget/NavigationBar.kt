package com.android.lib.widget

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.android.lib.R
import com.android.lib.util.ActivityCollector
import com.android.lib.util.FragmentManagerUtil.hideSoftInput

/**
 * date: 2019/1/30
 * desc: 公用的title头部
 */
class NavigationBar : FrameLayout {

    private lateinit var mLeftLinearLayout: LinearLayout
    private lateinit var mCenterLinearLayout: LinearLayout
    private lateinit var mRightLinearLayout: LinearLayout
    private lateinit var mBackButton: ImageView
    private lateinit var mTitleTextView: TextView
    private lateinit var mRoot: LinearLayout
    private lateinit var mLeftTextView: TextView

    constructor(context: Context) : super(context) {
        initComponent()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
        initComponent()
    }

    private fun initComponent() {
        mRoot = LayoutInflater.from(context).inflate(R.layout.navigation_bar, this, false) as LinearLayout
        mLeftLinearLayout =
            mRoot.findViewById<View>(R.id.leftLinearLayout) as LinearLayout
        mCenterLinearLayout =
            mRoot.findViewById<View>(R.id.centerLinearLayout) as LinearLayout
        mRightLinearLayout =
            mRoot.findViewById<View>(R.id.rightLinearLayout) as LinearLayout
        mTitleTextView = mRoot.findViewById<View>(R.id.titleTextView) as TextView
        mLeftTextView =
            mRoot.findViewById<View>(R.id.navigation_title_bar_back_view) as TextView
        mBackButton =
            mRoot.findViewById<View>(R.id.navigation_title_bar_back) as ImageView
        mLeftLinearLayout.setOnClickListener {
            backButtonOnClick()
        }
        addView(mRoot)
    }

    private fun backButtonOnClick() {
        if (context is FragmentActivity) {
            val activity = context as FragmentActivity
            if (ActivityCollector.goBlackPage() && activity.supportFragmentManager.backStackEntryCount == 0) {
                activity.finish()
            } else {
                activity.supportFragmentManager.popBackStack()
            }
        } else {
            (context as Activity).finish()
        }
        hideSoftInput(context as Activity)
    }

    fun setTitle(title: String) {
        mTitleTextView.text = title
    }

    fun addRightView(view: View) {
        mRightLinearLayout.addView(view)
    }

    fun clearRightViews() {
        mRightLinearLayout.removeAllViews()
    }

    fun addLeftView(view: View) {
        mLeftLinearLayout.addView(view)
    }

    fun clearLeftViews() {
        mLeftLinearLayout.removeAllViews()
    }

    fun showBackButton() {
        mBackButton.visibility = View.VISIBLE
    }

    fun hideLeftLayout() {
        mLeftLinearLayout.visibility = View.INVISIBLE
    }

    fun hideBackButton() {
        mBackButton.visibility = View.GONE
    }

    fun hideBackTextView() {
        mLeftTextView.visibility = View.GONE
    }

    val backBackButton: View?
        get() = mBackButton

    fun setNavigationBarBackgroundColor(color: Int) {
        mRoot.setBackgroundColor(color)
    }

    fun setBackButtonOnClickListener(onClickListener: OnClickListener?) {
        mBackButton.setOnClickListener(onClickListener)
    }

}