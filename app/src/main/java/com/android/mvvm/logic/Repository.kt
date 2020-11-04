package com.android.mvvm.logic

import androidx.lifecycle.liveData
import com.android.mvvm.logic.model.Login
import com.android.mvvm.logic.network.CommonNetwork
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

/**
 * date: 2020/11/4
 * desc: 仓库层
 */
object Repository {

    fun login(login: Login) = fire(Dispatchers.IO) {
        val loginResponse = CommonNetwork.login(login)
        if (loginResponse.SYS_HEAD?.RET?.RET_CODE == "000000") {
            Result.success(loginResponse)
        } else {
            Result.failure(RuntimeException("response status is ${loginResponse.SYS_HEAD?.RET?.RET_CODE}"))
        }
    }

    /**
     * suspend 能将一个函数声明成挂起函数
     * liveData()函数可以自动构建并返回一个LiveData对象 在代码块中提供一个挂起函数的上下文 可以调用任意的挂起函数
     */
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {
            val result = try {
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result) // 结果发射出去 类似LiveData的setValue()方法来通知数据变化
        }

}