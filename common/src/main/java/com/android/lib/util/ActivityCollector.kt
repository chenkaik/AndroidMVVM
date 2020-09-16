package com.android.lib.util

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import java.util.*
import kotlin.system.exitProcess

/**
 * date: 2020/7/12
 * desc: 管理activity
 */
object ActivityCollector {

    private val activities = ArrayList<Activity>()

    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    fun finishAll() {
        for (activity in activities) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
        activities.clear()
    }


    private val activityStack = Stack<Activity>()

    /**
     * 获取栈顶Activity（堆栈中最后一个压入的）
     */
    val topActivity: Activity?
        get() = activityStack.lastElement()

    /**
     * 结束栈顶Activity（堆栈中最后一个压入的）
     */
    fun killTopActivity() {
        val activity = activityStack.lastElement()
        killActivity(activity)
    }

    /**
     * 结束指定的Activity
     */
    fun killActivity(activity: Activity?) {
        if (activity != null) {
            activityStack.remove(activity)
            activity.finish()
        }
    }

    /**
     * 结束指定类名的Activity
     */
    fun killActivity(cls: Class<*>) {
        for (activity in activityStack) {
            if (activity.javaClass == cls) {
                killActivity(activity)
            }
        }
    }

    /**
     * 结束所有栈中的Activity
     */
    fun killAllActivity() {
        var i = 0
        val size = activityStack.size
        while (i < size) {
            if (null != activityStack[i]) {
                activityStack[i]!!.finish()
            }
            i++
        }
        activityStack.clear()
    }

    /**
     * 退出应用程序
     */
    fun AppExit(context: Context) {
        try {
            killAllActivity()
            val activityMgr =
                context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            activityMgr.restartPackage(context.packageName)
            exitProcess(0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 弹出栈并finish activity
     */
    fun popActivity() {
        val activity = activityStack.pop()
        activity?.finish()
    }

    /**
     * 只弹出栈但不finish
     */
    fun popActivityNoFinish() {
        val size = size
        if (size > 0) {
            activityStack.pop()
        }
    }

    /**
     * 弹出直到是cls类型的，并finish
     *
     * @param cls class
     */
    fun popAllActivityExceptOne(cls: Class<*>) {
        while (true) {
            val activity = activityStack.pop() ?: break
            if (activity.javaClass == cls) {
                activityStack.remove(activity)
                activity.finish()
                break
            }
        }
    }

    /**
     * 弹出直到是cls类型的,但并不finish
     *
     * @param cls class
     */
    fun popAllNoFinishExceptOne(cls: Class<*>) {
        while (true) {
            val activity = currentActivity() ?: break
            if (activity.javaClass == cls) {
                break
            }
        }
    }

    /**
     * 清除并finish释放内存
     */
    fun clearAllActivity() {
        var size = size
        var isEmpty = activityStack.isEmpty()
        while (size > 0 && !isEmpty) {
            popActivity()
            isEmpty = activityStack.isEmpty()
            size--
        }
        activityStack.clear()
    }

    /**
     * 清除栈中的内容，但并不finish释放内存
     */
    fun clearAllNoFinish() {
        var size = size
        var isEmpty = activityStack.isEmpty()
        while (size > 0 && !isEmpty) {
            popActivityNoFinish()
            isEmpty = activityStack.isEmpty()
            size--
        }
        activityStack.clear()
    }

    /**
     * 入栈
     *
     * @param activity act
     */
    fun pushActivity(activity: Activity?) {
        activityStack.push(activity)
    }

    /**
     * 获得最近的activity
     */
    fun currentActivity(): Activity? {
        return activityStack.lastElement()
    }

    /**
     * 栈的大小
     *
     * @return size
     */
    val size: Int
        get() = activityStack.size

    /**
     * 获得第几个元素
     *
     * @param i index
     * @return act
     */
    fun getElement(i: Int): Activity {
        return activityStack.elementAt(i)!!
    }

    /**
     * 界面跳转
     *
     * @param activity   当前页面
     * @param intent     目标页面
     * @param isPutStack 启动下个是否压入栈中
     */
    fun startPage(activity: Activity?, intent: Intent?, isPutStack: Boolean) {
        if (activity == null || intent == null) {
            return
        }
        activity.startActivity(intent)
        if (isPutStack) {
            pushActivity(activity)
        } else {
            activity.finish()
        }
    }

    /**
     * 界面跳转
     *
     * @param activity    当前页面
     * @param intent      目标页面
     * @param isPutStack  启动下个是否压入栈中
     * @param requestCode 请求code
     */
    fun startPage(
        activity: Activity?,
        intent: Intent?,
        isPutStack: Boolean,
        requestCode: Int
    ) {
        if (activity == null || intent == null) {
            return
        }
        activity.startActivityForResult(intent, requestCode)
        if (isPutStack) {
            pushActivity(activity)
        } else {
            activity.finish()
        }
    }

    /**
     * 返回上个界面
     *
     * @return 栈中是否有界面, 有, 返回true; 没有,返回false
     */
    fun goBlackPage(): Boolean {
        var b = false
        val size = size
        var isEmpty = true
        isEmpty = activityStack.isEmpty()
        if (size > 0 && !isEmpty) {
            popActivityNoFinish()
            b = true
        }
        return b
    }

}