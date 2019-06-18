package com.novel.cn.mvp.model.entity

data class ChapterComment(val chapterCommentUser: CommentUser, val chapterId: String,
                          val content: String, val isAuthor: Boolean, val deviceType: String,
                          val replyTime: Long, var thumbUpNumber: Int, val replyNumber: String,
                          val thumbUp: Boolean, val replyId: String, val remindUser: RemindUser, var isThumbed: Boolean
)

data class RemindUser(
        val basePath: String,
        val fansValue: Any,
        val levelName: String,
        val levelValue: String,
        val penName: String,
        val recodeCode: Int,
        val userId: Int,
        val userNickName: String = "",
        val userPhoto: String
)
