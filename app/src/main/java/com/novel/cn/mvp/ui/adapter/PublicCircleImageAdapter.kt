package com.novel.cn.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jakewharton.rxbinding3.view.clicks
import com.novel.cn.R
import com.novel.cn.app.loadImage
import com.novel.cn.app.visible
import kotlinx.android.synthetic.main.item_public_circle_image.view.*

class PublicCircleImageAdapter : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_public_circle_image) {

    override fun convert(helper: BaseViewHolder, item: String) {
        with(helper.itemView) {
            if (item == "") {
                helper.setImageResource(R.id.iv_image, R.drawable.ic_add_image)
                iv_delete.visible(false)
            } else {
                iv_delete.visible(true)
                iv_image.loadImage(item)
                iv_delete.clicks().subscribe {
                    data.remove(item)
                    notifyDataSetChanged()
                }
            }
        }
    }

}