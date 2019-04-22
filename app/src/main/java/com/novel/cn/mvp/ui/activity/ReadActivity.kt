package com.novel.cn.mvp.ui.activity

import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.LogUtils

import com.novel.cn.di.component.DaggerReadComponent
import com.novel.cn.di.module.ReadModule
import com.novel.cn.mvp.contract.ReadContract
import com.novel.cn.mvp.presenter.ReadPresenter

import com.novel.cn.R
import com.novel.cn.app.click
import com.novel.cn.app.isVisible
import com.novel.cn.app.visible
import com.novel.cn.mvp.model.entity.ChapterBean
import com.novel.cn.mvp.model.entity.Volume
import com.novel.cn.mvp.ui.adapter.ChapterAdapter
import com.novel.cn.mvp.ui.dialog.ReadSettingDialog
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.read.PageLoader
import com.novel.cn.view.read.ReadView
import com.novel.cn.view.read.TxtChapter
import kotlinx.android.synthetic.main.layout_menu_chapter.*
import kotlinx.android.synthetic.main.activity_read.*
import kotlinx.android.synthetic.main.layout_header_volume.*
import kotlinx.android.synthetic.main.layout_header_volume.view.*
import java.util.ArrayList


class ReadActivity : BaseActivity<ReadPresenter>(), ReadContract.View {


    private val mBookId by lazy { intent.getStringExtra("bookId") }

    private val mSettingDialog by lazy {
        ReadSettingDialog(this).apply {
            setOnDismissListener {
                hideSystemBar()
            }
        }
    }

    private val mPageLoader by lazy { readView.pageLoader }

    private val mAdapter by lazy { ChapterAdapter() }


    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerReadComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .readModule(ReadModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_read
    }


    override fun initData(savedInstanceState: Bundle?) {
        StatusBarUtils.immersive(this)
        StatusBarUtils.setPaddingSmart(this, toolbar)

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        drawerLayout.isFocusableInTouchMode = false
        recyclerView.adapter = mAdapter
        val header = LayoutInflater.from(this).inflate(R.layout.layout_header_volume, recyclerView, false)
        mAdapter.addHeaderView(header)


        readView.setOnTouchListener(object : ReadView.OnTouchListener {
            override fun onMenuClick() {
                toggleMenu()
            }

            override fun onClick() {
                if (toolbar.isVisible()) {
                    hideSystemBar()
                }
            }
        })
        mPageLoader.setOnPageChangeListener(object : PageLoader.OnPageChangeListener {
            override fun onChapterChange(pos: Int) {

            }

            override fun requestChapters(requestChapters: MutableList<TxtChapter>?) {
                LogUtils.warnInfo("===================request")
            }

            override fun onCategoryFinish(chapters: MutableList<TxtChapter>?) {
            }

            override fun onPageCountChange(count: Int) {
            }

            override fun onPageChange(pos: Int) {
            }

        })
        mPageLoader.openChapter()
        mPresenter?.getVolumeList(mBookId)
        click(tv_content, tv_setting, tv_catalogue) {
            when (it) {
                tv_catalogue -> {
                    toggleMenu()
                    drawerLayout.openDrawer(Gravity.LEFT)
                }
                tv_content -> toggleMenu()
                tv_setting -> {
                    ll_bottom_menu.visible(false)
                    mSettingDialog.show()
                }
            }
        }
    }

    override fun showVolume(data: MutableList<Volume>?) {
        tv_volume_title.text = data?.get(0)?.title
        tv_count.text = "共${data?.get(0)?.chapterNum}章"
    }

    override fun showChapterList(data: ChapterBean) {
        mAdapter.setNewData(data.list)
        val list = ArrayList<TxtChapter>(data.list.size)
        data.list.forEach {
            val txt = TxtChapter()
            txt.bookId = it.id
            txt.title = it.title
            list.add(txt)
        }

        mPageLoader.setChapterList(list)
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
}
