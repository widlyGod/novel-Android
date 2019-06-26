package com.novel.cn.mvp.ui.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import com.jakewharton.rxbinding3.view.clicks
import com.novel.cn.R
import com.novel.cn.ext.clicks
import com.novel.cn.mvp.contract.VipContract
import com.novel.cn.mvp.model.entity.CouponBean
import com.novel.cn.mvp.ui.adapter.SelectCouponAdapter
import kotlinx.android.synthetic.main.pop_select_coupon.view.*
import razerdp.basepopup.BasePopupWindow

@SuppressLint("CheckResult")
class SelectCouponPopup(context: Context, list: List<CouponBean>, view: VipContract.View) : BasePopupWindow(context) {

    private val mSelectCouponAdapter by lazy { SelectCouponAdapter() }
    private var selectedCouponId: String = ""

    init {
        popupGravity = Gravity.BOTTOM
        contentView.recyclerView.apply {
            adapter = mSelectCouponAdapter
        }
        mSelectCouponAdapter.setNewData(list)
        mSelectCouponAdapter.clicks().subscribe {
            if (list[it.second].isSelect) {

            } else {
                selectedCouponId = list[it.second].id
                list.forEach { coupon ->
                    coupon.isSelect = selectedCouponId == coupon.id
                }
                mSelectCouponAdapter.notifyDataSetChanged()
            }
        }
        contentView.tv_select_coupon_done.clicks().subscribe {
            view.selectCoupon(selectedCouponId)
            dismiss()
        }
        contentView.iv_close_pop.clicks().subscribe {
            dismiss()
        }
    }

    override fun onCreateShowAnimation(): Animation {
        return getTranslateVerticalAnimation(1f, 0f, 300)
    }

    override fun onCreateDismissAnimation(): Animation {
        return getTranslateVerticalAnimation(0f, 1f, 300)
    }

    override fun onCreateContentView(): View = createPopupById(R.layout.pop_select_coupon)

}