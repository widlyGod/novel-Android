package com.novel.cn.mvp.ui.activity

import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent

import com.novel.cn.di.component.DaggerReadComponent
import com.novel.cn.di.module.ReadModule
import com.novel.cn.mvp.contract.ReadContract
import com.novel.cn.mvp.presenter.ReadPresenter

import com.novel.cn.R
import com.novel.cn.app.click
import com.novel.cn.app.isVisible
import com.novel.cn.app.visible
import com.novel.cn.ext.ETextWatcher
import com.novel.cn.ext.dp2px
import com.novel.cn.ext.textWatcher
import com.novel.cn.ext.toast
import com.novel.cn.mvp.model.entity.ChapterBean
import com.novel.cn.mvp.model.entity.ChapterInfo
import com.novel.cn.mvp.model.entity.ChapterInfo2
import com.novel.cn.mvp.model.entity.Volume
import com.novel.cn.mvp.ui.adapter.ChapterAdapter
import com.novel.cn.mvp.ui.dialog.MorePopup
import com.novel.cn.mvp.ui.dialog.ReadSettingDialog
import com.novel.cn.mvp.ui.dialog.VolumePopup
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.readpage.*
import kotlinx.android.synthetic.main.layout_menu_chapter.*
import kotlinx.android.synthetic.main.activity_read.*
import kotlinx.android.synthetic.main.layout_header_volume.*

import java.util.ArrayList


class ReadActivity : BaseActivity<ReadPresenter>(), ReadContract.View {



    private val mBookId by lazy { intent.getStringExtra("bookId") }

    private val mSettingDialog by lazy {
        ReadSettingDialog(this).apply {
            setOnDismissListener {
                hideSystemBar()
            }
            setOnSettingChanageListener(object : ReadSettingDialog.OnSettingChangeListener {
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

    private val mPageLoader by lazy { readView.getPageLoader(mBookId) }

    private val mAdapter by lazy { ChapterAdapter() }

    private var mVolume: Volume? = null

    private val mPopup by lazy { VolumePopup(this) }


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
        mAdapter.bindToRecyclerView(recyclerView)
        val header = LayoutInflater.from(this).inflate(R.layout.layout_header_volume, recyclerView, false)
        header.setOnClickListener {
            mPopup.showAsDropDown(it, dp2px(20), 0)
        }

        mAdapter.addHeaderView(header)

        mAdapter.setOnItemClickListener { adapter, view, position ->
            mPageLoader.skipToChapter(position)
            drawerLayout.closeDrawer(Gravity.LEFT)
        }

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
                if (!prev) {
                    toast("没有上一页了")
                }
            }

            override fun nextPage(next: Boolean) {
                if (!next) {
                    toast("没有下一页了")
                }
            }

            override fun cancel() {
            }

        })

        mPageLoader.setOnPageChangeListener(object : PageLoader.OnPageChangeListener {
            override fun onChapterChange(pos: Int) {
                mAdapter.setCurrentPosition(pos)
                val item = mAdapter.getItem(pos) as ChapterInfo
                tv_chapter_name.text = "${item.chapter}：${item.title}"
                tv_chapter_info.text = "${item.chapter}章节/${mAdapter.itemCount}章节"
                toolbar_title.text = "第${item.chapter}章 ${item.title}"
            }

            override fun requestChapters(requestChapters: MutableList<TxtChapter>?) {
                  mPresenter?.readNovel(requestChapters, mBookId)
            }

            override fun onCategoryFinish(chapters: MutableList<TxtChapter>?) {
            }

            override fun onPageCountChange(count: Int) {
            }

            override fun onPageChange(pos: Int) {
            }
        })
        mPresenter?.getVolumeList(mBookId)
        click(tv_content, tv_setting, tv_catalogue, iv_pre, iv_next) {
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
                iv_next -> mPageLoader.skipNextChapter()
                iv_pre -> mPageLoader.skipPreChapter()
            }
        }
    }

    override fun loadChapterSuccess(chapterInfo: ChapterInfo2) {
        if (mPageLoader.pageStatus == PageLoader.STATUS_LOADING) {
            mPageLoader.openChapter()
        }
    }

    override fun showVolume(data: MutableList<Volume>?) {
        mVolume = data?.get(0)
        tv_volume_title.text = data?.get(0)?.title
        tv_count.text = "共${data?.get(0)?.chapterNum}章"
        mPopup.setData(data)

    }

    override fun showChapterList(volume: String?, data: ChapterBean) {

        mAdapter.setNewData(data.list)
        val list = ArrayList<TxtChapter>(data.list.size)
        data.list.forEach {
            val txt = TxtChapter()
            txt.bookId = mBookId
            txt.link = it.id
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

    override fun onDestroy() {
        mPageLoader.closeBook()
        super.onDestroy()
    }
}
