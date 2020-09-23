package com.android.mvvm.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lib.Logger.e
import com.android.mvvm.MainActivity
import com.android.mvvm.R

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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_me, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        e(TAG, "onResume: ")
    }

    private fun getMyActivity() = activity ?: getActivity() as MainActivity

}