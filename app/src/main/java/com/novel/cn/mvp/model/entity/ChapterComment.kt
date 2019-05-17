package com.novel.cn.mvp.model.entity

data class ChapterComment(val chapterCommentUser:CommentUser,val chapterId:String,
                          val content:String,val isAuthor:Boolean,val deviceType:String,
                          val replyTime:Long,val thumbUpNumber:Int,val replyNumber:String,
                          val thumbUp:Boolean
                          )
