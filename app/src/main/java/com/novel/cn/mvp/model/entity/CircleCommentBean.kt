package com.novel.cn.mvp.model.entity

data class CircleCommentBean(
    val content: List<Content>,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val size: Int,
    val sort: String,
    val totalElements: Int,
    val totalPages: Int
)

data class Content(
        val commentContent: String,
        val commentId: String,
        val commentTime: Long,
        val commentUserId: Int,
        val commentUserName: String,
        val commentUserPhoto: String,
        val momentId: Int,
        val replyList: List<CircleCommentReply>,
        val thumbNum: Int
)

data class CircleCommentReply(
        val commentId: String,
        val replyContent: String,
        val replyId: String,
        val replyTime: Int,
        val replyUserId: String,
        val replyUserName: String,
        val replyUserPhoto: String,
        val toReplyUserId: String,
        val toReplyUserName: String,
        val toReplyUserPhoto: String
)