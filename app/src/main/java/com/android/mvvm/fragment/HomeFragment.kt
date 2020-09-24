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
import com.android.mvvm.entity.LoginResponse
import com.android.mvvm.https.NetWorkManager
import com.android.mvvm.https.response.BaseResponse
import com.android.mvvm.https.response.OkHttpResponse
import com.android.mvvm.https.response.RetrofitResponse
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONObject

/**
 * date: 2020/9/21
 * desc: 首页
 */
class HomeFragment : BaseFragment(), OkHttpResponse, RetrofitResponse {

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

            val map = HashMap<String, String>()
            map["username"] = "lixiangbin"
            map["password"] = "shjacf"
            NetWorkManager.instance
                .post(true)
                .url("https://www.shjacf.com/server/login")
                .jsonParams(JSONObject(map as Map<*, *>).toString())
                .tag(this)
                .enqueue(1, this)
        }
        http.setOnClickListener {
            //            NetWorkManager.instance.asyncNetWork(
//                TAG, 1, ApiManager
//                    .service()
//                    .login(LoginRequest("shjacf", "lixiangbin")), this, true
//            )
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

    override fun onDataSuccess(requestCode: Int, obj: Any?, json: String) {
        e(TAG, json)
    }

    override fun onDataFailure(
        requestCode: Int,
        responseCode: Int,
        message: String?,
        isOverdue: Boolean
    ) {
    }

    override fun onDataReady(response: BaseResponse) {
        if (response.requestCode == 1) {
            val res = response as LoginResponse
            e(TAG, res.MSG_BODY.userName)
            e(TAG, res.MSG_BODY.token)
        }
    }

    override fun onDataError(
        requestCode: Int,
        responseCode: Int,
        message: String?,
        isOverdue: Boolean
    ) {

    }

    override fun showLoading(msg: String?) {

    }

    override fun dismissLoading() {

    }

}