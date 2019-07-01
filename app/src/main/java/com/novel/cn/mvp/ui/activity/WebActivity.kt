package com.novel.cn.mvp.ui.activity

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.novel.cn.BuildConfig
import com.novel.cn.R
import com.novel.cn.app.visible
import com.novel.cn.ext.bindToLifecycle
import com.novel.cn.ext.setGone
import com.novel.cn.ext.setVisible
import com.novel.cn.log.Timber
import com.novel.cn.log.debug
import com.novel.cn.mvp.presenter.NothingPresenter
import com.novel.cn.utils.StatusBarUtils
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView
import kotlinx.android.synthetic.main.activity_crop_image.*
import kotlinx.android.synthetic.main.activity_crop_image.toolbar_back
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.include_title.*
import org.jetbrains.anko.startActivity
import wendu.dsbridge.DWebView

class WebActivity : BaseActivity<NothingPresenter>() {

    override fun setupActivityComponent(appComponent: AppComponent) {

    }

    companion object {
        private const val KEY_URL = "key_url"

        fun actionStart(context: Context, url: String) {
            context.startActivity<WebActivity>(KEY_URL to url)
        }
    }

    private var mUrl = ""

    override fun initStatusBar(savedInstanceState: Bundle?) {
        //白底黑字
        StatusBarUtils.darkMode(this)
//        给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, toolbar)
    }

    override fun initView(savedInstanceState: Bundle?) = R.layout.activity_web

    override fun initData(savedInstanceState: Bundle?) {
        loadArguments()

        // 设置调试模式，发生一些错误时，将会以弹窗形式提示
        DWebView.setWebContentsDebuggingEnabled(BuildConfig.LOG_DEBUG)
        web_view.apply {
            webChromeClient = MyWebChromeClient()
            loadUrl(mUrl)
//            loadUrl("file:///android_asset/js-call-native.html")
        }

        Timber.debug { mUrl }
        toolbar_back.clicks().subscribe {
            if (web_view.canGoBack()) {
                web_view.goBack()
                return@subscribe
            }
            finish()
        }.bindToLifecycle(this)
    }

    private fun loadArguments() {
        intent?.apply {
            mUrl = getStringExtra(KEY_URL) ?: ""
        }

    }

    override fun onBackPressed() {
        // 处理按返回按钮时返回上一页
        if (web_view.canGoBack()) {
            web_view.goBack()
            return
        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()

        web_view.apply {
            stopLoading()
            webChromeClient = null
            destroy()
        }
    }

    inner class MyWebChromeClient : WebChromeClient() {
        /**
         * 获取进度
         */
        override fun onProgressChanged(view: WebView, newProgress: Int) {
            if (isDestroyed || isFinishing)
                return
            super.onProgressChanged(view, newProgress)
            if (newProgress >= 100) {
                progress_bar.setGone() // 加载完网页进度条消失
            } else {
                progress_bar.setVisible() // 开始加载网页时显示进度条
                progress_bar.progress = newProgress // 设置进度值
            }
        }

        override fun onReceivedTitle(p0: WebView?, p1: String?) {
            super.onReceivedTitle(p0, p1)
            toolbar_title.text = p1
            toolbar_title.typeface = Typeface.createFromAsset(assets, "fonts/FZQKBYSJW.TTF")

        }
    }
}