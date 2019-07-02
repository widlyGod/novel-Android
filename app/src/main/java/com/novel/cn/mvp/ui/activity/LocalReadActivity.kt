package com.novel.cn.mvp.ui.activity

import android.content.Context
import android.os.Bundle
import com.ess.filepicker.model.EssFile
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.novel.cn.R
import com.novel.cn.mvp.presenter.NothingPresenter
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.readpage.PageLoader
import com.novel.cn.view.readpage.PageView
import com.novel.cn.view.readpage.TxtChapter
import kotlinx.android.synthetic.main.activity_local_read.*
import kotlinx.android.synthetic.main.activity_local_read.readView
import kotlinx.android.synthetic.main.activity_read.*

class LocalReadActivity : BaseActivity<NothingPresenter>() {

    private val essFile by lazy { intent.getParcelableExtra<EssFile>("essFile") }

    private val mPageLoader by lazy { readView.getPageLoader(essFile.absolutePath,true) }

    override fun setupActivityComponent(appComponent: AppComponent) {

    }

    override fun initStatusBar(savedInstanceState: Bundle?) {
        StatusBarUtils.immersive(this)
        StatusBarUtils.setPaddingSmart(this, toolbar)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_local_read
    }

    override fun initData(savedInstanceState: Bundle?) {
        val chapterList = ArrayList<TxtChapter>()
        chapterList.add(TxtChapter().apply {
            bookId = essFile.absolutePath
            title = essFile.name
            filePath = essFile.absolutePath
        })
        mPageLoader.setChapterList(chapterList)
        mPageLoader.setOnPageChangeListener(object : PageLoader.OnPageChangeListener {
            override fun onChapterChange(pos: Int) {

            }

            override fun requestChapters(requestChapters: MutableList<TxtChapter>?) {

            }

            override fun onCategoryFinish(chapters: MutableList<TxtChapter>?) {

            }

            override fun onPageCountChange(count: Int) {

            }

            override fun onPageChange(pos: Int) {

            }

            override fun noFree(txtChapter: TxtChapter?, mCurChapterPos: Int) {

            }

            override fun locked(txtChapter: TxtChapter?, mCurChapterPos: Int) {

            }

            override fun parseSuccess() {

            }

        })
        readView.setTouchListener(object : PageView.TouchListener {
            override fun onTouch(): Boolean {
                return true
            }

            override fun center() {
                
            }

            override fun prePage(prev: Boolean) {
                
            }

            override fun nextPage(next: Boolean) {
                
            }

            override fun cancel() {
                
            }

            override fun reward() {
                
            }

        })

    }

}