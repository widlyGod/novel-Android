package com.novel.cn.model.api;



/**
 * Created by YUNW-01 on 2017/10/18.
 */

public interface ApiService {


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
