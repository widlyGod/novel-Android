package com.novel.cn.mvp.ui.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import com.flyco.dialog.widget.base.BottomBaseDialog
import com.novel.cn.R

class RechargeCouponDialog(context: Context) : BottomBaseDialog<ReadSettingDialog>(context) {


    override fun setUiBeforShow() {

    }

    override fun onCreateView(): View {
        return layoutInflater.inflate(R.layout.dialog_recharge_coupon, null, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}
