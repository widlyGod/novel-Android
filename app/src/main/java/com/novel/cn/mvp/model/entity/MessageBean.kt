package com.novel.cn.mvp.model.entity

import com.google.gson.annotations.SerializedName

data class MessageBean(@SerializedName("novelMessageCount")val total: Int, @SerializedName("novelMessageList")val  list: List<Message>)


data class Message(val commentId: String, val headContent: String, val id: String, val messageStatus: String, val messageType: Int,
                   val novelId: String, val sortTime: String, val typeName: String, val typeNumber: Int)