package com.novel.cn.mvp.model.entity


data class MyBillBean(
        val myBuyOrders: List<MyBuyOrder>,
        val sumExpenditure: String,
        val sumIncome: String
)

data class MyBuyOrder(
        val membershipRecord: String,
        val rechargeAmount: String,
        val rechargeCode: Int,
        val rechargeTime: Long
)