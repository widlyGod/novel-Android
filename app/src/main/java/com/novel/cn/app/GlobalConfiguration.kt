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
package com.novel.cn.app

import android.app.Application
import android.content.Context
import android.support.v4.app.FragmentManager
import com.jess.arms.base.delegate.AppLifecycles
import com.jess.arms.di.module.GlobalConfigModule
import com.jess.arms.http.imageloader.glide.GlideImageLoaderStrategy
import com.jess.arms.http.log.RequestInterceptor
import com.jess.arms.integration.ConfigModule
import com.novel.cn.BuildConfig
import com.novel.cn.app.gson.BooleanAdapter
import com.novel.cn.app.gson.StringAdapter
import com.novel.cn.mvp.model.api.Api
import me.jessyan.progressmanager.ProgressManager
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import java.util.concurrent.TimeUnit


class GlobalConfiguration : ConfigModule {
    //    public static String sDomain = Api.APP_DOMAIN;

    override fun applyOptions(context: Context, builder: GlobalConfigModule.Builder) {
        if (!BuildConfig.LOG_DEBUG) { //Release 时, 让框架不再打印 Http 请求和响应的信息
            builder.printHttpLogLevel(RequestInterceptor.Level.NONE)
        }
        builder.baseurl(Api.APP_DOMAIN)
                .imageLoaderStrategy(GlideImageLoaderStrategy())
                //这里提供一个全局处理 Http 请求和响应结果的处理类, 可以比客户端提前一步拿到服务器返回的结果, 可以做一些操作, 比如 Token 超时后, 重新获取 Token
                .globalHttpHandler(GlobalHttpHandlerImpl(context))
                .responseErrorListener(ResponseErrorListenerImpl())
                .gsonConfiguration {//这里可以自己自定义配置 Gson 的参数
                    context1, gsonBuilder ->
                    gsonBuilder
                            .serializeNulls()//支持序列化值为 null 的参数
                            .enableComplexMapKeySerialization()//支持将序列化 key 为 Object 的 Map, 默认只能序列化 key 为 String 的 Map
                            .registerTypeAdapter(String::class.java, StringAdapter()) //String 类型null 改成 空字符串
                            .registerTypeAdapter(Boolean::class.java, BooleanAdapter()) //Boolean类型判断
                }
                .retrofitConfiguration {//这里可以自己自定义配置 Retrofit 的参数, 甚至您可以替换框架配置好的 OkHttpClient 对象 (但是不建议这样做, 这样做您将损失框架提供的很多功能)
                    context1, retrofitBuilder ->
                    //                    retrofitBuilder.addConverterFactory(FastJsonConverterFactory.create());//比如使用 FastJson 替代 Gson
                }
                .okhttpConfiguration {//这里可以自己自定义配置 Okhttp 的参数
                    context1, okhttpBuilder ->
                    //                    okhttpBuilder.sslSocketFactory(); //支持 Https, 详情请百度
                    okhttpBuilder.readTimeout(30, TimeUnit.SECONDS)
                    okhttpBuilder.writeTimeout(30, TimeUnit.SECONDS)
                    okhttpBuilder.connectTimeout(30, TimeUnit.SECONDS)
                    //使用一行代码监听 Retrofit／Okhttp 上传下载进度监听,以及 Glide 加载进度监听, 详细使用方法请查看 https://github.com/JessYanCoding/ProgressManager
                    ProgressManager.getInstance().with(okhttpBuilder)
                    //让 Retrofit 同时支持多个 BaseUrl 以及动态改变 BaseUrl, 详细使用方法请查看 https://github.com/JessYanCoding/RetrofitUrlManager
                    RetrofitUrlManager.getInstance().with(okhttpBuilder)
                }
                .rxCacheConfiguration {//这里可以自己自定义配置 RxCache 的参数
                    context1, rxCacheBuilder ->
                    rxCacheBuilder.useExpiredDataIfLoaderNotAvailable(true)
                    //想自定义 RxCache 的缓存文件夹或者解析方式, 如改成 FastJson, 请 return rxCacheBuilder.persistence(cacheDirectory, new FastJsonSpeaker());
                    //否则请 return null;
                    null
                }
    }

    override fun injectAppLifecycle(context: Context, lifecycles: MutableList<AppLifecycles>) {
        //AppLifecycles 中的所有方法都会在基类 Application 的对应生命周期中被调用, 所以在对应的方法中可以扩展一些自己需要的逻辑
        //可以根据不同的逻辑添加多个实现类
        lifecycles.add(AppLifecyclesImpl())
    }

    override fun injectActivityLifecycle(context: Context, lifecycles: MutableList<Application.ActivityLifecycleCallbacks>) {
        //ActivityLifecycleCallbacks 中的所有方法都会在 Activity (包括三方库) 的对应生命周期中被调用, 所以在对应的方法中可以扩展一些自己需要的逻辑
        //可以根据不同的逻辑添加多个实现类
        lifecycles.add(ActivityLifecycleCallbacksImpl())
    }

    override fun injectFragmentLifecycle(context: Context, lifecycles: MutableList<FragmentManager.FragmentLifecycleCallbacks>) {
        //FragmentLifecycleCallbacks 中的所有方法都会在 Fragment (包括三方库) 的对应生命周期中被调用, 所以在对应的方法中可以扩展一些自己需要的逻辑
        //可以根据不同的逻辑添加多个实现类
        lifecycles.add(FragmentLifecycleCallbacksImpl())
    }


}
