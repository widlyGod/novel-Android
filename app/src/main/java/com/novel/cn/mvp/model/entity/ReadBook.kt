package com.novel.cn.mvp.model.entity

data class ChapterInfoBean(val chapterInfo:ChapterInfo2,val novelInfo:NovelInfo)


data class ChapterInfo2(val chapter: Int, val content: String, val id: String, val isFree: Boolean,
                        val updateTime:String,val title:String,val words:String,val money:Int,
                        val volumeId:String
                        )

data class NovelInfo(val authorInfo:AuthorInfo)

data class AuthorInfo(val address:String,val penName:String,val words:String)