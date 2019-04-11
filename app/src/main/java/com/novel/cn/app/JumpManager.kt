package com.novel.cn.app

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.gson.Gson
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.novel.cn.mvp.model.entity.Book
import com.novel.cn.mvp.model.entity.BookInfo
import com.novel.cn.mvp.model.entity.Comment
import com.novel.cn.mvp.model.entity.HotNovel
import com.novel.cn.mvp.ui.activity.*
import org.jetbrains.anko.startActivity
import java.util.ArrayList

/**
 * 跳转管理类
 */
object JumpManager {

    /**
     * 跳转书籍详情页面
     */
    fun jumpBookDetail(context: Context?, bookId: String?) {
        context?.startActivity<BookDetailActivity>("bookId" to bookId)
    }

    /**
     * 跳转到阅读页面
     */
    fun jumpRead(context: Context?, bookId: String?) {
        context?.startActivity<ReadActivity>("bookId" to bookId)
    }

    /**
     * 跳转到搜索页面
     */
    fun jumpSearch(context: Context?, hotNovels: List<BookInfo>) {
        context?.startActivity<SearchActivity>("hotNovels" to hotNovels)
    }

    /**
     * 跳转到排行榜列表
     */
    fun toRankList(context: Context?, code: String?, title: String?) {
        context?.startActivity<RankListActivity>("code" to code, "title" to title)
    }

    /**
     * 跳转到评论列表
     */
    fun toCommentList(context: Context?, bookId: String?) {
        context?.startActivity<CommentActivity>("bookId" to bookId)
    }

    /**
     * 跳转到评论详情
     */
    fun toCommentDetail(context: Context?,comment: Comment?){
        context?.startActivity<CommentDetailActivity>("comment" to comment)
    }
}