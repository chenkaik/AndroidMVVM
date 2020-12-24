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
import com.android.mvvm.databinding.CommonHeadLayoutBinding
import com.android.mvvm.databinding.FragmentWorkBinding
import com.android.mvvm.ui.LoginActivity
import com.android.mvvm.util.showToast

/**
 * date: 2020/9/21
 * desc: 工作
 */
class WorkFragment : BaseFragment(), View.OnClickListener {

    private var activity: MainActivity? = null
    private lateinit var fragmentWorkBinding: FragmentWorkBinding
    private lateinit var commonHeadLayoutBinding: CommonHeadLayoutBinding

    companion object {
        private const val TAG = "WorkFragment"
        fun newInstance() = WorkFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity
        e(TAG, "onAttach:")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        e(TAG, "onCreate:")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        e(TAG, "onCreateView:")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun getLayoutView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentWorkBinding = FragmentWorkBinding.inflate(inflater, container, false)
        commonHeadLayoutBinding = fragmentWorkBinding.commonHead
        return fragmentWorkBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        e(TAG, "onActivityCreated:")
    }

    override fun initView() {
        commonHeadLayoutBinding.navigationBar.hideLeftLayout()
        commonHeadLayoutBinding.navigationBar.setTitle("工作")
        fragmentWorkBinding.baseRecyclerViewBtn.setOnClickListener(this)
        fragmentWorkBinding.headRecyclerViewBtn.setOnClickListener(this)
        fragmentWorkBinding.selectedImgBtn.setOnClickListener(this)
        fragmentWorkBinding.permissionBtn.setOnClickListener(this)
        fragmentWorkBinding.loginBtn.setOnClickListener(this)
    }

    override fun initData() {

    }

    override fun loadData() {
        "工作第一次加载".showToast()
    }

    override fun onStart() {
        super.onStart()
        e(TAG, "onStart: ")
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
            R.id.loginBtn -> {
                LoginActivity.actionStart(getMyActivity(), true)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        e(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        e(TAG, "onStop: ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        e(TAG, "onDestroyView: ")
    }

    override fun onDestroy() {
        super.onDestroy()
        e(TAG, "onDestroy: ")
    }

    private fun getMyActivity() = activity ?: getActivity() as MainActivity


}