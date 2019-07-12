package com.novel.cn.mvp.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.SeekBar
import com.ess.filepicker.model.EssFile
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.novel.cn.R
import com.novel.cn.app.click
import com.novel.cn.app.isVisible
import com.novel.cn.app.visible
import com.novel.cn.ext.bindToLifecycle
import com.novel.cn.ext.getCompactDrawable
import com.novel.cn.mvp.model.entity.Book
import com.novel.cn.mvp.model.entity.Calalogue
import com.novel.cn.mvp.presenter.NothingPresenter
import com.novel.cn.mvp.ui.adapter.ChapterAdapter
import com.novel.cn.mvp.ui.dialog.ReadSettingDialog
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.readpage.*
import kotlinx.android.synthetic.main.activity_local_read.*
import kotlinx.android.synthetic.main.activity_local_read.drawerLayout
import kotlinx.android.synthetic.main.activity_local_read.iv_next
import kotlinx.android.synthetic.main.activity_local_read.iv_night_mode
import kotlinx.android.synthetic.main.activity_local_read.iv_pre
import kotlinx.android.synthetic.main.activity_local_read.ll_bottom_menu
import kotlinx.android.synthetic.main.activity_local_read.readView
import kotlinx.android.synthetic.main.activity_local_read.seekbar
import kotlinx.android.synthetic.main.activity_local_read.toolbar
import kotlinx.android.synthetic.main.activity_local_read.toolbar_title
import kotlinx.android.synthetic.main.activity_local_read.tv_catalogue
import kotlinx.android.synthetic.main.activity_local_read.tv_chapter_info
import kotlinx.android.synthetic.main.activity_local_read.tv_setting
import kotlinx.android.synthetic.main.layout_menu_chapter.*
import kotlinx.android.synthetic.main.layout_shoufei.*

class LocalReadActivity : BaseActivity<NothingPresenter>() {

    private val book by lazy { intent.getParcelableExtra<Book>("Book") }

    private val mAdapter by lazy { ChapterAdapter() }

    private val mPageLoader by lazy { readView.getPageLoader(book.mFilePath, true) }
    private var isSequence = true
    private var mChapterList = mutableListOf<TxtChapter>()

    private val mSettingDialog by lazy {
        ReadSettingDialog(this).apply {
            setOnDismissListener {
                hideSystemBar()
            }
            setOnSettingChanageListener(object : ReadSettingDialog.OnSettingChangeListener {
                override fun onTextFont(font: Typeface) {
                    mPageLoader.setTextFont(font)
                }

                override fun onPageStyle(style: PageStyle) {
                    mPageLoader.setPageStyle(style)
                }

                override fun onPageMode(mode: PageMode) {
                }

                override fun onTextSize(textSize: Int) {
                    mPageLoader.setTextSize(textSize)
                }

            })
        }
    }

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
            bookId = book.mFilePath
            title = book.novelTitle
            filePath = book.mFilePath
        })
        mPageLoader.setChapterList(chapterList)
        recyclerView.adapter = mAdapter
        tv_chapter_name.text = book.novelTitle
        mAdapter.setOnItemClickListener { _, _, position ->
            if (isSequence)
                mPageLoader.skipToChapter(position)
            else
                mPageLoader.skipToChapter(mAdapter.data.size - 1 - position)
            drawerLayout.closeDrawer(Gravity.LEFT)
        }
        mPageLoader.setOnPageChangeListener(object : PageLoader.OnPageChangeListener {
            override fun onChapterChange(pos: Int) {
                if (isSequence)
                    mAdapter.setCurrentPosition(pos)
                else
                    mAdapter.setCurrentPosition(mAdapter.data.size - 1 - pos)
                seekbar.progress = pos
                tv_chapter_info.text = mChapterList[pos].title
            }

            override fun requestChapters(requestChapters: MutableList<TxtChapter>?) {

            }

            @SuppressLint("CheckResult")
            override fun onCategoryFinish(chapters: MutableList<TxtChapter>) {
                mChapterList = chapters
                tv_chapter_info.text = chapters[0].title
                var list = mutableListOf<Calalogue>()
                var i = 0
                chapters.forEach {
                    list.add(Calalogue(chapter = i, chapterTitle = it.title,isLocal = true))
                    i++
                }
                mAdapter.setNewData(list)
                iv_rank_type.clicks().subscribe {
                    var calalogue = mutableListOf<Calalogue>()
                    isSequence = if (isSequence) {
                        calalogue.clear()
                        calalogue.addAll(list.sortedByDescending { it.chapter })
                        mAdapter.setNewData(calalogue)
                        false
                    } else {
                        calalogue.clear()
                        calalogue.addAll(list.sortedBy { it.chapter })
                        mAdapter.setNewData(calalogue)
                        true
                    }
                    iv_rank_type.setImageDrawable(if (isSequence) {
                        getCompactDrawable(R.drawable.ic_rank_down)
                    } else getCompactDrawable(R.drawable.ic_rank_up))
                    mAdapter.setCurrentPosition(mAdapter.data.size - 1 -  mAdapter.getCurrentPosition())
                }
                seekbar.max = chapters.size - 1
                seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                        mPageLoader.skipToChapter(seekBar!!.progress)
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    }

                    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    }

                })
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
                if (toolbar.isVisible()) {
                    hideSystemBar()
                    return false
                }
                return true
            }


            override fun center() {
                toggleMenu()
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
        refreshNightMode()
        toolbar_title.text = book.novelTitle
        click(tv_setting, iv_night_mode, iv_pre, iv_next, tv_catalogue) {
            when (it) {
                tv_setting -> {
                    ll_bottom_menu.visible(false)
                    mSettingDialog.show()
                }
                iv_night_mode -> {
                    ReadSettingManager.getInstance().isNightMode = !ReadSettingManager.getInstance().isNightMode
                    refreshNightMode()
                }
                tv_catalogue, tv_contents -> {
                    toggleMenu()
                    drawerLayout.openDrawer(Gravity.LEFT)
                }
                iv_next -> mPageLoader.skipNextChapter()
                iv_pre -> mPageLoader.skipPreChapter()
            }
        }
    }

    private fun toggleMenu() {
        if (toolbar.isVisible()) {
            //close
            hideSystemBar()
        } else {
            showSystemBar()
        }
    }

    private fun hideSystemBar() {
        //隐藏
        toolbar.visible(false)
        ll_bottom_menu.visible(false)
        val attrs = window.attributes
        attrs.flags = attrs.flags or WindowManager.LayoutParams.FLAG_FULLSCREEN
        window.attributes = attrs
    }

    private fun showSystemBar() {
        //显示
        toolbar.visible(true)
        ll_bottom_menu.visible(true)
        val attrs = window.attributes
        attrs.flags = attrs.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
        window.attributes = attrs
    }

    override fun onDestroy() {
        mPageLoader.closeBook()
        super.onDestroy()
    }

    private fun refreshNightMode() {
        val isNightMode = ReadSettingManager.getInstance().isNightMode
        mPageLoader.setNightMode(isNightMode)
        if (isNightMode) {
            iv_night_mode.setImageResource(R.drawable.ic_sun)
        } else {
            iv_night_mode.setImageResource(R.drawable.ic_night)
        }
    }

}