package com.novel.cn.mvp.ui.adapter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lzy.ninegrid.ImageInfo
import com.lzy.ninegrid.preview.ImagePreviewActivity
import com.novel.cn.R
import com.novel.cn.app.loadImage
import com.novel.cn.mvp.model.entity.Circle
import kotlinx.android.synthetic.main.item_circle_image.view.*
import java.io.Serializable

class CircleImageAdapter : BaseQuickAdapter<ImageInfo, BaseViewHolder>(R.layout.item_circle_image) {

    override fun convert(helper: BaseViewHolder, item: ImageInfo) {
        with(helper.itemView) {
            iv_circle_image.loadImage(item.thumbnailUrl, R.color.image_holder, R.drawable.ic_load_image_error)
            iv_circle_image.setOnClickListener {
                val intent = Intent(context, ImagePreviewActivity::class.java)
                val bundle = Bundle()
                bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, data as Serializable)
                bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, helper.adapterPosition)
                intent.putExtras(bundle)
                context.startActivity(intent)
                (context as Activity).overridePendingTransition(0, 0)
            }
        }
    }

}