package com.novel.cn.mvp.model.entity

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parceler
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


@Parcelize
data class User(
        val basePath: String,
        val commentNum: Int,
        val coupon: Int,
        val diamondNumber: Int,
        val discountCoupon: Int,
        val goldNumber: String,
        val rechargeNumber: String,
        val rewardNumber: String,
        val grade: Int,
        val loginTime: Double,
        val meetDays: Int,
        val monthRecommendNumber: Int,
        val msgCount: Int,
        val readCount: Int,
        val readTime: Int,
        val gradeRate: Int,
        val recommendNumber: Int,
        val signDays: Int,
        val thumbNum: Int,
        val thumbedNum: Int,
        val userGender: String = "0",
        val userId: Int,
        val userIntroduction: String,
        val userNickName: String,
        val userPhoto: String,
        var vipInfo: VipInfo
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readDouble(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readString(),
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readParcelable<VipInfo>(VipInfo::class.java.classLoader)
    )

    override fun describeContents() = 0

    companion object : Parceler<User> {
        override fun User.write(dest: Parcel, flags: Int) = with(dest) {
            writeString(basePath)
            writeInt(commentNum)
            writeInt(coupon)
            writeInt(diamondNumber)
            writeInt(discountCoupon)
            writeString(goldNumber)
            writeString(rechargeNumber)
            writeString(rewardNumber)
            writeInt(grade)
            writeDouble(loginTime)
            writeInt(meetDays)
            writeInt(monthRecommendNumber)
            writeInt(msgCount)
            writeInt(readCount)
            writeInt(readTime)
            writeInt(gradeRate)
            writeInt(recommendNumber)
            writeInt(signDays)
            writeInt(thumbNum)
            writeInt(thumbedNum)
            writeString(userGender)
            writeInt(userId)
            writeString(userIntroduction)
            writeString(userNickName)
            writeString(userPhoto)
            writeParcelable(vipInfo, 0)
        }

        override fun create(source: Parcel): User = User(source)
    }
}

@Parcelize
class VipInfo(val autoRenew: Int = 0, val createTime: Long = 0, val expireTime: Long = 0, val isVip: Int = 0, val userId: Int = 0, val vipLevel: Int = 0, val vipTime: Long = 0, val vipType: Int = 0) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readLong(),
            source.readLong(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readLong(),
            source.readInt()
    )

    override fun describeContents() = 0

    companion object : Parceler<VipInfo> {
        override fun VipInfo.write(dest: Parcel, flags: Int) = with(dest) {
            writeInt(autoRenew)
            writeLong(createTime)
            writeLong(expireTime)
            writeInt(isVip)
            writeInt(userId)
            writeInt(vipLevel)
            writeLong(vipTime)
            writeInt(vipType)
        }

        override fun create(source: Parcel): VipInfo = VipInfo(source)
    }
}