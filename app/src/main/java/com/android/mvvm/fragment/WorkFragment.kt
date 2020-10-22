package com.android.mvvm.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lib.Logger.e
import com.android.mvvm.MainActivity
import com.android.mvvm.R
import com.android.mvvm.activity.BaseRecyclerViewActivity
import com.android.mvvm.activity.HeadRecyclerViewActivity
import com.android.mvvm.activity.PermissionActivity
import com.android.mvvm.activity.PhotoActivity
import kotlinx.android.synthetic.main.common_head_layout.*
import kotlinx.android.synthetic.main.fragment_work.*

/**
 * date: 2020/9/21
 * desc: 工作
 */
class WorkFragment : BaseFragment(), View.OnClickListener {

    private var activity: MainActivity? = null

    companion object {
        private const val TAG = "WorkFragment"
        fun newInstance() = WorkFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_work, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        navigationBar.hideLeftLayout()
        navigationBar.setTitle("工作")
        baseRecyclerViewBtn.setOnClickListener(this)
        headRecyclerViewBtn.setOnClickListener(this)
        selectedImgBtn.setOnClickListener(this)
        permissionBtn.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        e(TAG, "onResume: ")
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.baseRecyclerViewBtn -> {
                BaseRecyclerViewActivity.actionStart(getMyActivity(), true)
            }
            R.id.headRecyclerViewBtn -> {
                HeadRecyclerViewActivity.actionStart(getMyActivity(), true)
            }
            R.id.selectedImgBtn -> {
                PhotoActivity.actionStart(getMyActivity(), true)
            }
            R.id.permissionBtn -> {
                PermissionActivity.actionStart(getMyActivity(), true)
            }
        }
    }

    private fun getMyActivity() = activity ?: getActivity() as MainActivity


}