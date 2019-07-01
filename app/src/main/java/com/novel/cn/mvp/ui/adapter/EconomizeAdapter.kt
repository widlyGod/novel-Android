package com.novel.cn.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.mvp.model.entity.Calalogue
import com.novel.cn.mvp.model.entity.EconomizeBean

class EconomizeAdapter  : BaseQuickAdapter<EconomizeBean, BaseViewHolder>(R.layout.item_economize) {
    override fun convert(helper: BaseViewHolder, item: EconomizeBean?) {
        with(helper.itemView) {

        }
    }

}