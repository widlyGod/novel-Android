package com.novel.cn.mvp.model.entity

import java.io.Serializable

data class UserInfo(
        val sessionId:String,
        val userId:String
):Serializable