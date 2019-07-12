package com.novel.cn.mvp.model.entity

data class Calalogue(val novelId: String = "", val chapter: Int = 0, val chapterId: String = "", val chapterTitle: String = "", val filePath: String = "", val isFree: Boolean = false, val isLocked: Boolean = false,
                     val money: String = "", val volume: String = "", val volumeId: String = "", val volumeTitle: String = "", val words: String = "", val createTime: String = "", var isLocal: Boolean = false)


data class VolumeBean(val volumeId: String, val volumeName: String, val calalogue: List<Calalogue>)