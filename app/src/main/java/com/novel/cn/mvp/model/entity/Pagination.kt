package com.novel.cn.mvp.model.entity

data class Pagination<T>(val total: Int, val book: List<T>)