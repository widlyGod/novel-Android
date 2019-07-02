package com.novel.cn.mvp.ui.activity

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.WindowManager
import com.ess.filepicker.model.EssFile
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.novel.cn.R
import com.novel.cn.app.click
import com.novel.cn.app.isVisible
import com.novel.cn.app.visible
import com.novel.cn.mvp.presenter.NothingPresenter
import com.novel.cn.mvp.ui.dialog.ReadSettingDialog
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.readpage.*
import kotlinx.android.synthetic.main.activity_local_read.*
import kotlinx.android.synthetic.main.activity_local_read.iv_night_mode
import kotlinx.android.synthetic.main.activity_local_read.ll_bottom_menu
import kotlinx.android.synthetic.main.activity_local_read.readView
import kotlinx.android.synthetic.main.activity_local_read.toolbar
import kotlinx.android.synthetic.main.activity_local_read.toolbar_title
import kotlinx.android.synthetic.main.activity_local_read.tv_setting
import kotlinx.android.synthetic.main.activity_read.*

class LocalReadActivity : BaseActivity<NothingPresenter>() {

    private val essFile by lazy { intent.getParcelableExtra<EssFile>("essFile") }

    private val mPageLoader by lazy { readView.getPageLoader(essFile.absolutePath, true) }

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
        toolbar_title.text = essFile.name
        click(tv_setting, iv_night_mode) {
            when (it) {
                tv_setting -> {
                    ll_bottom_menu.visible(false)
                    mSettingDialog.show()
                }
                iv_night_mode -> {
                    ReadSettingManager.getInstance().isNightMode = !ReadSettingManager.getInstance().isNightMode
                    refreshNightMode()
                }
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