package com.novel.cn.mvp.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import com.isseiaoki.simplecropview.CropImageView
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.novel.cn.R
import com.novel.cn.di.component.DaggerLoginComponent
import com.novel.cn.di.module.LoginModule
import com.novel.cn.ext.applySchedulers
import com.novel.cn.ext.bindToLifecycle
import com.novel.cn.ext.handleError
import com.novel.cn.mvp.presenter.NothingPresenter
import kotlinx.android.synthetic.main.activity_crop_image.*
import java.io.File

/**
 * Created by hy on 2019/2/18
 */
class CropImageActivity : BaseActivity<NothingPresenter>() {

    override fun setupActivityComponent(appComponent: AppComponent) {

    }

    override fun initView(savedInstanceState: Bundle?) = R.layout.activity_crop_image

    override fun initData(savedInstanceState: Bundle?) {
        val sourceUri: Uri = Uri.fromFile(File(intent.getStringExtra("imageUri")))

        val saveUri = Uri.fromFile(File(cacheDir, "temp_header.jpg"))

        cropImageView.apply {
            setCropMode(CropImageView.CropMode.CIRCLE_SQUARE)
            setCompressFormat(Bitmap.CompressFormat.JPEG)
        }
        cropImageView.load(sourceUri).executeAsCompletable()
                .applySchedulers(this)
                .subscribe({}, {})
                .bindToLifecycle(this)

        toolbar_back.clicks().subscribe {
            finish()
        }.bindToLifecycle(this)

        tv_sub.clicks().subscribe {
            cropImageView.crop(sourceUri)
                    .executeAsSingle()
                    .flatMap {
                        cropImageView.save(it).executeAsSingle(saveUri)
                    }
                    .applySchedulers(this)
                    .subscribe({
                        var intent = Intent()
                        intent.putExtra("saveUri", it)
                        setResult(1, intent)
                        finish()
                    }, {
                        handleError(it, this@CropImageActivity, "裁剪失败${it.message}")
                    }).bindToLifecycle(this)
        }.bindToLifecycle(this)

    }


}