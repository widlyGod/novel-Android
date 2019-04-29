package com.novel.cn.mvp.model.entity

data class ChapterInfoBean(val chapterInfo:ChapterInfo2)


data class ChapterInfo2(val chapter: Int, val content: String, val id: String, val isFree: Boolean)