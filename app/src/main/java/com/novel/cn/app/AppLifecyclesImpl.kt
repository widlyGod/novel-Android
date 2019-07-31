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
import android.graphics.Bitmap
import android.util.Log
import android.view.View
import android.widget.ImageView
import com.didichuxing.doraemonkit.DoraemonKit

import com.jess.arms.base.delegate.AppLifecycles
import com.jess.arms.integration.cache.IntelligentCache
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.lzy.ninegrid.CircleProgressView
import com.lzy.ninegrid.NineGridView
import com.lzy.ninegrid.preview.ImagePreviewAdapter
import com.mob.MobSDK
import com.novel.cn.BuildConfig
import com.novel.cn.R
import com.novel.cn.db.DbManager
import com.novel.cn.ext.setVisible
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import com.tencent.bugly.Bugly
import com.zchu.rxcache.RxCache
import com.zchu.rxcache.diskconverter.GsonDiskConverter
import me.jessyan.progressmanager.ProgressListener
import me.jessyan.progressmanager.ProgressManager
import me.jessyan.progressmanager.body.ProgressInfo

import timber.log.Timber
import java.io.File

/**
 * ================================================
 * 展示 [AppLifecycles] 的用法
 *
 *
 * Created by JessYan on 04/09/2017 17:12
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
class AppLifecyclesImpl : AppLifecycles {

    override fun attachBaseContext(base: Context) {
        //          MultiDex.install(base);  //这里比 onCreate 先执行,常用于 MultiDex 初始化,插件化框架的初始化
    }

    override fun onCreate(application: Application) {
        if (LeakCanary.isInAnalyzerProcess(application)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        if (BuildConfig.LOG_DEBUG) {//Timber初始化
            //Timber 是一个日志框架容器,外部使用统一的Api,内部可以动态的切换成任何日志框架(打印策略)进行日志打印
            //并且支持添加多个日志框架(打印策略),做到外部调用一次 Api,内部却可以做到同时使用多个策略
            //比如添加三个策略,一个打印日志,一个将日志保存本地,一个将日志上传服务器
            Timber.plant(Timber.DebugTree())
            // 如果你想将框架切换为 Logger 来打印日志,请使用下面的代码,如想切换为其他日志框架请根据下面的方式扩展
            //                    Logger.addLogAdapter(new AndroidLogAdapter());
            //                    Timber.plant(new Timber.DebugTree() {
            //                        @Override
            //                        protected void log(int priority, String tag, String message, Throwable t) {
            //                            Logger.log(priority, tag, message, t);
            //                        }
            //                    });
        }
        Preference.init(application, "config")
        MobSDK.init(application)
        DbManager.init(application)
        initRxCache(application)
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout -> ClassicsHeader(application) }
        //LeakCanary 内存泄露检查
        //使用 IntelligentCache.KEY_KEEP 作为 key 的前缀, 可以使储存的数据永久存储在内存中
        //否则存储在 LRU 算法的存储空间中, 前提是 extras 使用的是 IntelligentCache (框架默认使用)
        ArmsUtils.obtainAppComponentFromContext(application).extras()
                .put(IntelligentCache.getKeyOfKeep(RefWatcher::class.java.name), if (BuildConfig.USE_CANARY) LeakCanary.install(application) else RefWatcher.DISABLED)
        Bugly.init(application, "289ea54a9a", false)
        DoraemonKit.install(application)

        NineGridView.setImageLoader(object : NineGridView.ImageLoader {

            override fun onDisplayImage(context: Context, imageView: ImageView, url: String) {
                imageView.loadImage(url, R.color.image_holder, R.drawable.ic_load_image_error)
            }

            override fun onDisplayImagePreview(context: Context, imageView: ImageView, progressed: ImagePreviewAdapter.Progress, url: String) {
                ProgressManager.getInstance().addResponseListener(url.replace(":80", ""), object : ProgressListener {
                    override fun onProgress(progressInfo: ProgressInfo) {
                        val progress = progressInfo.percent
                        LogUtils.warnInfo("aaaaaaaaa$progress")
                        progressed.onProgress(progress)
                        if (progressInfo.isFinish || progress >= 100) {
                            //说明已经加载完成
                            progressed.onSuccess(true)
                        }
                    }

                    override fun onError(id: Long, e: Exception) {
                        progressed.onSuccess(false)
                    }
                })
                imageView.loadImage(url, R.color.image_holder_big, R.drawable.ic_load_image_error)
            }


            override fun getCacheImage(url: String): Bitmap? {
                return null
            }
        })
    }

    override fun onTerminate(application: Application) {

    }

    private fun initRxCache(application: Application) {
        RxCache.initializeDefault(
                RxCache.Builder()
                        .appVersion(1)//当版本号改变,缓存路径下存储的所有数据都会被清除掉
                        .diskDir(File(application.cacheDir.path + File.separator + "data-cache"))
                        .diskConverter(GsonDiskConverter())//支持Serializable、Json(GsonDiskConverter)
                        .memoryMax(20 * 1024 * 1024)
                        .diskMax(200 * 1024 * 1024)
                        .build()
        )
    }
}
