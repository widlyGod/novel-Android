package com.novel.cn.mvp.model.entity

class CouponBean(
        val couponDescribe: String = "",
        val couponTitle: String = "",
        val couponType: Int = 0,
        val createTime: Long= 0,
        val expireTime: Long= 0,
        val id: String = "",
        val status: Int= 0,
        val userId: String = "",
        var isSelect: Boolean = false
)