package com.novel.cn.mvp.ui.adapter

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.lzy.ninegrid.ImageInfo
import com.lzy.ninegrid.NineGridView
import com.lzy.ninegrid.preview.NineGridViewClickAdapter
import com.novel.cn.R
import com.novel.cn.app.JumpManager
import com.novel.cn.app.loadHeadImage
import com.novel.cn.app.loadImage
import com.novel.cn.app.visible
import com.novel.cn.mvp.model.entity.Circle
import kotlinx.android.synthetic.main.item_circle.view.*
import java.util.*


class CircleAdapter : BaseQuickAdapter<Circle, BaseViewHolder>(R.layout.item_circle) {

    private var onReplyClickListener: ((Int) -> Unit)? = null
    private var onDeleteClickListener: ((Int) -> Unit)? = null
    private var onLikeClickListener: ((Int) -> Unit)? = null

    fun setOnLikeClickListener(listener: ((Int) -> Unit)?) {
        this.onLikeClickListener = listener
    }

    fun setOnDeleteClickListener(listener: ((Int) -> Unit)?) {
        this.onDeleteClickListener = listener
    }

    fun setOnReplyClickListener(listener: ((Int) -> Unit)?) {
        this.onReplyClickListener = listener
    }

    override fun convert(helper: BaseViewHolder, item: Circle) {
        with(helper.itemView) {
            iv_avatar.loadHeadImage(item.userPhoto)
            tv_user_name.text = item.userName
            tv_circle_title.text = item.momentTitle
            tv_circle_content.text = item.momentContent
            tv_num.text = item.likeNum.toString()
            tv_comment_num.text = item.commentNum.toString()
            tv_isAuthor.visible(item.beNovelAuthor)
            iv_thumbUp.setImageResource(if (item.hadThumbed) R.drawable.ic_zan_check else R.drawable.ic_zan_uncheck)
            ll_like.setOnClickListener { if (!item.hadThumbed) onLikeClickListener?.invoke(helper.adapterPosition - headerLayoutCount) }
            ll_comment.setOnClickListener { onReplyClickListener?.invoke(helper.adapterPosition - headerLayoutCount) }
            when (item.momentType) {
                2 -> {
                    rl_book_detail.visible(true)
                    nineGrid.visible(false)
                    iv_book_image.loadImage(item?.novelInfo?.novelPhoto)
                    tv_book_name.text = item?.novelInfo?.novelTitle
                    tv_book_detail.text = "书评${item?.novelInfo?.commentNum}  书友${item?.novelInfo?.readNum}  周排名${item?.novelInfo?.weeklyRank}"
                }
                0 -> {
                    rl_book_detail.visible(false)
                    nineGrid.visible(false)
                }
                1 -> {
                    rl_book_detail.visible(false)
                    nineGrid.visible(true)
                    val imageInfo = ArrayList<ImageInfo>()
                    val images = item.imgUrls
                    if (images != null) {
                        for (image in images) {
                            val info = ImageInfo()
                            info.setThumbnailUrl(image.litUrl)
                            info.setBigImageUrl(image.url)
                            imageInfo.add(info)
                        }
                    }
                    nineGrid.setAdapter(NineGridViewClickAdapter(mContext, imageInfo))
                }
            }

        }
    }

}