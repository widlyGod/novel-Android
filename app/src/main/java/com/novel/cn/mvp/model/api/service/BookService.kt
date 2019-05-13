package com.novel.cn.mvp.model.api.service

import com.novel.cn.mvp.model.entity.*
import io.reactivex.Observable
import retrofit2.http.*
import java.util.*
import kotlin.collections.HashMap

interface BookService {


    /**
     * 书架（收藏）
     */
    @POST("novelOAService/mobile/getAllCollection")
    fun getBookshelf(@Body params: HashMap<String, String>): Observable<BaseResponse<Pagination<Book>>>

    /**
     * 阅读记录
     */
    @POST("novelOAService/mobile/getNovleHistory")
    fun getReadRecordList(@Body params: HashMap<String, String>): Observable<BaseResponse<Pagination<Book>>>

    /**
     * 清空阅读记录
     */
    @POST("novelOAService/novelCollection/emptyNovleHistoryList")
    fun cleanRecord(): Observable<BaseResponse<Any>>

    /**
     * 删除书架书籍
     */
    @POST("novelOAService/novelCollection/cancelCollectionList")
    fun deleteBook(@Body params: LinkedList<String>): Observable<BaseResponse<Any>>

    /**
     * 分类
     */
    @GET("novelOAService/novelType/getAllTypesMo")
    fun getCategory(): Observable<BaseResponse<MutableList<Category>>>

    /**
     * 男频 女频  精选
     */
    @POST("novelUserService/channelLabelNovelManage/findChannelsLabelNovelShow")
    fun getChannelData(@Body params: HashMap<String, Int>): Observable<BaseResponse<Map<String, Any>>>

    /**
     * 小说详情
     */
    @GET("novelOAService/mobile/openNovel")
    fun getBookDetail(@Query("novelId") bookId: String?): Observable<BaseResponse<NovelInfoBean>>

    /**
     * 热门搜索
     */
    @POST("novelOAService/homepage/hotNovels")
    fun getHotWords(): Observable<BaseResponse<HotNovelBean>>

    /**
     * 排行版
     */
    @POST("novelOAService/novelList/getAppAllList")
    fun getRank(@Body params: HashMap<String, String>): Observable<BaseResponse<MutableList<RankBean>>>

    /*
    *小说评论
     */
    @POST("novelOAService/comment/findNovelComment")
    fun getCommentList(@Body params: HashMap<String, String>): Observable<BaseResponse<MutableList<Comment>>>

    /**
     * 回复列表
     */
    @POST("novelOAService/comment/findReplyPage")
    fun getReplyList(@Body params: HashMap<String, Any>): Observable<BaseResponse<MutableList<Reply>>>

    /**
     * 加入书架
     */
    @POST("novelOAService/novelCollection/saveCollection")
    fun addConllcetion(@Body params: HashMap<String, Any>): Observable<BaseResponse<Any>>

    /**
     * 加入书架
     */
    @POST("novelOAService/novelCollection/cancelCollection")
    fun cancelCollection(@Body params: HashMap<String, Any>): Observable<BaseResponse<Any>>

    /**
     * 获取小说卷
     */
    @GET("novelOAService/mobile/getVolumes")
    fun getVolumeList(@Query("novelId") bookId: String?): Observable<BaseResponse<MutableList<Volume>?>>

    /**
     * 小说目录
     */
    @POST("novelOAService/mobile/getChapters")
    fun getChapterList(@Body params: HashMap<String, Any?>): Observable<BaseResponse<ChapterBean>>

    @POST("novelOAService/novel/readNovel")
    fun readNovel(@Body params: HashMap<String, Any?>): Observable<BaseResponse<ChapterInfoBean>>

    @GET("novelOAService/comment/giveThumbUp")
    fun agree(@Query("commentId")commentId: String): Observable<BaseResponse<Any>>
}