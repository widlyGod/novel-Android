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

import com.novel.cn.mvp.model.entity.BaseResponse
import com.novel.cn.mvp.model.entity.MessageBean
import com.novel.cn.mvp.model.entity.SignIn
import com.novel.cn.mvp.model.entity.UserInfo
import io.reactivex.Observable
import retrofit2.http.*


interface UserService {

    /**
     * 登录
     */
    @POST("novelUserService/user/login")
    fun login(@Body map: HashMap<String, String>): Observable<BaseResponse<UserInfo>>

    /**
     * 第三方登录
     */
    @POST
    fun loginThrid(@Url url: String, @Body params: HashMap<String, String>): Observable<BaseResponse<UserInfo>>

    /**
     * 注册
     */
    @POST("novelUserService/user/registerFromApp")
    fun regist(@Body params: HashMap<String, String>): Observable<BaseResponse<UserInfo>>

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
    @POST("novelapi/novelUserService/sign/in")
    fun signIn(@Body params: HashMap<String, String>): Observable<BaseResponse<Any>>

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

}
