package com.novel.cn.mvp.ui.adapter

import android.view.LayoutInflater
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.app.loadHeadImage
import com.novel.cn.ext.clicks
import com.novel.cn.mvp.model.entity.Content
import com.novel.cn.utils.TimeUtils
import kotlinx.android.synthetic.main.item_circle_comment.view.*
import kotlinx.android.synthetic.main.item_circle_reply.view.*
import java.text.SimpleDateFormat

class CircleCommentAdapter : BaseQuickAdapter<Content, BaseViewHolder>(R.layout.item_circle_comment) {

    private var onReplyClickListener: ((Int) -> Unit)? = null
    private var onLikeClickListener: ((Int) -> Unit)? = null
    private var onCommentReplyMoreClickListener: ((Int) -> Unit)? = null

    fun setOnLikeClickListener(listener: ((Int) -> Unit)?) {
        this.onLikeClickListener = listener
    }

    fun setOnReplyClickListener(listener: ((Int) -> Unit)?) {
        this.onReplyClickListener = listener
    }

    fun setOnCommentReplyMoreClickListenerListener(listener: ((Int) -> Unit)?) {
        this.onCommentReplyMoreClickListener = listener
    }

    override fun convert(helper: BaseViewHolder, item: Content) {
        with(helper.itemView) {
            iv_avatar.loadHeadImage(item.commentUserPhoto)
            tv_nickname.text = item.commentUserName
            tv_content.text = item.commentContent
            tv_time.text = TimeUtils.millis2String(item.commentTime, SimpleDateFormat("yyyy-MM-dd HH:mm"))
            iv_thumbUp.setImageResource(if (item.hadThumbed) R.drawable.ic_zan_check else R.drawable.ic_zan_uncheck)
            tv_num.text = item.thumbNum.toString()
            ll_like.setOnClickListener { if (!item.hadThumbed) onLikeClickListener?.invoke(helper.adapterPosition - headerLayoutCount) }
            tv_reply_num.setOnClickListener { onReplyClickListener?.invoke(helper.adapterPosition - headerLayoutCount) }
            var adapter = CircleReplyAdapter()
            circle_reply_recyclerView.adapter = adapter.apply {
                setNewData(item.replyList)
            }
            adapter.clicks().subscribe {
                onCommentReplyMoreClickListener?.invoke(helper.adapterPosition - headerLayoutCount)
            }
            if (item.replyNum > 3) {
                val footer = LayoutInflater.from(context).inflate(R.layout.item_circle_reply, recyclerView, false)
                adapter.addFooterView(footer)
                footer.tv_user_name.text = "共${item.replyNum}条回复 >"
            }
        }
    }

}