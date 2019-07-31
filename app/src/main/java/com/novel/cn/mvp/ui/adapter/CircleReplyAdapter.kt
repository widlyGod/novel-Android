package com.novel.cn.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.mvp.model.entity.CircleCommentReply
import kotlinx.android.synthetic.main.item_circle_reply.view.*

class CircleReplyAdapter : BaseQuickAdapter<CircleCommentReply, BaseViewHolder>(R.layout.item_circle_reply) {

    override fun convert(helper: BaseViewHolder, item: CircleCommentReply) {
        with(helper.itemView) {
            tv_user_name.text = "${item.replyUserName}: "
            tv_reply.text = item.replyContent
        }
    }

}