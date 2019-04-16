package com.novel.cn.mvp.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Demo(val name: String, var age: Int, var nn: Int, val ss: String) : Parcelable