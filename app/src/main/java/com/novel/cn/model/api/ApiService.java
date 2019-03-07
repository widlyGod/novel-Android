package com.novel.cn.model.api;


import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BaseObjectBean;
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

    //登录，注册，忘记密码
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




    //微信，支付宝后台接口
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("novelOAService/upayCenter/upayCenterRecharge")
    Observable<String> upayCenterRecharge(@Body RequestBody info);
    //老界面查询用户个人信息
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @POST("novelOAService/accountCenter/queryPersonAccount")
    Observable<QueryUpayBean> queryUpayCenter();


    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("novelUserService/user/logout")
    Observable<BaseBean> logout();



}
