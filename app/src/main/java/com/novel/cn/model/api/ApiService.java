package com.novel.cn.model.api;


import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BaseObjectBean;
import com.novel.cn.model.entity.UserBean;

import okhttp3.RequestBody;
import retrofit2.http.Body;
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

    //替换BaseBean为泛型
//    /*  ctrl+shift+/  sendCode       publicRequest                 登陆注册                                   */
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST
//    Observable<BaseBean> publicRequest(@Url String url, @Body RequestBody info);
//
//    //    @POST("noteAppOAService/user/login")
//    @Headers({"Content-Type: application/json", "Accept: application/json"})
//    @POST("noteAppOAService/user/login")
//    Observable<BaseBean> login(@Body RequestBody info);
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
//
//
//    @Streaming
//    @GET
//    Observable<ResponseBody> downloadApk(@Url String url);
}
