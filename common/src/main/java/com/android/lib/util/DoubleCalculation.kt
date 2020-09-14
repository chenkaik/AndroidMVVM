package com.android.lib.util

import java.math.BigDecimal

/**
 * date: 2019/1/15
 * desc: double值加减乘除计算
 */
object DoubleCalculation {

    /**
     * 加法运算
     *
     * @param m1 值1
     * @param m2 值2
     * @return 计算结果
     */
    fun addDouble(m1: Double, m2: Double): Double {
        val p1 = BigDecimal(m1.toString())
        val p2 = BigDecimal(m2.toString())
        return p1.add(p2).toDouble()
    }

    /**
     * 减法运算
     *
     * @param m1 值1
     * @param m2 值2
     * @return 计算结果
     */
    fun subDouble(m1: Double, m2: Double): Double {
        val p1 = BigDecimal(m1.toString())
        val p2 = BigDecimal(m2.toString())
        return p1.subtract(p2).toDouble()
    }

    /**
     * 乘法运算
     *
     * @param m1 值1
     * @param m2 值2
     * @return 计算结果
     */
    fun mul(m1: Double, m2: Double): Double {
        val p1 = BigDecimal(m1.toString())
        val p2 = BigDecimal(m2.toString())
        return p1.multiply(p2).toDouble()
    }

    /**
     * 除法运算
     *
     * @param m1    值1
     * @param m2    值2
     * @param scale 四舍五入 小数点位数
     * @return 计算结果
     */
    fun div(m1: Double, m2: Double, scale: Int): Double {
        // require(boolean) 用来检测方法的参数，当参数boolean为false时，抛出 IllegalArgumentException
        require(scale >= 0) { "Parameter error" }
        val p1 = BigDecimal(m1.toString())
        val p2 = BigDecimal(m2.toString())
        return p1.divide(p2, scale, BigDecimal.ROUND_HALF_UP).toDouble()
    }

}