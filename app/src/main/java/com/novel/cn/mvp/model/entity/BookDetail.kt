package com.novel.cn.mvp.model.entity

data class BookDetail(val novelId: String, val novelAuthor: String, val novelPhoto: String, val isCollection: Boolean, val isRead: Boolean,
                      val novelWords: Int, val clickNum: Int, val novelTitle: String, val novelIntro: String, val chapterCount: String)


data class NovelInfoBean(val novelInfo: BookDetail, val comment: CommentInfo)


data class CommentInfo(val totalCount: Int, val comments: List<Comment>)


data class Comment(val commentId: String, val commentTime: Long, val content: String, val counts: Int, val thumbUpNumber: Int,
                   val uid: String, val replyNumber: Int, val isAuthor: Int,
                   val deviceType: String, val commentUser: CommentUser)

data class CommentUser(val userPhoto: String, val userNickName: String, val userId: String,
                       val recodeCode: String, val penName: String, val levelName: String, val fansValue: Int)