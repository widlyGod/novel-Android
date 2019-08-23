package com.novel.cn.mvp.model.entity

class ChatSendBean(
        var type: String = "0",
        var content: String,
        var image: Image? = null
)

class Image(
        var url: String,
        var litUrl: String
)