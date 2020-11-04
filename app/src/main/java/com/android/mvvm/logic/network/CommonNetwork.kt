package com.android.mvvm.logic.network

import com.android.mvvm.logic.model.Login
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * date: 2020/11/4
 * desc: 定义统一访问网络数据源入口
 */
object CommonNetwork {

    private val commonService = ServiceCreator.create<CommonService>()

    // suspend 能将一个函数声明成挂起函数
    suspend fun login(login: Login) = commonService.login(login).await() // await()函数会将当前协程阻塞住

    /**
     * suspend 声明为挂起函数
     * suspendCoroutine函数必须在协程作用域或挂起函数中才能调用 接收一个Lambda表达式参数 主要是将当前协程立即挂起
     * 然后在一个普通线程中执行Lambda表达式中的代码 表达式参数列表会传入一个Continuation参数 调用它的resume()方法或
     * resumeWithException()方法可以让协程继续(huifu)执行
     */
    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine {
            enqueue(object : Callback<T> {

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        it.resume(body)
                    } else {
                        it.resumeWithException(RuntimeException("response body is null"))
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    it.resumeWithException(t)
                }

            })
        }
    }

}