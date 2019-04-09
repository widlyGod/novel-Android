package com.novel.cn.mvp.model.entity

data class Reply(val commentId: String, val content: String, val replyTime: Long, val replyUser: CommentUser,val deviceType:String)

