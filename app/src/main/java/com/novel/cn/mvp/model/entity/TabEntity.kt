package com.novel.cn.mvp.model.entity

import com.flyco.tablayout.listener.CustomTabEntity

class TabEntity(var title: String, private var selectedIcon: Int, private var unSelectedIcon: Int) : CustomTabEntity {

    constructor(title: String) : this(title, 0, 0)

    override fun getTabTitle(): String {
        return title
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }


}
