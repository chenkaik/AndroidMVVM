package com.android.mvvm.new

/**
 * date: 2021/5/1
 * desc: 定义常用的UI操作
 */
interface ViewBehavior {

    /**
     * 是否显示Loading视图
     */
    fun showLoadingUI(isShow: Boolean)

    /**
     * 是否显示空白视图
     */
    fun showEmptyUI(isShow: Boolean)

    /**
     * 弹出Toast提示
     */
    fun showToast(map: Map<String, *>)

    /**
     * 不带参数的页面跳转
     */
    fun navigate(page: Any)

    /**
     * 返回键点击
     */
    fun backPress(arg: Any?);

    /**
     * 关闭页面
     */
    fun finishPage(arg: Any?)

}