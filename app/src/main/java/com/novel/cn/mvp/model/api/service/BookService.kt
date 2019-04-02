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

    @GET("novelOAService/mobile/openNovel")
    fun getBookDetail(@Query("novelId") bookId: String?): Observable<BaseResponse<NovelInfoBean>>
}