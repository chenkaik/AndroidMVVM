package com.android.lib.util

/**
 * date: 2020/8/8
 * desc: 求最大数
 * val a = 10
    val b = 15
    val c = 20
    val largest = max(a, b, c)
 */
fun <T : Comparable<T>> max(vararg nums: T): T {
    if (nums.isEmpty()) {
        throw RuntimeException("Params can not be empty.")
    }
    var maxNum = nums[0]
    for (num in nums) {
        if (num > maxNum) {
            maxNum = num
        }
    }
    return maxNum
}