package com.android.mvvm.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lib.Logger.e
import com.android.mvvm.MainActivity
import com.android.mvvm.R
import com.android.mvvm.databinding.FragmentMeBinding

/**
 * date: 2020/9/21
 * desc: 我的
 */
class MeFragment : BaseFragment() {

    private var activity: MainActivity? = null

    companion object {
        private const val TAG = "MeFragment"
        fun newInstance() = MeFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity
    }

    override fun getLayoutView(inflater: LayoutInflater, container: ViewGroup?): View {
        val fragmentMeBinding = FragmentMeBinding.inflate(inflater, container, false)
        return fragmentMeBinding.root
    }

    override fun initView() {
    }

    override fun initData() {
    }

    override fun onResume() {
        super.onResume()
        e(TAG, "onResume: ")
    }

    private fun getMyActivity() = activity ?: getActivity() as MainActivity

}