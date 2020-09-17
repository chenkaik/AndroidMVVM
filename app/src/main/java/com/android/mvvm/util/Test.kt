package com.android.mvvm.util

/**
 * date: 2020/9/8
 * desc: 测试的数据类
 */
data class Test(
    val MSG_BODY: MSGBODY
)

data class MSGBODY(
    val colonyRecognitionMap: ColonyRecognitionMap,
    val countInfoMapList: List<CountInfoMap>
)

data class ColonyRecognitionMap(
    val expDate: String,
    val finalCount: String,
    val lot: String,
    val multiplier: Int,
    val productResults: Int,
    val referenceNo: String,
    val remark: String,
    val samplingPoint: String,
    val samplingTime: String,
    val tag: String,
    val testItems: String
)

data class CountInfoMap(
    val colorCount: String,
    val colorType: String
)
