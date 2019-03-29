package com.novel.cn.mvp.model.entity

data class MessageBean(val novelMessageCount: Int,val  novelMessageList: List<Message>)


data class Message(val commentId: String, val headContent: String, val id: String, val messageStatus: String, val messageType: Int,
                   val novelId: String, val sortTime: String, val typeName: String, val typeNumber: Int)