package com.novel.cn.mvp.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BookDetail(val novelId: String, val novelAuthor: String, val novelPhoto: String, val isCollection: Boolean, val isRead: Boolean,val authorId:String,
                      val novelWords: Int, val clickNum: Int, val novelTitle: String, val novelIntro: String, val chapterCount: String,val isFreeLimit:Boolean) : Parcelable

@Parcelize
data class NovelInfoBean(val novelInfo: BookDetail, val comment: CommentInfo) : Parcelable

@Parcelize
data class CommentInfo(val totalCount: Int, val comments: List<Comment>) : Parcelable

@Parcelize
data class Comment(val commentId: String, val commentTime: Long, val content: String, val counts: Int, var thumbUpNumber: Int,
                   val uid: String, var replyNumber: Int, val isAuthor: Boolean,
                   val deviceType: String, val commentUser: CommentUser, var thumbUp: Boolean = false,var isThumbed: Boolean = false) : Parcelable

@Parcelize
data class CommentUser(val userPhoto: String, val userNickName: String, val userId: String,
                       val recodeCode: String, val penName: String, val levelName: String, val fansValue: Int) : Parcelable