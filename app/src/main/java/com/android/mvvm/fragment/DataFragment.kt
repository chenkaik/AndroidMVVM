package com.android.mvvm.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lib.Logger.e
import com.android.mvvm.MainActivity
import com.android.mvvm.R
import com.android.mvvm.databinding.FragmentDataBinding
import com.android.mvvm.util.showToast

/**
 * date: 2020/9/21
 * desc: 数据
 */
class DataFragment : BaseFragment() {

    private var activity: MainActivity? = null

    companion object {
        private const val TAG = "DataFragment"
        fun newInstance() = DataFragment()
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
        val fragmentDataBinding = FragmentDataBinding.inflate(inflater, container, false)
        return fragmentDataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        e(TAG, "onActivityCreated:")
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun loadData() {
        "数据第一次加载".showToast()
    }

    override fun onStart() {
        super.onStart()
        e(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        e(TAG, "onResume: ")
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