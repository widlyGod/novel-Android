package com.novel.cn.mvp.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class User(val diamonds: String?, val shareCount: String?, val monthTickets: String?, val userPhoto: String?, val userLevel: String?, val userIntro: String?,
                val recommendTickets: String?, val msgCount: String?, val userName: String?, val moneys: String?, val userId: String?, val meetDays: String?, val signDays: String?,
                val userGender: Int, val readCount: String?) : Parcelable