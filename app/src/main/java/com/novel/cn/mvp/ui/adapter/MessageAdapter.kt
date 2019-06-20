package com.novel.cn.mvp.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.novel.cn.mvp.model.entity.Message
import com.novel.cn.R
import com.novel.cn.app.visible
import kotlinx.android.synthetic.main.item_message.view.*

class MessageAdapter : BaseQuickAdapter<Message, BaseViewHolder>(R.layout.item_message) {


    val map = HashMap<Int, Long>()

    init {
        map[0] = 0xFFA745F9
        map[1] = 0xFFFF9C00
        map[2] = 0xFF01BBF1
        map[3] = 0xFFF93A7D
        map[4] = 0xFF00BE82
        map[5] = 0xFFFC3A3A
        map[6] = 0xFFFC3A3A
        map[7] = 0xFFFC3A3A
        map[8] = 0xFF76A511
        map[9] = 0xFF76A511
        map[10] = 0xFF76A511
        map[11] = 0xFF76A511
        map[12] = 0xFF76A511
        map[13] = 0xFF3B82E3
        map[14] = 0xFFFFA4C3
    }

    override fun convert(helper: BaseViewHolder, item: Message) {

        with(helper.itemView) {
            tv_title.setTextColor(map[item.typeNumber]?.toInt()!!)
            tv_title.text = item.typeName
            iv_unread_message.visible(item.messageStatus == 0)
            tv_content.text = item.headContent
            tv_date.text = item.sortTime
        }
    }

}