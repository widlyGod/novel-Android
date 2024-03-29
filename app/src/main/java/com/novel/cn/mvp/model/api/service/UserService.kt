/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.novel.cn.mvp.model.api.service

import com.jess.arms.mvp.BasePresenter
import com.novel.cn.mvp.model.entity.*
import io.reactivex.Observable
import retrofit2.http.*


interface UserService {

    /**
     * 登录
     */
    @POST("novelUserService/user/login")
    fun login(@Body map: HashMap<String, String>): Observable<BaseResponse<LoginInfo>>

    /**
     * 第三方登录
     */
    @POST
    fun loginThrid(@Url url: String, @Body params: HashMap<String, String>): Observable<BaseResponse<LoginInfo>>

    /**
     * 注册
     */
    @POST("novelUserService/user/registerFromApp")
    fun regist(@Body params: HashMap<String, String>): Observable<BaseResponse<LoginInfo>>

    /**
     * 验证昵称
     */
    @POST("novelUserService/user/checkNickName")
    fun checkNickName(@Body params: HashMap<String, String>): Observable<BaseResponse<Any>>

    /**
     * 发送验证码
     * URL
     *      注册：novelUserService/user/sendCode
     *      找回：
     */
    @POST
    fun sendCode(@Url url: String, @Body params: HashMap<String, String>): Observable<BaseResponse<Any>>

    /**
     * 忘记密码
     */
    @POST("novelUserService/user/forgetPwd")
    fun resetPassword(@Body params: HashMap<String, String>): Observable<BaseResponse<Any>>

    /**
     * 签到
     */
    @GET("novelAppService/signIn/in")
    fun signIn(): Observable<BaseResponse<Signinbean>>

    /**
     * 是否签到
     */
    @GET("novelUserService/sign/validSignIn")
    fun validateSignIn(): Observable<BaseResponse<SignIn>>

    /**
     * 我的消息
     */

    @POST("novelOAService/novelMessage/getNovelMessageList")
    fun getMessasgeList(@Body params: HashMap<String, String>): Observable<BaseResponse<MessageBean>>

    /**
     * 我的消息
     */

    @POST("novelOAService/novelMessage/updateBatchMessageStatus")
    fun messageRead(@Body params: HashMap<String, String>): Observable<BaseResponse<Any>>

    /**
     * 我的购买
     */

    @POST("novelAppService/personalCenter/myBuy")
    fun getMyBill(@Body params: HashMap<String, Any>): Observable<BaseResponse<MyBillBean>>

    /**
     * 获取用户信息
     */
    @GET("novelAppService/personalCenter/personalInfo")
    fun getUserInfo(): Observable<BaseResponse<User>>

    /**
     * 用户信息修改
     */
    @POST("novelAppService/personalCenter/updatePersonInfo")
    fun modifyUserInfo(@Body params: HashMap<String, Any?>): Observable<BaseResponse<Any>>

    /**
     * 获取用户优惠券
     */
    @POST("novelAppService/coupon/selectCoupon")
    fun getUserCoupon(@Body params: HashMap<String, String>): Observable<BaseResponse<List<CouponBean>>>

    /**
     * 充值vip
     */
    @POST("novelAppService/vip/becomeVip")
    fun vipPay(@Body params: HashMap<String, String>): Observable<BaseResponse<PayInfoBean>>

    /**
     * 上传使用时长
     */
    @GET("novelAppService/personalCenter/updateLoginTime")
    fun updateLoginTime(@Query("userId") userId: String, @Query("loginTime") loginTime: Long): Observable<BaseResponse<Any>>

    /**
     * 上传阅读时长
     */
    @POST("novelUserService/personal/saveWeeklyReadTime")
    fun updateReadTime(@Body params: HashMap<String, Any?>): Observable<BaseResponse<Any>>

}
