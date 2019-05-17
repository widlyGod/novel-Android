package com.novel.cn.mvp.model.entity

data class PayInfoBean(val tradeNumber: String, val payCode: Any)

data class PayInfo(val `package`: String, val appid: String, val sign: String, val partnerid: String, val prepayid: String, val noncestr: String, val timestamp: String)