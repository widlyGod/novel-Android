package com.novel.cn.mvp.model.entity

import java.io.Serializable

data class LoginInfo(
        val sessionId:String,
        val userId:String
):Serializable