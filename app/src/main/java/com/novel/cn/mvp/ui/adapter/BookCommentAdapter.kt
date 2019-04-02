package com.novel.cn.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.app.loadImage
import com.novel.cn.mvp.model.entity.Comment
import com.novel.cn.utils.TimeUtils
import kotlinx.android.synthetic.main.item_review.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat

class BookCommentAdapter : BaseQuickAdapter<Comment, BaseViewHolder>(R.layout.item_review) {
    override fun convert(helper: BaseViewHolder, item: Comment) {
        with(helper.itemView) {
            iv_avatar.loadImage(item.commentUser.userPhoto)
            tv_level.text = item.commentUser.levelName
            tv_nickname.text = item.commentUser.userNickName
            tv_time.text = TimeUtils.millis2String(item.commentTime, SimpleDateFormat("yyyy-MM-dd HH:mm"))
            tv_content.text = item.content
            tv_from.text = item.deviceType
            tv_num.text = item.thumbUpNumber.toString()
            tv_reply_num.text = "回复(${item.replyNumber})"
        }
    }
}
