package com.novel.cn.mvp.model.entity

import com.google.gson.annotations.SerializedName

data class RankResult(val code:String,val name:String,@SerializedName("WEEK")val week:List<RankWeek>)


data class RankWeek(val novelPhoto:String,val novelTitle:String)