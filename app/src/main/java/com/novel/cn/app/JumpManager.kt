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
    fun jumpSearch(context: Context?, hotNovels: List<BookInfo>, type: Int, isAddBookComment: Boolean = false) {
        context?.startActivity<SearchActivity>("hotNovels" to hotNovels, "type" to type, "isAddBookComment" to isAddBookComment)
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
//    fun toCommentList(context: Context?, bookId: String?) {
//        context?.startActivity<CommentActivity>("bookId" to bookId)
//    }

    fun toCommentList(context: Context?, book: NovelInfoBean?) {
        context?.startActivity<CommentActivity>("book" to book)
    }

    /**
     * 跳转到评论详情
     */
    fun toCommentDetail(context: Context?, comment: Comment?, book: NovelInfoBean?) {
        context?.startActivity<CommentDetailActivity>("comment" to comment, "book" to book)
    }

    /**
     * 跳转到用户信息
     */
    fun jumpUserInfo(context: Context?, user: User) {
        context?.startActivity<UserInfoActivity>("user" to user)
    }

    /**
     * 跳转到书籍管理
     */
    fun jumpBookManager(context: Context?, type: Int) {
        context?.startActivity<BookManagerActivity>("type" to type)
    }

    /**
     * 跳转到我的账户
     */
    fun jumpMineAccount(context: Context?, user: User) {
        context?.startActivity<MineAccountActivity>("user" to user)
    }

    /**
     * 跳转到我的账户
     */
    fun jumpVipInfo(context: Context?, user: User, isTime: Boolean = false) {
        context?.startActivity<VipActivity>("user" to user, "isTime" to isTime)
    }

    /**
     * 跳转到充值页面
     */
    fun jumpRecharge(context: Context?) {
        context?.startActivity<RechargeActivity>()
    }

    fun jumpContents(context: Context?, book: NovelInfoBean) {
        context?.startActivity<ContentsActivity>("book" to book)
    }

    fun jumpChapterComment(context: Context?, bookId: String?, chapterId: String?, volumeId: String?, authorId: String, book: NovelInfoBean) {
        context?.startActivity<ChapterCommentActivity>("bookId" to bookId,
                "chapterId" to chapterId,
                "volumeId" to volumeId,
                "authorId" to authorId,
                "book" to book
        )
    }

    /**
     * 跳转到分类列表
     */
    fun toCategoryList(context: Context?, novelTypeId: Int?, parentId: Int?, title: String?) {
        context?.startActivity<CategoryListActivity>("novelTypeId" to novelTypeId, "parentId" to parentId, "title" to title)
    }

    /**
     * 跳转圈子发布
     */
    fun toPublish(context: Context?, type: Int) {
        context?.startActivity<PublishActivity>("type" to type)
    }

    /**
     * 跳转圈子详情
     */
    fun toCircleComment(context: Context?, momentId: String) {
        context?.startActivity<CircleCommentActivity>("momentId" to momentId)
    }

    /**
     * 跳转圈子评论回复详情
     */
    fun toCircleCommentReplyDetail(context: Context?, commentId: String) {
        context?.startActivity<CircleCommentReplyDetailActivity>("commentId" to commentId)
    }
}