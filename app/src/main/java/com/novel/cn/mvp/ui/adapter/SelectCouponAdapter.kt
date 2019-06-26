package com.novel.cn.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.app.JumpManager
import com.novel.cn.app.loadImage
import com.novel.cn.app.visible
import com.novel.cn.mvp.model.entity.CouponBean
import com.novel.cn.mvp.model.entity.SearchInfo
import com.novel.cn.utils.DateUtil
import kotlinx.android.synthetic.main.item_search_result.view.*
import kotlinx.android.synthetic.main.item_select_coupon.view.*

class SelectCouponAdapter : BaseQuickAdapter<CouponBean, BaseViewHolder>(R.layout.item_select_coupon) {
    override fun convert(helper: BaseViewHolder, item: CouponBean) {
        with(helper.itemView) {
            tv_coupon_title.text = item.couponDescribe
            if (item.id.isEmpty()) {
                tv_coupon_date.visible(false)
                tv_coupon_detail.visible(false)
                tv_coupon_title.text = "不使用"
                        } else {
                tv_coupon_date.text = "有效期：${DateUtil.formatDateTime(item.expireTime)}"
                tv_coupon_title.text = item.couponTitle
                tv_coupon_detail.text = item.couponDescribe
            }

            iv_select_coupon.isSelected = item.isSelect
        }
    }
}