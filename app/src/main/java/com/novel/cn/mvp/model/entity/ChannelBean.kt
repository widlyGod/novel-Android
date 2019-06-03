package com.novel.cn.mvp.model.entity

import android.os.Parcelable
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.novel.cn.mvp.ui.adapter.BookChannelAdapter
import kotlinx.android.parcel.Parcelize
import javax.annotation.Resource


class ChannelData(val recommendResultList: List<BookInfo>,
                  val hotNovelInfoList: List<BookInfo>,
                  val bestNovelRecommend: List<BookInfo>,
                  val editorRecommend: List<BookInfo>,
                  val bestAuthorZone: List<BookInfo>,
                  val freeRecommend: List<BookInfo>,
                  val recentUpdate: List<BookInfo>)

@Parcelize
open class BookInfo(

        var novelId: String? = null,
        var novelTitle: String? = null,
        var photoContent: String? = null,
        var novelType: String? = null,
        var penName: String? = null,
        var novelDescribe: String? = null,
        var chapterTitle: String? = null,
        var itemType2: Int = BookChannelAdapter.TYPE_BOOKS
) : MultiItemEntity, Parcelable {


    override fun getItemType(): Int {
        return itemType2
    }
}

class BookInfoBean(val novelList: List<BookInfo>, val selectLabelName: String, val uIStyle: Int)

class BannerInfo(val novelId: String, val path: String)


class BookHorizontal(val books: List<BookInfo>) : MultiItemEntity {
    override fun getItemType(): Int {
        return BookChannelAdapter.TYPE_HORIZONTAL
    }

}


class BannerBean(val banner: List<BannerInfo>) : MultiItemEntity {
    override fun getItemType(): Int {
        return BookChannelAdapter.TYPE_BANNER
    }
}

class MenuBean : MultiItemEntity {
    override fun getItemType(): Int {
        return BookChannelAdapter.TYPE_MENU
    }
}

class SearchBean(val books: List<BookInfo>) : MultiItemEntity {
    override fun getItemType(): Int {
        return BookChannelAdapter.TYPE_SEARCH
    }
}

data class TitleIndicator(val title: String) : MultiItemEntity {
    override fun getItemType(): Int {
        return BookChannelAdapter.TYPE_INDICATOR
    }
}

class SwitchoverBooks(val bookInfoBean: BookInfoBean) : MultiItemEntity {
    override fun getItemType(): Int {
        return BookChannelAdapter.TYPE_SWITCHOVER
    }
}

class LateyUpdate : BookInfo(), MultiItemEntity {
    override fun getItemType(): Int {
        return BookChannelAdapter.TYPE_LATEY_UPDATE
    }

}