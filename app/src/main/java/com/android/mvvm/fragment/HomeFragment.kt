package com.android.mvvm.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.lib.Logger.e
import com.android.lib.banner.RecyclerViewBannerBaseView
import com.android.lib.util.ActivityCollector
import com.android.lib.util.InputTextHelper
import com.android.mvvm.MainActivity
import com.android.mvvm.R
import com.android.mvvm.activity.HttpActivity
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * date: 2020/9/21
 * desc: 首页
 */
class HomeFragment : BaseFragment() {

    private var activity: MainActivity? = null
    private lateinit var inputTextHelper: InputTextHelper

    companion object {
        private const val TAG = "HomeFragment"
        fun newInstance() = HomeFragment()
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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewRes: MutableList<String> = ArrayList()
        viewRes.add("http://www.3goodsoft.net/sys/static/sanhao/h5/img/banner3.png")
        viewRes.add("http://www.3goodsoft.net/sys/static/sanhao/h5/img/banner1.png?v=1")
        viewRes.add("http://www.3goodsoft.net/sys/static/sanhao/h5/img/banner0.png")
        viewRes.add("http://www.3goodsoft.net/sys/static/sanhao/h5/img/banner4.png")
        banner.initBannerImageView(viewRes,
            object : RecyclerViewBannerBaseView.OnBannerItemClickListener {
                override fun onItemClick(position: Int) {
                    Toast.makeText(getMyActivity(), position.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        banner.setPlaying(true)
//        startActivity<MainActivity>(this)
//        if ("hello" beginsWith "he"){
//
//        }
//        "t".showToast(this)
//        R.string.app_name.showToast(this)
//        val dialog = CommonDialog(getMyActivity())
        inputTextHelper = InputTextHelper(button1)
        inputTextHelper.addViews(editText1, editText2)
        button1.setOnClickListener {
            //            dialog.showProgress("hh")
//            dialog.showAlertDialog("提示","这是内容", View.OnClickListener {
//                "确定".showToast()
//            }, View.OnClickListener {
//                "取消".showToast()
//            })
//            dialog.showOneAlertDialog("提示1","这是内容1",View.OnClickListener {
//                "确定了".showToast()
//            }


        }
        http.setOnClickListener {
            val intent = Intent(getMyActivity(), HttpActivity::class.java)
            ActivityCollector.startPage(getMyActivity(), intent, true)
        }

        button3.setOnClickListener {

        }

    }

    override fun onResume() {
        super.onResume()
        e(TAG, "onResume: ")
    }

    override fun onDestroy() {
        inputTextHelper.removeViews()
        super.onDestroy()
    }

    private fun getMyActivity() = activity ?: getActivity() as MainActivity


}