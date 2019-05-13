package com.novel.cn.mvp.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.novel.cn.R
import com.novel.cn.app.loadImage
import com.novel.cn.mvp.ui.activity.SearchActivity
import com.youth.banner.loader.ImageLoaderInterface
import kotlinx.android.synthetic.main.item_banner.view.*
import kotlinx.android.synthetic.main.item_book.view.*
import kotlinx.android.synthetic.main.item_book_banner.view.*
import kotlinx.android.synthetic.main.item_search.view.*
import org.jetbrains.anko.startActivity
import android.support.v7.widget.PagerSnapHelper
import android.view.ViewGroup
import android.widget.LinearLayout
import com.jess.arms.integration.EventBusManager
import com.novel.cn.app.JumpManager
import com.novel.cn.eventbus.SwitchFragmentEvent
import com.novel.cn.mvp.model.entity.*
import kotlinx.android.synthetic.main.item_indicator.view.*
import kotlinx.android.synthetic.main.item_lately_update.view.*
import org.greenrobot.eventbus.EventBus


class BookChannelAdapter : BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(ArrayList()) {

    companion object {
        const val TYPE_BANNER = 1
        const val TYPE_MENU = 2
        const val TYPE_SEARCH = 3
        const val TYPE_INDICATOR = 4
        const val TYPE_BOOKS = 5
        const val TYPE_LATEY_UPDATE = 6
        const val TYPE_EMPTY = 7
        const val TYPE_HORIZONTAL = 8
    }

    init {

        addItemType(TYPE_BANNER, R.layout.item_banner)
        addItemType(TYPE_MENU, R.layout.item_menu)
        addItemType(TYPE_SEARCH, R.layout.item_search)
        addItemType(TYPE_INDICATOR, R.layout.item_indicator)
        addItemType(TYPE_BOOKS, R.layout.item_book)
        addItemType(TYPE_LATEY_UPDATE, R.layout.item_lately_update)
        addItemType(TYPE_EMPTY, R.layout.item_empty)
        addItemType(TYPE_HORIZONTAL, R.layout.item_book_banner)

        setSpanSizeLookup { gridLayoutManager, position ->
            val spanCount = gridLayoutManager.spanCount
            when (getItemViewType(position)) {
                TYPE_BANNER, TYPE_MENU, TYPE_SEARCH, TYPE_INDICATOR,
                TYPE_LATEY_UPDATE, TYPE_EMPTY, TYPE_HORIZONTAL -> spanCount
                else -> 1
            }
        }

    }

    override fun onCreateDefViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        return super.onCreateDefViewHolder(parent, viewType)
    }

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity) {
        val itemView = helper.itemView
        when (item.itemType) {
            TYPE_BANNER -> {
                item as BannerBean
                itemView.banner.setImages(item.banner)
                        .setImageLoader(object : ImageLoaderInterface<ImageView> {
                            override fun createImageView(context: Context?): ImageView {
                                return ImageView(context)
                            }

                            override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
                                path as BannerInfo?
                                imageView?.loadImage(path?.path)
                            }
                        }).start()
            }
            TYPE_MENU -> {
                itemView as LinearLayout
                val count = itemView.childCount
                for (i in 0 until count) {
                    itemView.getChildAt(i).setOnClickListener {
                        EventBusManager.getInstance().post(SwitchFragmentEvent(i))
                    }
                }
            }
            TYPE_INDICATOR -> {
                item as TitleIndicator
                itemView.tv_title_indicator.text = item.title
            }
            TYPE_SEARCH -> {
                item as SearchBean

                val builder = StringBuilder()
                item.books.forEach {
                    builder.append(it.novelTitle)
                            .append("\t")
                            .append("\t")
                            .append("\t")
                }
                itemView.tv_keyword.text = builder

                itemView.setOnClickListener {
                    JumpManager.jumpSearch(mContext, item.books)
                }
            }
            TYPE_BOOKS -> {
                item as BookInfo

                itemView.iv_book_image.loadImage(item.photoContent)
                itemView.tv_book_name.text = item.novelTitle
                itemView.tv_author1.text = item.penName

                itemView.setOnClickListener {
                    JumpManager.jumpBookDetail(mContext, item.novelId)
                }
            }
            TYPE_HORIZONTAL -> {
                item as BookHorizontal

                val adapter = BookHorizontalAdapter()
                //设置左右滑动跟viewpager效果
                val snapHelper = PagerSnapHelper()

                itemView.recyclerView.apply {
                    //重置为null，不然会报错
                    onFlingListener = null
                    //取消焦点
                    isFocusableInTouchMode = false
                    requestFocus()
                    this.adapter = adapter
                    //添加滑动监听
                    addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                            //滑动停止
                            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                //获取当前view
                                val view = snapHelper.findSnapView(layoutManager)
                                view?.let {
                                    //获取当前滑动的position
                                    val currentPosition = layoutManager?.getPosition(it)
                                    helper.setTag(R.id.recyclerView, currentPosition)
                                    itemView.tv_page.text = (currentPosition?.plus(1)).toString()
                                }
                            }
                        }
                    })
                }
                snapHelper.attachToRecyclerView(itemView.recyclerView)
                adapter.setNewData(item.books)

                itemView.tv_page.text = "1"
                itemView.tv_pagesize.text = "/${item.books.size}"
            }

            TYPE_LATEY_UPDATE -> {
                item as BookInfo
                itemView.tv_type.text = "【${item.novelType}】"
                itemView.tv_content.text = "${item.novelTitle}：${item.chapterTitle}"

            }
        }
    }

}