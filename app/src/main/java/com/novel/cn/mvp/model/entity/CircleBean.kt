package com.novel.cn.mvp.model.entity

data class CircleBean(
        val endRow: Int,
        val firstPage: Int,
        val hasNextPage: Boolean,
        val hasPreviousPage: Boolean,
        val isFirstPage: Boolean,
        val isLastPage: Boolean,
        val lastPage: Int,
        val list: List<Circle>,
        val navigateFirstPage: Int,
        val navigateLastPage: Int,
        val navigatePages: Int,
        val navigatepageNums: List<Int>,
        val nextPage: Int,
        val pageNum: Int,
        val pageSize: Int,
        val pages: Int,
        val prePage: Int,
        val size: Int,
        val startRow: Int,
        val total: Int
)

data class Circle(
        val address: Address,
        val beNovelAuthor: Boolean,
        var commentNum: Int,
        val createTime: Long,
        var hadThumbed: Boolean,
        val imgUrls: List<ImageInfo>,
        var likeNum: Int,
        val momentContent: String,
        val momentId: String,
        val momentTitle: String,
        val momentType: Int,
        val novelInfo: Novel,
        val userId: Int,
        val userName: String,
        val userPhoto: String
)

data class Novel(
        val commentNum: String,
        val novelId: String,
        val novelPhoto: String = "",
        val novelTitle: String,
        val readNum: String,
        val weeklyRank: String
)

data class ImageInfo(
        val id: Int,
        val litUrl: String,
        val momentId: Int,
        val url: String
)

data class Address(
        val id: String,
        val momentId: String,
        val address: String,
        val longitude: Double,
        val latitude: Double
)