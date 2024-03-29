package com.novel.cn.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.app.Constant
import com.novel.cn.app.Preference
import com.novel.cn.app.loadHeadImage
import com.novel.cn.app.visible
import com.novel.cn.mvp.model.entity.CircleCommentReply
import com.novel.cn.mvp.model.entity.LoginInfo
import com.novel.cn.utils.TimeUtils
import kotlinx.android.synthetic.main.item_circle_comment.view.*
import java.text.SimpleDateFormat

class CircleCommentReplyAdapter : BaseQuickAdapter<CircleCommentReply, BaseViewHolder>(R.layout.item_circle_comment) {

    private var onReplyClickListener: ((Int) -> Unit)? = null
    private var onLikeClickListener: ((Int) -> Unit)? = null
    private var onUnLikeClickListener: ((Int) -> Unit)? = null
    private var onDeleteClickListener: ((Int) -> Unit)? = null

    fun setOnLikeClickListener(listener: ((Int) -> Unit)?) {
        this.onLikeClickListener = listener
    }

    fun setOnReplyClickListener(listener: ((Int) -> Unit)?) {
        this.onReplyClickListener = listener
    }

    fun setOnUnLikeClickListener(listener: ((Int) -> Unit)?) {
        this.onUnLikeClickListener = listener
    }

    fun setOnDeleteClickListener(listener: ((Int) -> Unit)?) {
        this.onDeleteClickListener = listener
    }

    override fun convert(helper: BaseViewHolder, item: CircleCommentReply) {
        with(helper.itemView) {
            iv_avatar.loadHeadImage(item.replyUserPhoto)
            tv_nickname.text = item.replyUserName
            if (item.replyType == 1)
                tv_content.text = "回复@${item.toReplyUserName}: ${item.replyContent}"
            else
                tv_content.text = item.replyContent
            tv_time.text = TimeUtils.millis2String(item.replyTime, SimpleDateFormat("yyyy-MM-dd HH:mm"))
            iv_thumbUp.setImageResource(if (item.hadThumbed) R.drawable.ic_zan_check else R.drawable.ic_zan_uncheck)
            tv_num.text = item.thumbNum.toString()
            ll_like.setOnClickListener { if (!item.hadThumbed) onLikeClickListener?.invoke(helper.adapterPosition - headerLayoutCount) else onUnLikeClickListener?.invoke(helper.adapterPosition - headerLayoutCount) }
            tv_reply_num.setOnClickListener { onReplyClickListener?.invoke(helper.adapterPosition - headerLayoutCount) }
            val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
            if (user?.userId == item.replyUserId) {
                tv_delete.visible(true)
                tv_reply_num.visible(false)
            } else {
                tv_delete.visible(false)
                tv_reply_num.visible(true)
            }
            tv_delete.setOnClickListener {
                onDeleteClickListener?.invoke(helper.adapterPosition - headerLayoutCount)
            }
        }

    }


}