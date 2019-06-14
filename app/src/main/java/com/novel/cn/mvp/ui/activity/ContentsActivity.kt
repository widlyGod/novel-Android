package com.novel.cn.mvp.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.LogUtils
import com.novel.cn.R
import com.novel.cn.app.JumpManager
import com.novel.cn.db.DbManager
import com.novel.cn.db.Readcord
import com.novel.cn.di.component.DaggerContentsComponent
import com.novel.cn.di.module.ContentsModule
import com.novel.cn.ext.*
import com.novel.cn.mvp.contract.ContentsContract
import com.novel.cn.mvp.model.entity.*
import com.novel.cn.mvp.presenter.ContentsPresenter
import com.novel.cn.mvp.ui.adapter.ChapterAdapter
import com.novel.cn.mvp.ui.dialog.PageChoosePopup
import com.novel.cn.mvp.ui.dialog.VolumePopup
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.VolumeView
import com.novel.cn.view.decoration.LinearItemDecoration
import com.novel.cn.view.readpage.TxtChapter
import kotlinx.android.synthetic.main.activity_contents.*
import kotlinx.android.synthetic.main.include_title.toolbar
import kotlinx.android.synthetic.main.layout_foot_volume.view.*
import kotlinx.android.synthetic.main.layout_header_volume.*
import kotlinx.android.synthetic.main.layout_header_volume.view.*


class ContentsActivity : BaseActivity<ContentsPresenter>(), ContentsContract.View, VolumeView {


    private val mPopup by lazy { VolumePopup(this, this) }
    private val mPagePopup by lazy { PageChoosePopup(this, this) }

    private val mAdapter by lazy { ChapterAdapter() }

    private val mBook by lazy { intent.getParcelableExtra<NovelInfoBean>("book") }
    private var mVolume: Volume? = null
    private lateinit var mHeaderView: View
    private lateinit var mFootView: View
    private lateinit var mBookRecord: Readcord

    private var volumeList = mutableListOf<VolumeBean>()
    private var calalogue = mutableListOf<Calalogue>()
    private var selectedVolumePosition = 0//已选择章节的券

    private var isSequence = true
    private var page = 0

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerContentsComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .contentsModule(ContentsModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_contents
    }

    override fun initStatusBar(savedInstanceState: Bundle?) {
        //白底黑字
        StatusBarUtils.darkMode(this)
        //给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, toolbar)
    }

    override fun initData(savedInstanceState: Bundle?) {

        mBookRecord = DbManager.getReadcord(mBook.novelInfo.novelId)!!
        mAdapter.bindToRecyclerView(recyclerView)
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.layout_header_volume, recyclerView, false)
        mFootView = LayoutInflater.from(this).inflate(R.layout.layout_foot_volume, recyclerView, false)
        mAdapter.addHeaderView(mHeaderView)
        mAdapter.addFooterView(mFootView)
        mHeaderView.setOnClickListener {
            mPopup.showAsDropDown(it, dp2px(20), 0)
        }
        mPagePopup.popupGravity = Gravity.TOP or Gravity.CENTER
        mFootView.ll_page_choose.setOnClickListener {
            mPagePopup.showPopupWindow(mFootView.ll_page_choose)
        }
        val decoration = LinearItemDecoration(Color.parseColor("#c7c7c7"), 1)
        decoration.leftMargin = dp2px(15)
        decoration.rightMargin = dp2px(15)
        recyclerView.addItemDecoration(decoration)
        mAdapter.setOnItemClickListener { _, _, position ->
            if(mAdapter.data[position].isLocked){
                toast("该章节尚未发布")
                return@setOnItemClickListener
            }
            mBookRecord.bookId = mBook.novelInfo.novelId
            mBookRecord.chapter = position + page * 100
            mBookRecord.volumePos = selectedVolumePosition
            mBookRecord.pagePos = 0
            DbManager.saveRecord(mBookRecord)
            JumpManager.jumpRead(this, mBook)
            finish()
        }
        mHeaderView.iv_rank_type.clicks().subscribe {
            var list = volumeList[selectedVolumePosition].calalogue
            isSequence = if (isSequence) {
                calalogue.clear()
                calalogue.addAll(list.sortedByDescending { it.chapter })
                page = 0
                mAdapter.setNewData(goPage(page))
                false
            } else {
                calalogue.clear()
                calalogue.addAll(list.sortedBy { it.chapter })
                page = 0
                mAdapter.setNewData(goPage(page))
                true
            }
            setCurrentPositionShow()
            mHeaderView.iv_rank_type.setImageDrawable(if (isSequence) {
                getCompactDrawable(R.drawable.ic_rank_down)
            } else getCompactDrawable(R.drawable.ic_rank_up))
        }.bindToLifecycle(this)
        mFootView.tv_previous_page.clicks().subscribe {
            if (page > 0) {
                page--
                setCurrentPositionShow()
            }

        }.bindToLifecycle(this)
        mFootView.tv_next_page.clicks().subscribe {
            if (page < calalogue.size / 100) {
                if (calalogue.size % 100 == 0) {
                    if (page < calalogue.size / 100 - 1)
                        page++
                } else
                    page++
                setCurrentPositionShow()
            }

        }.bindToLifecycle(this)
//        mPresenter?.getVolumeList(mBook.novelInfo.novelId)
        mPresenter?.getCatalogue(mBook.novelInfo.novelId)
    }

    private fun initPagePopup() {
        mPagePopup.setData(calalogue)
        mPagePopup.setCurrentPosition(page)
    }

    override fun selectVolume(position: Int) {
        chooseVolume(position)
    }

    override fun selectPage(slepage: Int) {
        page = slepage
        setCurrentPositionShow()
    }

    override fun showVolume(data: MutableList<Volume>?) {
        mVolume = data?.get(0)
        mHeaderView.tv_volume_title.text = data?.get(0)?.title
        mHeaderView.tv_count.text = "共${data?.get(0)?.chapterNum}章"

//        mPopup.setData(data)
    }

    override fun showChapterList(volume: String?, data: ChapterBean) {
//        mAdapter.setNewData(data.list)
    }

    override fun showState(state: Int) {
        multiStateView.viewState = state
    }

    override fun showCalalogueInfo(list: ArrayList<VolumeBean>) {
        volumeList.clear()
        volumeList.addAll(list)
        mPopup.setData(volumeList)
        if (volumeList.size > 0) {
            chooseVolume(0)
        }
    }

    private fun chooseVolume(position: Int) {
        isSequence = true
        mHeaderView.iv_rank_type.setImageDrawable(if (isSequence) {
            getCompactDrawable(R.drawable.ic_rank_down)
        } else getCompactDrawable(R.drawable.ic_rank_up))

        selectedVolumePosition = position

        val data = volumeList[position]
        if (mHeaderView.tv_volume_title != null) {
            mHeaderView.tv_volume_title.text = data.volumeName
        }
        if (mHeaderView.tv_count != null) {
            mHeaderView.tv_count.text = "共${data.calalogue.size}章"
        }
        calalogue.clear()
        calalogue.addAll(data.calalogue)
        isSequence = true
        page = 0
        setCurrentPositionShow()
    }

    private fun goPage(page: Int): List<Calalogue> {
        return calalogue.take((page + 1) * 100).takeLast(if (calalogue.size - page * 100 >= 100) 100 else calalogue.size - page * 100)
    }


    private fun setCurrentPositionShow() {
        val list = goPage(page)
        mAdapter.setNewData(list)
        if (isSequence)
            mAdapter.setCurrentPosition(mBookRecord.chapter - (page * 100))
        else
            mAdapter.setCurrentPosition(calalogue.size - 1 - mBookRecord.chapter - (page * 100))
        if (selectedVolumePosition == mBookRecord.volumePos)
            mAdapter.isCurrentPositionShow(true)
        else
            mAdapter.isCurrentPositionShow(false)
        mFootView.tv_page_chapter_num.text = "第${list[0].chapter}章 - 第${list[list.size - 1].chapter}章"
        recyclerView.layoutManager?.scrollToPosition(0)
        initPagePopup()
    }

}
