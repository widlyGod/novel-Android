package com.novel.cn.mvp.ui.adapter

import com.amap.api.services.core.PoiItem
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import kotlinx.android.synthetic.main.item_location_select.view.*

class LocationSelectAdapter : BaseQuickAdapter<PoiItem, BaseViewHolder>(R.layout.item_location_select){

    override fun convert(helper: BaseViewHolder, item: PoiItem) {
        with(helper.itemView) {
            tv_location_name.text = item.title
            tv_location_detail.text = item.snippet
        }
    }

}