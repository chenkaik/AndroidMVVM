package com.android.mvvm.https.callback

import com.android.lib.Logger.e
import com.android.lib.util.GsonUtil.fromJson
import com.android.mvvm.https.NetWorkManager
import com.android.mvvm.https.config.HttpConfig
import com.android.mvvm.https.response.BaseResponse
import com.android.mvvm.https.response.OkHttpResponse
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

/**
 * date: 2019/2/13
 * desc: okHttp 回调
 */
class OkHttpCallback(
    private val mRequestCode: Int,
    private val mOkHttpResponse: OkHttpResponse
) : Callback {

    companion object {
        private const val TAG = "OkHttpCallback"
    }

    private var mResponseBodyStr = ""

    override fun onFailure(call: Call, e: IOException) {
        e("onFailure: ", "警告,调用接口出错!", e)
        NetWorkManager.mHandler.post {
            mOkHttpResponse.onDataFailure(
                mRequestCode,
                400,
                "系统异常(400),请联系管理员!",
                false
            )
        }
    }

    override fun onResponse(call: Call, response: Response) {
        e(TAG, "ResponseCode: " + response.code)
        if (response.isSuccessful) { // code >= 200 && code < 300;
            val responseBody = response.body
            try {
                if (responseBody != null) {
                    mResponseBodyStr = responseBody.string()
                    e(
                        TAG,
                        "onResponse: $mResponseBodyStr"
                    )
                    val baseResponse = fromJson(mResponseBodyStr, BaseResponse::class.java)
                    if (baseResponse != null) {
                        when (baseResponse.SYS_HEAD?.RET?.RET_CODE) {
                            HttpConfig.SUCCESS -> {
                                NetWorkManager.mHandler.post {
                                    mOkHttpResponse.onDataSuccess(
                                        mRequestCode,
                                        null,
                                        mResponseBodyStr
                                    )
                                }
                            }
                            HttpConfig.TOKENERROR -> {
                                NetWorkManager.mHandler.post {
                                    mOkHttpResponse.onDataFailure(
                                        mRequestCode,
                                        0,
                                        "登录失效!",
                                        true
                                    )
                                }
                            }
                            else -> {
                                NetWorkManager.mHandler.post {
                                    mOkHttpResponse.onDataFailure(
                                        mRequestCode,
                                        0,
                                        baseResponse.SYS_HEAD?.RET?.RET_CODE + " " + baseResponse.SYS_HEAD?.RET?.RET_MSG,
                                        false
                                    )
                                }
                            }
                        }
                    } else {
                        NetWorkManager.mHandler.post {
                            mOkHttpResponse.onDataFailure(
                                mRequestCode,
                                0,
                                "数据解析有误!",
                                false
                            )
                        }
                    }
                } else {
                    NetWorkManager.mHandler.post {
                        mOkHttpResponse.onDataFailure(
                            mRequestCode,
                            0,
                            "服务响应异常!",
                            false
                        )
                    }
                }
            } catch (e: Exception) {
                e(
                    "onResponse",
                    "onResponse json fail status=" + response.code
                )
                NetWorkManager.mHandler.post {
                    mOkHttpResponse.onDataFailure(
                        mRequestCode,
                        0,
                        "数据有误!",
                        false
                    )
                }
            } finally {
                responseBody?.close()
            }
        } else {
            e("onResponse", "onResponse fail status=" + response.code)
            NetWorkManager.mHandler.post {
                mOkHttpResponse.onDataFailure(
                    mRequestCode,
                    500,
                    "系统异常(500),请联系管理员!",
                    false
                )
            }
        }
    }

}