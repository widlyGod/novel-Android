package com.novel.cn.mvp.model.entity

class SearchResultBean(val novelInfos: NovelInfos)

class NovelInfos(val list: List<SearchInfo>, val total: Int)

class SearchInfo(val isFree: Int, val id: String, val title: String,val photoContent:String)