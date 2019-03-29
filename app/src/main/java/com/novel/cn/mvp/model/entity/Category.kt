package com.novel.cn.mvp.model.entity

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.novel.cn.mvp.ui.adapter.CategoryAdapter

data class Category(val id: Int, val typeName: String, val children: MutableList<CategoryChildren>):MultiItemEntity {
    override fun getItemType(): Int {
        return CategoryAdapter.TYPE_TITLE
    }
}


data class CategoryChildren(val id: Int, val imgUrl: String, val novelNum: String, val parentId: Int, val typeName: String):MultiItemEntity{
    override fun getItemType(): Int {
        return CategoryAdapter.TYPE_CHILD
    }

}






