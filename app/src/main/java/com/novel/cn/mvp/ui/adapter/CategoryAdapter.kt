package com.novel.cn.mvp.ui.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.novel.cn.R
import com.novel.cn.app.JumpManager
import com.novel.cn.app.loadImage
import com.novel.cn.app.visible
import com.novel.cn.mvp.model.entity.Category
import com.novel.cn.mvp.model.entity.CategoryChildren
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.item_gender.view.*

class CategoryAdapter : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(ArrayList()) {

    companion object {
        const val TYPE_TITLE = 1
        const val TYPE_CHILD = 2
    }

    init {
        addItemType(TYPE_TITLE, R.layout.item_gender)
        addItemType(TYPE_CHILD, R.layout.item_category)

        //设置跨列
        setSpanSizeLookup { gridLayoutManager, position ->
            val spanCount = gridLayoutManager.spanCount
            return@setSpanSizeLookup when (getItemViewType(position)) {
                TYPE_TITLE -> spanCount
                else -> 1
            }
        }
    }

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity) {

        val itemView = helper.itemView
        when (item.itemType) {
            TYPE_TITLE -> {
                item as Category
                itemView.tv_type.text = item.typeName
                itemView.line.visible(helper.adapterPosition != 0)
            }
            TYPE_CHILD -> {
                item as CategoryChildren
                itemView.iv_icon.loadImage(item.imgUrl)
                itemView.tv_category_name.text = item.typeName
                itemView.setOnClickListener { JumpManager.toCategoryList(mContext, item.id, item.parentId, item.typeName) }
            }
        }

    }

}