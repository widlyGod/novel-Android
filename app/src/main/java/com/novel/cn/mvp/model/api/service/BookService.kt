package com.novel.cn.mvp.model.api.service

import com.novel.cn.mvp.model.entity.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
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
     * 自动订阅查询
     */
    @POST("/novelapi/novelOAService/mobile/getSubcribe")
    fun getBookSubcribe(@Body params: HashMap<String, String>): Observable<BaseResponse<Pagination<Book>>>

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
     * 取消自动订阅
     */
    @POST("novelOAService/novelSubscribe/cancelSubscribeList")
    fun cancelSubscribeList(@Body params: LinkedList<String>): Observable<BaseResponse<Any>>


    /**
     * 分类
     */
    @GET("novelOAService/novelType/getAllTypesApp")
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
     * 搜索
     */
    @POST("novelOAService/homepage/searchNovel")
    fun search(@Body params: HashMap<String, Any>): Observable<BaseResponse<SearchResultBean>>

    /**
     * 排行版
     */
    @POST("novelOAService/novelList/getAppAllList")
    fun getRank(@Body params: HashMap<String, String>): Observable<BaseResponse<MutableList<RankBean>>>


    /**
     * 筛选小说
     */
    @POST("novelOAService/novelFilter/getNovels")
    fun getNovels(@Body params: HashMap<String, Any>): Observable<BaseResponse<CategoryBean>>


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
     * 取消加入书架
     */
    @GET("novelOAService/novelCollection/cancelCollection")
    fun cancelCollection(@Query("novel_id") bookId: String): Observable<BaseResponse<Any>>

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


    /**
     * 获取单章小说
     */
    @POST("novelOAService/novel/readNovel")
    fun readNovel(@Body params: HashMap<String, Any?>): Observable<BaseResponse<ChapterInfoBean>>

    /**
     * 点赞
     */
    @GET("novelOAService/comment/giveThumbUp")
    fun agree(@Query("commentId") commentId: String, @Query("type") type: Int): Observable<BaseResponse<Any>>

    /**
     * 获取热门搜索
     */
    @GET("novelUserService/channelLabelNovelManage/selectHot")
    fun getHotSearch(): Observable<BaseResponse<List<BookInfo>>>


    /**
     * 发布评论
     */
    @POST("novelOAService/comment/saveComment")
    fun comment(@Body params: HashMap<String, String?>): Observable<BaseResponse<Any>>

    /**
     * 删除评论
     */
    @GET("novelOAService/comment/deleteComment")
    fun deleteComment(@Query("commentId") commentId: String): Observable<BaseResponse<Any>>

    /**
     * 删除评论
     */
    @POST("novelOAService/comment/deleteReply")
    fun deleteReply(@Body params: HashMap<String, Any?>): Observable<BaseResponse<Any>>

    /**
     * 回复
     */
    @POST("novelOAService/comment/saveReply")
    fun reply(@Body params: HashMap<String, Any?>): Observable<BaseResponse<Any>>

    /**
     * 发布章节评论
     */
    @POST("novelOAService/chapterComment/queryChapterCommentPage")
    fun getChapterComment(@Body params: HashMap<String, Any?>): Observable<BaseResponse<MutableList<ChapterComment>>>

    /**
     * 单章订阅
     */
    @POST("novelOAService/novelDetail/addConsumeRecord")
    fun subcribeBook(@Body params: HashMap<String, Any?>): Observable<BaseResponse<Any>>

    /**
     * 阅读进度
     */
    @POST("novelAppService/novelChapter/updateReadRecord")
    fun updateRead(@Body params: ArrayList<HashMap<String, Any>>): Observable<BaseResponse<Any>>


    /**
     * 书管理移动
     */
    @POST("novelOAService/mobile/moveMyNovel")
    fun moveBook(@Body params: HashMap<String, HashMap<String, Int>>): Observable<BaseResponse<Any>>

    /**
     * 获取用户账户信息
     */
    @POST("novelOAService/accountCenter/queryPersonAccount")
    fun getUserAccountInfo(): Observable<BaseResponse<UserAccountBean>>

    /**
     * 获取个人中心用户账户信息
     */
    @GET("novelAppService/personalCenter/myAccountInfo")
    fun getMyAccountInfo(@Query("userId") userId: String): Observable<BaseResponse<MyAccountBean>>

    /**
     * 获取阅读时间
     */
    @GET("novelUserService/personal/getWeeklyReadTime")
    fun getReadTime(@Query("userId") userId: String): Observable<BaseResponse<ReadTimeBean>>

    /**
     * 打赏
     */
    @POST("novelOAService/reward/giveApp")
    fun reward(@Body params: HashMap<String, Any?>): Observable<BaseResponse<Any>>

    /**
     * 自动订阅
     */
    @POST("novelOAService/novelSubscribe/saveSubscribes")
    fun addAutoSubscribe(@Body params: ArrayList<String>): Observable<BaseResponse<Any>>

    /**
     * 上传阅读点击
     */
    @POST("novelOAService/novel/clickNum")
    fun clickNum(@Body params: HashMap<String, Any?>): Observable<BaseResponse<Any>>

    /**
     * 删除章节评论
     */
    @POST("novelOAService/chapterComment/deleteChapterComment")
    fun deleteChapterComment(@Body params: HashMap<String, Any?>): Observable<BaseResponse<Any>>


    @POST("novelOAService/novelDetail/isChargeChapter")
    fun isChargeChapter(@Body param: HashMap<String, Any?>): Observable<BaseResponse<ChargeChapter>>

    //发布圈子
    @Multipart
    @POST("novelAppService/moments/publishMoment")
    fun publishMoment(
            @Part parts: List<MultipartBody.Part>): Observable<BaseResponse<Any>>

    /**
     * 获取书评书籍信息
     */
    @GET("novelAppService/moments/getMomentNovel")
    fun getMomentNovel(@Query("novelId") novelId: String): Observable<BaseResponse<Novel>>

    /**
     * 查看所有圈子
     */
    @POST("novelAppService/moments/getAllMoments")
    fun getAllMoments(@Body params: HashMap<String, String>): Observable<BaseResponse<CircleBean>>

    /**
     * 圈子点赞
     */
    @POST("novelAppService/moments/commendMoment")
    fun circleAgree(@Query("momentId") momentId: String): Observable<BaseResponse<Any>>

    /**
     * 圈子评论
     */
    @POST("novelAppService/moments/comment")
    fun circleChapterComment(@Body params: HashMap<String, Any?>): Observable<BaseResponse<Any>>

    /**
     * 圈子详情
     */
    @GET("novelAppService/moments/getMoment")
    fun getMomentDetail(@Query("momentId") momentId: String): Observable<BaseResponse<Circle>>

    /**
     * 圈子详情评论
     */
    @POST("novelAppService/moments/getComments")
    fun getComments(@Body params: HashMap<String, String>): Observable<BaseResponse<CircleCommentBean>>

    @GET("novelOAService/mobile/getChapter")
    fun getChapterInfo(@Query("chapterId") link: String?): Observable<BaseResponse<ChapterInfoBean>>

    @POST("novelAppService/account/recharge")
    fun recharge(@Body code: HashMap<String, String>): Observable<BaseResponse<PayInfoBean>>

    @POST("novelOAService/chapterComment/saveChapterComment")
    fun chapterComment(@Body params: HashMap<String, Any?>): Observable<BaseResponse<Any>>

    @GET("novelAppService/catalogue/getCatalogue")
    fun getCalalogue(@Query("novelId") novelId: String): Observable<BaseResponse<CalalogueVo>>

    @Streaming
    @GET
    fun preDownload(@Url url: String?): Observable<ResponseBody>
}