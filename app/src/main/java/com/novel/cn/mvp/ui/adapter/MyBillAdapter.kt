package com.novel.cn.mvp.ui.adapter

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.mvp.model.entity.MyBuyOrder
import com.novel.cn.utils.TimeUtils
import kotlinx.android.synthetic.main.item_my_bill.view.*
import java.text.SimpleDateFormat

class MyBillAdapter : BaseQuickAdapter<MyBuyOrder, BaseViewHolder>(R.layout.item_my_bill) {

    @SuppressLint("SimpleDateFormat")
    override fun convert(helper: BaseViewHolder, item: MyBuyOrder) {
        with(helper.itemView) {
            tv_name.text = if (item.membershipRecord == "0") "阅读币" else "VIP"
            tv_date.text = TimeUtils.millis2String(item.rechargeTime, SimpleDateFormat("yyyy-MM-dd HH:mm"))
            tv_price.text = "${if (item.plusMinus == "0") "-" else "+" }${item.rechargeAmount}"
        }
    }

}