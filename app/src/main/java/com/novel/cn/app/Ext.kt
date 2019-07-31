package com.novel.cn.app

import android.content.Context
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.jess.arms.http.imageloader.ImageConfig
import com.jess.arms.http.imageloader.glide.GlideArms
import com.jess.arms.http.imageloader.glide.ImageConfigImpl
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.novel.cn.R
import com.novel.cn.ext.setVisible
import me.jessyan.progressmanager.ProgressListener
import me.jessyan.progressmanager.ProgressManager
import me.jessyan.progressmanager.body.ProgressInfo

fun click(vararg views: View, method: (view: View) -> Unit) {
    for (item in views) {
        item.setOnClickListener {
            method.invoke(it)
        }
    }
}


fun Any?.isNull(): Boolean {
    return this == null
}

fun List<*>?.isNullOrEmpty(): Boolean {
    return this == null || this.isEmpty()
}


fun View.isVisible(): Boolean {
    return this.visibility == View.VISIBLE
}

fun View.visible(isVisible: Boolean) {
    this.visibility = if (isVisible) VISIBLE else GONE
}

fun ImageView.loadImage(url: Any?) {
    loadImage(url, R.drawable.img_default_book_txt, R.drawable.img_default_book_txt)
}

fun ImageView.loadHeadImage(url: Any?) {
    loadImage(url, R.drawable.img_default_user_head, R.drawable.img_default_user_head)
}

fun Context.loadImage(config: ImageConfig) {
    ArmsUtils.obtainAppComponentFromContext(this)
            .imageLoader()
            .loadImage(this, config)
}

fun ImageView.loadImage(url: Any?, placeholder: Int, error: Int) {
    ArmsUtils.obtainAppComponentFromContext(this.context)
            .imageLoader()
            .loadImage(this.context, ImageConfigImpl.builder()
                    .url(url)
                    .placeholder(placeholder)
                    .cacheStrategy(DiskCacheStrategy.DATA)
                    .errorPic(error)
                    .imageView(this)
                    .build())
}
