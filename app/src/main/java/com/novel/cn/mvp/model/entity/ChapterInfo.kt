package com.novel.cn.mvp.model.entity

data class ChapterBean(val endRow: Int,val list:MutableList<ChapterInfo>)

data class ChapterInfo(val chapter: Int, val isFree: Boolean, val id: String, val title: String, val isReading:Boolean)