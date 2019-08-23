package com.novel.cn.mvp.model.entity

data class ChatMessageBean(
    val data: Data,
    val code: String,
    val message: String,
    val success: Boolean
)

data class Data(
    val content: String,
    val image: Image,
    val time: Long,
    val type: Int,
    val userId: String,
    val userName: String,
    val userPhoto: String
)