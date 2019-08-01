package com.novel.cn.mvp.ui.adapter

import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.mvp.model.entity.CircleCommentReply
import kotlinx.android.synthetic.main.item_circle_reply.view.*

class CircleReplyAdapter : BaseQuickAdapter<CircleCommentReply, BaseViewHolder>(R.layout.item_circle_reply) {

    override fun convert(helper: BaseViewHolder, item: CircleCommentReply) {
        with(helper.itemView) {
            if (item.toReplyUserId.isNotEmpty()) {
                val spannableString = SpannableString("${item.replyUserName}回复@${item.toReplyUserName}: ${item.replyContent}")
                spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#666666")), item.replyUserName.length, item.replyUserName.length + 2, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#666666")), item.replyUserName.length + item.toReplyUserName.length + 2, spannableString.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
                tv_user_name.text = spannableString
            } else {
                val spannableString = SpannableString("${item.replyUserName}: ${item.replyContent}")
                spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#666666")), item.replyUserName.length, spannableString.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
                tv_user_name.text = spannableString
            }
        }
    }

}