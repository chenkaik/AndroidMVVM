package com.android.mvvm.util

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * date: 2020/9/9
 * desc:
 */
@Parcelize
data class Person(val name: String, var age: Int) : Parcelable