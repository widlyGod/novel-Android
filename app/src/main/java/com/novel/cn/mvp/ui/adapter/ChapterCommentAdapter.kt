package com.novel.cn.mvp.ui.adapter

import android.annotation.SuppressLint
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.jess.arms.utils.ArmsUtils.startActivity
import com.novel.cn.R
import com.novel.cn.app.*
import com.novel.cn.mvp.model.entity.BookDetail
import com.novel.cn.mvp.model.entity.ChapterComment
import com.novel.cn.mvp.model.entity.LoginInfo
import com.novel.cn.mvp.ui.activity.LoginActivity
import com.novel.cn.utils.TimeUtils
import kotlinx.android.synthetic.main.item_comment.view.*
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat

class ChapterCommentAdapter : BaseQuickAdapter<ChapterComment, BaseViewHolder>(R.layout.item_comment) {
    val levelList = ArrayList<LEVEL>()

    private var onReplyClickListener: ((Int) -> Unit)? = null
    private var onDeleteClickListener: ((Int) -> Unit)? = null
    private var onLikeClickListener: ((Int) -> Unit)? = null
    private lateinit var mBookDetail: BookDetail

    init {
        levelList.add(LEVEL.LEVEL_1)
        levelList.add(LEVEL.LEVEL_2)
        levelList.add(LEVEL.LEVEL_3)
        levelList.add(LEVEL.LEVEL_4)
        levelList.add(LEVEL.LEVEL_5)
        levelList.add(LEVEL.LEVEL_6)
        levelList.add(LEVEL.LEVEL_7)
        levelList.add(LEVEL.LEVEL_8)
        levelList.add(LEVEL.LEVEL_9)
        levelList.add(LEVEL.LEVEL_10)
        levelList.add(LEVEL.LEVEL_11)
        levelList.add(LEVEL.LEVEL_12)
        levelList.add(LEVEL.LEVEL_13)
        levelList.add(LEVEL.LEVEL_14)
        levelList.add(LEVEL.LEVEL_15)
        levelList.add(LEVEL.LEVEL_16)
        levelList.add(LEVEL.LEVEL_17)


    }

    fun setOnLikeClickListener(listener: ((Int) -> Unit)?) {
        this.onLikeClickListener = listener
    }

    fun setOnDeleteClickListener(listener: ((Int) -> Unit)?) {
        this.onDeleteClickListener = listener
    }

    fun setOnReplyClickListener(listener: ((Int) -> Unit)?) {
        this.onReplyClickListener = listener
    }

    fun setBookDetail(bookDetail: BookDetail) {
        mBookDetail = bookDetail
    }

    @SuppressLint("SetTextI18n")
    override fun convert(helper: BaseViewHolder, item: ChapterComment) {

        with(helper.itemView) {
            iv_avatar.loadHeadImage(item.chapterCommentUser.userPhoto)

            tv_time.text = TimeUtils.millis2String(item.replyTime, SimpleDateFormat("yyyy-MM-dd HH:mm"))
            if (item.remindUser != null && item.remindUser.userNickName.isNotEmpty()) {
                tv_content.text = "回复@${item.remindUser.userNickName} : ${item.content}"
            } else
                tv_content.text = item.content
            tv_from.text = Constant.DEVICE_TYPE[item.deviceType]
            tv_num.text = item.thumbUpNumber.toString()
            tv_reply_num.text = "回复"
            if (mBookDetail.authorId == item.chapterCommentUser.userId) {
                tv_isAuthor.visible(true)
                tv_nickname.text = mBookDetail.novelAuthor
            } else {
                tv_isAuthor.visible(false)
                tv_nickname.text = item.chapterCommentUser.userNickName
            }
            iv_thumbUp.setImageResource(if (item.isThumbed) R.drawable.ic_zan_check else R.drawable.ic_zan_uncheck)
            levelList.forEach {
                if (item.chapterCommentUser.fansValue in (it.startValue..it.endValue)) {
                    tv_level.apply {
                        delegate.backgroundColor = it.color.toInt()
                        text = item.chapterCommentUser.levelName
                    }
                }
                if (item.chapterCommentUser.levelName.isEmpty())
                    tv_level.visible(false)
            }

            tv_reply_num.setOnClickListener {
                val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)!!
                if (user.sessionId.isBlank()) {
                    context.startActivity<LoginActivity>()
                    return@setOnClickListener
                }
                onReplyClickListener?.invoke(helper.adapterPosition - headerLayoutCount)
            }
            tv_delete.setOnClickListener {
                onDeleteClickListener?.invoke(helper.adapterPosition - headerLayoutCount)
            }

            ll_like.setOnClickListener { if (!item.isThumbed) onLikeClickListener?.invoke(helper.adapterPosition - headerLayoutCount) }

            val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
            if (user?.userId == item.chapterCommentUser.userId) {
                tv_delete.visible(true)
                tv_reply_num.visible(false)
            } else {
                tv_delete.visible(false)
                tv_reply_num.visible(true)
            }

        }
    }


    enum class LEVEL private constructor(val startValue: Int, val endValue: Int, val color: Long, val text: String) {

        LEVEL_1(1, 499, 0xFFF4B2BD, "见习"),
        LEVEL_2(500, 999, 0xFFFFB05B, "书虫"),
        LEVEL_3(1000, 1999, 0xFF43DDC2, "书迷"),
        LEVEL_4(2000, 4999, 0xFFFF7A8A, "书呆"),
        LEVEL_5(5000, 7999, 0xFF9393FF, "书钰"),
        LEVEL_6(8000, 9999, 0xFFFF97EF, "书侯"),
        LEVEL_7(10000, 19999, 0xFFA1E231, "书尊"),
        LEVEL_8(20000, 29999, 0xFFC5C30E, "书王"),
        LEVEL_9(30000, 49999, 0xFF57D3FC, "书皇"),
        LEVEL_10(50000, 79999, 0xFF3AA3BA, "书仙"),
        LEVEL_11(80000, 99999, 0xFF7598C4, "书神"),
        LEVEL_12(100000, 149999, 0xFFB063A4, "书圣"),
        LEVEL_13(150000, 199999, 0xFF2F26D, "白银十圣"),
        LEVEL_14(200000, 499999, 0xFFF8C108, "黄金十圣"),
        LEVEL_15(500000, 799999, 0xFFB073FA, "白金十圣"),
        LEVEL_16(800000, 999999, 0xFFBB854B, "钻石十圣"),
        LEVEL_17(1000000, Int.MAX_VALUE, 0xFFA5A3AD, "书虫"),
    }
}
