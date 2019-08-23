package com.novel.cn.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.R
import com.novel.cn.app.isNull
import com.novel.cn.app.loadHeadImage
import com.novel.cn.app.loadImage
import com.novel.cn.app.visible
import com.novel.cn.mvp.model.entity.ChatMessageBean
import com.novel.cn.mvp.model.entity.LoginInfo
import kotlinx.android.synthetic.main.item_chat_message.view.*

class ChatMessageAdapter : BaseQuickAdapter<ChatMessageBean, BaseViewHolder>(R.layout.item_chat_message) {

    private lateinit var user: LoginInfo

    public fun setUser(user: LoginInfo) {
        this.user = user
    }

    override fun convert(helper: BaseViewHolder, item: ChatMessageBean) {
        with(helper.itemView) {
            if (!user.isNull() && user.userId == item.data.userId) {
                ll_chat_message.visible(false)
                ll_chat_message_mine.visible(true)
                iv_chat_user_avatar_mine.loadHeadImage(item.data.userPhoto)
                tv_chat_user_name_mine.text = item.data.userName
                if(item.data.type==0){
                    tv_chat_message_mine.visible(true)
                    iv_chat_photo_mine.visible(false)
                    tv_chat_message_mine.text = item.data.content
                }else if(item.data.type==1){
                    tv_chat_message_mine.visible(false)
                    iv_chat_photo_mine.visible(true)
                    iv_chat_photo_mine.loadImage(item.data.image.litUrl)
                }
            } else {
                ll_chat_message.visible(true)
                ll_chat_message_mine.visible(false)
                iv_chat_user_avatar.loadHeadImage(item.data.userPhoto)
                tv_chat_user_name.text = item.data.userName
                if(item.data.type==0){
                    tv_chat_message.visible(true)
                    iv_chat_photo.visible(false)
                    tv_chat_message.text = item.data.content
                }else if(item.data.type==1){
                    tv_chat_message.visible(false)
                    iv_chat_photo.visible(true)
                    iv_chat_photo.loadImage(item.data.image.litUrl)
                }
            }

        }
    }

}