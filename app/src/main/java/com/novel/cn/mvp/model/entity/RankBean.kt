package com.novel.cn.mvp.model.entity

import com.google.gson.annotations.SerializedName


data class RankBean(val result: RankResult, val code: String, val name: String)

data class RankResult(@SerializedName("WEEK") val week: List<RankWeek>)


data class RankWeek(val novelPhoto: String, val novelTitle: String, val novelDescribe: String, val writer: String,val isCollection:String,
                   val novelId:String )