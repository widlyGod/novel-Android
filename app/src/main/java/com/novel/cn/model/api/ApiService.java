package com.novel.cn.model.api;


import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BaseListObjectBean;
import com.novel.cn.model.entity.BaseObjectBean;
import com.novel.cn.model.entity.BookDetailBean;
import com.novel.cn.model.entity.BookShelfAllBean;
import com.novel.cn.model.entity.BookShelfBean;
import com.novel.cn.model.entity.BookShowBean;
import com.novel.cn.model.entity.ChapterBean;
import com.novel.cn.model.entity.ChargeChapterBean;
import com.novel.cn.model.entity.HomeReturnBean;
import com.novel.cn.model.entity.PersonDataBean;
import com.novel.cn.model.entity.QueryUpayBean;
import com.novel.cn.model.entity.RankingBean;
import com.novel.cn.model.entity.ReadChapterBean;
import com.novel.cn.model.entity.ReadResponBean;
import com.novel.cn.model.entity.UserBean;
import com.novel.cn.model.entity.VolumesBean;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by YUNW-01 on 2017/10/18.
 */

public interface ApiService {


    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST
    Observable<BaseBean> sendCode(@Url String url,@Body RequestBody info);


    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("novelUserService/user/registerFromApp")
    Observable<BaseObjectBean<UserBean>> register(@Body RequestBody info);


    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("novelUserService/user/login")
    Observable<BaseObjectBean<UserBean>> login(@Body RequestBody info);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("novelUserService/user/forgetPwd")
    Observable<BaseBean> forgetPwd(@Body RequestBody info);


    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST
    Observable<BaseObjectBean<UserBean>> otherLoginRegister(@Url String url,@Body RequestBody info);


    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("novelOAService/mobile/homepage")
    Observable<HomeReturnBean> getHomepage();


    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("novelOAService/mobile/homepage")
    Observable<BaseListObjectBean<HomeReturnBean.DataBean.RecentUpdateBean>> getRecentUpdatedNovel(@Body RequestBody info);

//    @POST("aliPayCenter/appTrade")
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("novelOAService/upayCenter/upayCenterRecharge")
    Observable<String> upayCenterRecharge(@Body RequestBody info);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("novelOAService/accountCenter/queryPersonAccount")
    Observable<QueryUpayBean> queryUpayCenter();


    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("novelOAService/mobile/personCenter")
    Observable<PersonDataBean> personCenter();


    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("novelUserService/user/logout")
    Observable<BaseBean> logout();


    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST
    Observable<BookShelfBean> getBookshelfData(@Url String url,@Body RequestBody info);

    // @Query("id") int id) 不用拼接在url上
    //  @Path("newsId") String newsId      @QueryMap Map<String, String> map        需要类似这样@GET("News/{newsId}")拼接
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET
    Observable<BaseBean> cancelOper1(@Url String url,@QueryMap Map<String, String> map);


    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("novelOAService/novelCollection/saveCollection")
    Observable<BaseBean> saveCollection(@Body RequestBody info);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("novelOAService/novelList/getList")
    Observable<RankingBean> getRankingList();

    //BookShowBean
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("novelOAService/novelList/getNovelList")
    Observable<BookShowBean> getNovelList(@Body RequestBody info);


    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("novelOAService/novelType/getAllTypesMo")
    Observable<BookShelfAllBean> getAllTypesMo();

    //BookDetailBean
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("novelOAService/mobile/openNovel")
    Observable<BookDetailBean> getOpenNovel(@Query("novelId") String novelId);


    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("novelOAService/mobile/getVolumes")
    Observable<VolumesBean> getVolumes(@Query("novelId") String novelId);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("novelOAService/mobile/getChapters")
    Observable<ChapterBean> getChapters(@Body RequestBody info);




    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("novelOAService/catalogue/getCatalogue")
    Observable<ReadChapterBean> getReadChapters(@Body RequestBody info);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("novelOAService/novel/readNovel")
    Observable<ReadResponBean> readNovel(@Body RequestBody info);
    //addConsumeRecord  订阅本章节
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("novelOAService/mobile/getChapter")
    Observable<ReadResponBean> readChargeNovel(@Query("chapterId") String chapterId);


    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("novelOAService/novelDetail/isChargeChapter")
    Observable<ChargeChapterBean> isChargeChapter(@Body RequestBody info);

    //novelapi/novelOAService/novelDetail/addConsumeRecord  自动订阅下一章


//                  /*          ctrl+shift+/    BookShowBean   */
//
//    //上传文件      FileBean
//    @Multipart
//    @POST("noteAppFileService/file/uploadFile")
//    Observable<FileBean> uploadFile(@Part MultipartBody.Part MultipartFile);
//
//    //多文件上传    @Part() List<MultipartBody.Part> parts
//    @Multipart
//    @POST("noteAppFileService/file/uploadFileList")
//    Observable<String> uploadMultiFile(@Part() List<MultipartBody.Part> parts);
//
//    //下载文件   ,@Body RequestBody info
//    @Streaming //大文件时要加不然会OOM
//    @GET
//    Observable<ResponseBody> fileDownload(@Url String url);


}
