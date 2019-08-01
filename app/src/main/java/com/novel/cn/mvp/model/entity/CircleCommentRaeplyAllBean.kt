package com.novel.cn.mvp.model.entity

data class CircleCommentRaeplyAllBean(
        val content: List<CircleCommentReply>,
        val first: Boolean,
        val last: Boolean,
        val number: Int,
        val numberOfElements: Int,
        val size: Int,
        val totalElements: Int,
        val totalPages: Int
)