package com.novel.cn.mvp.model.entity

import android.os.Parcelable
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.novel.cn.mvp.ui.adapter.BookChannelAdapter
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class Book(
        val auto: Boolean = false,
        val isRecord: Boolean = false,
        val newChapter: String = "",
        val newChapterId: String = "",
        val newChapterTitle: String = "",
        val newIsFree: Int = 0,
        val newMoney: String = "",
        var novelId: String = "",
        val novelPoto: String = "",
        var novelTitle: String = "",
        val readChapterId: String = "",
        val readMoney: String = "",
        val noReadNum: Int = 0,
        val readTime: Long = 0,
        val orderNum: Int = 0,
        var isLocal: Boolean = false,
        var mFilePath: String = ""
) : MultiItemEntity, Parcelable {
    override fun getItemType(): Int {
        return BookChannelAdapter.TYPE_BOOKS
    }
}
