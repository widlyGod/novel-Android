package com.novel.cn.app

import android.content.Context
import com.novel.cn.mvp.model.entity.*
import com.novel.cn.mvp.ui.activity.*
import org.jetbrains.anko.startActivity

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
    fun jumpRead(context: Context?, book: NovelInfoBean) {
        context?.startActivity<ReadActivity>("book" to book)
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

    /**
     * 跳转到用户信息
     */
    fun jumpUserInfo(context: Context?,user: User){
        context?.startActivity<UserInfoActivity>("user" to user)
    }

    /**
     * 跳转到充值页面
     */
    fun jumpRecharge(context: Context?,user: User){
        context?.startActivity<RechargeActivity>("user" to user)
    }

    fun jumpContents(context: Context?,book: NovelInfoBean) {
        context?.startActivity<ContentsActivity>("book" to book)
    }
}