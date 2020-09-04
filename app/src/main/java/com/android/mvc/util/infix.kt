package com.android.mvc.util

/**
 * date: 2020/8/2
 * desc: infix函数不能定义成顶层函数，必须是某个类的成员函数且函数只能接收一个参数(参数类型没有限制)
 * 可以使用扩展函数的方式将它定义到某个类当中
 */
// if("Hello Kotlin" beginsWith "Hello"){}
infix fun String.beginsWith(prefix: String) = startsWith(prefix)

// if(list has "Banana"){}
infix fun <T> Collection<T>.has(element: T) = contains(element)

// val map = mapOf("Apple" with 1 , "Banana" with 2)
infix fun <A, B> A.with(that: B): Pair<A, B> = Pair(this, that)