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
        val commentUserId: String,
        val commentUserName: String,
        val commentUserPhoto: String,
        val momentId: String,
        val replyList: List<CircleCommentReply>,
        var thumbNum: Int,
        val replyNum: Int,
        var hadThumbed: Boolean
)

data class CircleCommentReply(
        val commentId: String,
        val replyContent: String,
        val replyId: String,
        val replyTime: Long,
        val replyUserId: String,
        val replyUserName: String,
        val replyUserPhoto: String,
        val toReplyUserId: String,
        val toReplyUserName: String,
        val toReplyUserPhoto: String
)