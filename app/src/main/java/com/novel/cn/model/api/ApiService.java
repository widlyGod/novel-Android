package com.novel.cn.model.api;


import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BaseListObjectBean;
import com.novel.cn.model.entity.BaseObjectBean;
import com.novel.cn.model.entity.HomeReturnBean;
import com.novel.cn.model.entity.PersonDataBean;
import com.novel.cn.model.entity.QueryUpayBean;
import com.novel.cn.model.entity.UserBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
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

//
//                  /*  ctrl+shift+/                          新建笔记                                   */
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
