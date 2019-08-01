package com.novel.cn.mvp.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
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
) : Parcelable

@Parcelize
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
) : Parcelable

@Parcelize
data class CircleCommentReply(
        val commentId: String,
        var hadThumbed: Boolean,
        val replyContent: String,
        val replyId: String,
        val replyTime: Long,
        val replyUserId: String,
        val replyUserName: String,
        val replyUserPhoto: String,
        var thumbNum: Int,
        var replyType: Int,
        val toReplyUserId: String,
        val toReplyUserName: String,
        val toReplyUserPhoto: String
) : Parcelable