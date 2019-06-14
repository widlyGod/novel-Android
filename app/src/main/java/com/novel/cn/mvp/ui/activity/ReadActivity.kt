package com.novel.cn.mvp.ui.activity

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.text.Html
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.SeekBar
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.EventBusManager
import com.jess.arms.utils.TipDialog
import com.jess.arms.utils.TipDialog.Builder.ICON_TYPE_LOADING
import com.novel.cn.R
import com.novel.cn.app.*
import com.novel.cn.db.DbManager
import com.novel.cn.db.Readcord
import com.novel.cn.di.component.DaggerReadComponent
import com.novel.cn.di.module.ReadModule
import com.novel.cn.eventbus.BookshelfEvent
import com.novel.cn.ext.bindToLifecycle
import com.novel.cn.ext.dp2px
import com.novel.cn.ext.getCompactDrawable
import com.novel.cn.ext.toast
import com.novel.cn.mvp.contract.ReadContract
import com.novel.cn.mvp.model.entity.*
import com.novel.cn.mvp.presenter.ReadPresenter
import com.novel.cn.mvp.ui.adapter.ChapterAdapter
import com.novel.cn.mvp.ui.dialog.ReadSettingDialog
import com.novel.cn.mvp.ui.dialog.RewardDialog
import com.novel.cn.mvp.ui.dialog.VolumePopup
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.VolumeView
import com.novel.cn.view.readpage.*
import kotlinx.android.synthetic.main.activity_read.*
import kotlinx.android.synthetic.main.layout_header_volume.view.*
import kotlinx.android.synthetic.main.layout_menu_chapter.*
import kotlinx.android.synthetic.main.layout_shoufei.*
import org.jetbrains.anko.startActivity


class ReadActivity : BaseActivity<ReadPresenter>(), ReadContract.View, VolumeView {

    private val mBook by lazy { intent.getParcelableExtra<NovelInfoBean>("book") }

    private val mRewardDialog by lazy { RewardDialog(this, this) }

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

    private lateinit var user: LoginInfo

    private val mPageLoader by lazy { readView.getPageLoader(mBook.novelInfo.novelId) }

    private val mAdapter by lazy { ChapterAdapter() }

    private lateinit var volumeIdId: String

    private val mPopup by lazy { VolumePopup(this, this) }

    private var isCollect = false

    private var volumeList = mutableListOf<VolumeBean>()


    private val tipDialog by lazy {
        TipDialog.Builder(this)
                .setTipWord("请稍后")
                .setIconType(ICON_TYPE_LOADING)
                .create()
    }

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

    override fun initStatusBar(savedInstanceState: Bundle?) {
        StatusBarUtils.immersive(this)
        StatusBarUtils.setPaddingSmart(this, toolbar)
    }


    override fun initData(savedInstanceState: Bundle?) {
        user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)!!
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        drawerLayout.isFocusableInTouchMode = false
        mAdapter.bindToRecyclerView(recyclerView)


        mAdapter.setOnItemClickListener { _, _, position ->
            if (mAdapter.data[position].isLocked) {
                toast("该章节尚未发布")
                return@setOnItemClickListener
            }
            if (isSequence)
                mPageLoader.skipToChapter(position)
            else
                mPageLoader.skipToChapter(mAdapter.data.size - 1 - position)

            drawerLayout.closeDrawer(Gravity.LEFT)
        }
        isCollect = mBook.novelInfo.isCollection
        refreshCollectState()


        tv_collect.setOnClickListener {
            if (user!!.sessionId.isBlank()) {
                startActivity<LoginActivity>()
                return@setOnClickListener
            }
            if (!isCollect)
                mPresenter?.addCollection(mBook.novelInfo.novelId)
            else
                mPresenter?.cancelCollection(mBook.novelInfo.novelId)
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

            }

            override fun nextPage(next: Boolean) {

            }

            override fun cancel() {
            }

            override fun reward() {
                if (user!!.sessionId.isBlank()) {
                    startActivity<LoginActivity>()
                    return
                }
                mPresenter?.getUserAccountInfo()
            }
        })

        mPageLoader.setOnPageChangeListener(object : PageLoader.OnPageChangeListener {
            override fun parseSuccess() {
                ll_info.visible(false)
            }

            override fun locked(txtChapter: TxtChapter, mCurChapterPos: Int) {
                toast("章节《${txtChapter.title}》未解锁")
            }

            override fun noFree(txtChapter: TxtChapter, mCurChapterPos: Int) {
                if (user!!.sessionId.isBlank()) {
                    startActivity<LoginActivity>()
                    finish()
                    return
                }
                if (!tipDialog.isShowing) {
                    tipDialog.show()
                }
                mPresenter?.isChargeChapter(mBook.novelInfo.novelId, volumeList[nowVolumePosition].calalogue[mCurChapterPos].volumeId, volumeList[nowVolumePosition].calalogue[mCurChapterPos].chapterId, txtChapter, mCurChapterPos)
            }

            override fun onChapterChange(pos: Int) {
                volumeIdId = volumeList[nowVolumePosition].calalogue[pos].volumeId
                seekbar.progress = pos
                selectedVolumePosition = nowVolumePosition
                var postion = 0
                if (isSequence)
                    postion = pos
                else
                    postion = mAdapter.data.size - 1 - pos
                mAdapter.setCurrentPosition(postion)
                val item = mAdapter.getItem(postion) as Calalogue
                tv_chapter_name.text = "${item.chapter}：${item.chapterTitle}"
                tv_chapter_info.text = "${item.chapter}章节/${mAdapter.data.size}章节"
                toolbar_title.text = "第${item.chapter}章 ${item.chapterTitle}"

                if (tipDialog.isShowing) {
                    tipDialog.dismiss()
                }
                mPresenter?.updateRead(item.novelId, item.chapterId)
            }

            override fun requestChapters(requestChapters: MutableList<TxtChapter>?) {
//                tipDialog.show()
//                mPresenter?.readNovel(requestChapters, mBook.novelInfo.novelId)
                mPresenter?.preDownload(requestChapters)
            }

            override fun onCategoryFinish(chapters: MutableList<TxtChapter>) {
            }

            override fun onPageCountChange(count: Int) {
            }

            override fun onPageChange(pos: Int) {
            }
        })
        refreshNightMode()
//        mPresenter?.getVolumeList(mBook.novelInfo.novelId)
        mPresenter?.getCatalogue(mBook.novelInfo.novelId)
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mPageLoader.skipToChapter(seekBar!!.progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                /* val item = mAdapter.getItem(progress) as ChapterInfo
                 tv_chapter_name.text = "${item.chapter}：${item.title}"
                 tv_chapter_info.text = "${item.chapter}章节/${mAdapter.data.size}章节"
                 toolbar_title.text = "第${item.chapter}章 ${item.title}"*/
            }

        })
        v_dash1.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        v_dash2.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        click(tv_setting, tv_catalogue, iv_pre, iv_next, iv_night_mode,
                tv_zhifu, tv_prev, tv_contents, tv_next, ll_info, iv_reward, tv_chapter_comment) {
            when (it) {
                iv_reward -> {
                    if (user!!.sessionId.isBlank()) {
                        startActivity<LoginActivity>()
                        return@click
                    }
                    mPresenter?.getUserAccountInfo()
                }
                ll_info -> toggleMenu()
                tv_prev -> {
                    mPageLoader.skipPreChapter()
                }
                tv_next -> {
                    mPageLoader.skipNextChapter()
                }
                tv_catalogue, tv_contents -> {
                    toggleMenu()
                    drawerLayout.openDrawer(Gravity.LEFT)
                }
//                tv_content -> toggleMenu()
                tv_setting -> {
                    ll_bottom_menu.visible(false)
                    mSettingDialog.show()
                }
                iv_next -> mPageLoader.skipNextChapter()
                iv_pre -> mPageLoader.skipPreChapter()
                iv_night_mode -> {
                    ReadSettingManager.getInstance().isNightMode = !ReadSettingManager.getInstance().isNightMode
                    refreshNightMode()
                }
                tv_chapter_comment -> {
                    if (user!!.sessionId.isBlank()) {
                        startActivity<LoginActivity>()
                        return@click
                    }
                    val chapter = mAdapter.getCurrentChapter()
                    chapter?.let {
                        hideSystemBar()
                        JumpManager.jumpChapterComment(this, mBook.novelInfo.novelId, it.chapterId, volumeIdId, mBook.novelInfo.authorId, mBook)
                    }
                }
            }
        }
    }

    override fun reward(operation: String, number: Int) {
        mPresenter?.reward(mBook.novelInfo.novelId, operation, number)
    }


    override fun getUserAccountInfoSuccess(userAccountBean: UserAccountBean) {
        mRewardDialog.show()
        mRewardDialog.setUserAccount(userAccountBean)
    }

    val header by lazy { LayoutInflater.from(this).inflate(R.layout.layout_header_volume, recyclerView, false) }
    private var isSequence = true

    override fun showCalalogueInfo(list: ArrayList<VolumeBean>) {
        header.setOnClickListener {
            mPopup.showAsDropDown(it, dp2px(20), 0)
        }
        header.iv_rank_type.clicks().subscribe {
            var list = volumeList[nowVolumePosition].calalogue
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
            if (selectedVolumePosition == nowVolumePosition)
                mAdapter.setCurrentPosition(mAdapter.data.size - 1 - mAdapter.getCurrentPosition())
            header.iv_rank_type.setImageDrawable(if (isSequence) {
                getCompactDrawable(R.drawable.ic_rank_down)
            } else getCompactDrawable(R.drawable.ic_rank_up))
        }.bindToLifecycle(this)

//        ll_info.setBackgroundColor(ContextCompat.getColor(this, ReadSettingManager.getInstance().pageStyle.bgColor))
        mAdapter.addHeaderView(header)
        volumeList.clear()
        volumeList.addAll(list)
        mPopup.setData(list)
        if (volumeList.size > 0) {
            var mBookRecord = DbManager.getReadcord(mBook.novelInfo.novelId)
            if (mBookRecord == null) {
                mBookRecord = Readcord()
            }
            chooseVolume(mBookRecord.volumePos)
        }
    }

    var nowVolumePosition = 0//当前目录展示的券
    var selectedVolumePosition = 0//已选择章节的券

    private fun chooseVolume(position: Int) {
        nowVolumePosition = position
        val data = volumeList[position]
        if (header.tv_volume_title != null) {
            header.tv_volume_title.text = data.volumeName
        }
        if (header.tv_count != null) {
            header.tv_count.text = "共${data.calalogue.size}章"
        }

        seekbar.max = data.calalogue.size - 1

        var list = data.calalogue
        var calalogue = mutableListOf<Calalogue>()
        if (!isSequence) {
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
        mAdapter.setNewData(calalogue)
        val chapterList = ArrayList<TxtChapter>(data.calalogue.size)
        data.calalogue.forEach {
            val txt = TxtChapter()

            txt.bookId = mBook.novelInfo.novelId
            txt.chapterId = it.chapterId
            txt.title = it.chapterTitle
            if (mBook.novelInfo.isFreeLimit)
                txt.isFree = false
            else
                txt.isFree = !it.isFree
            txt.isLocked = it.isLocked
            txt.filePath = it.filePath
            txt.words = it.words
            txt.money = it.money
            chapterList.add(txt)
        }
        mPageLoader.setChapterList(chapterList)
    }

    override fun selectVolume(position: Int) {
        chooseVolume(position)
        mAdapter.isCurrentPositionShow(selectedVolumePosition == position)
    }

    override fun isChargeChapter(data: ChargeChapter) {
        if (!data.isSubscibe) {

        } else {
        }
    }

    override fun subscribeError() {

    }

    override fun openBook(mCurChapterPos: Int, txtChapter: TxtChapter?) {
        ll_info.visible(false)
        txtChapter?.isFree = true
        tipDialog.dismiss()
        mPageLoader.skipToChapter(mCurChapterPos)
    }

    override fun onPause() {
        super.onPause()
        mPageLoader?.saveRecord(selectedVolumePosition)
    }

    override fun showChapter(data: ChapterInfoBean, txtChapter: TxtChapter, mCurChapterPos: Int, charge: ChargeChapter) {
        tipDialog.dismiss()

        tv_book_name.text = "《${mBook.novelInfo.novelTitle}》"
        tv_chaptername.text = "第 ${data.chapterInfo.chapter} 章 ${data.chapterInfo.title}"
        tv_author.text = data.novelInfo.authorInfo.penName
        tv_update_time.text = data.chapterInfo.updateTime
        tv_words.text = data.chapterInfo.words
        ll_info.visible(true)

        tv_price.text = Html.fromHtml("价格：<font color='#5e8fca'>${data.chapterInfo.money}</font>阅读币")
        tv_blance.text = "余额：${charge.goldNumber}阅读币 | 0钻石"

        if (charge.goldNumber < data.chapterInfo.money) {
            tv_zhifu.text = "余额不足，请立即充值"
            tv_zhifu.setOnClickListener {
                JumpManager.jumpRecharge(this)
            }
        } else {
            tv_zhifu.text = "订阅"
            tv_zhifu.setOnClickListener {
                if (!tipDialog.isShowing) {
                    tipDialog.show()
                }
                mPresenter?.subscribeBook(data.chapterInfo.id, data.chapterInfo.title,
                        data.chapterInfo.money.toString(), data.chapterInfo.chapter,
                        mBook.novelInfo.novelId, data.chapterInfo.volumeId, txtChapter, mCurChapterPos, charge, data)
            }
        }
        ctv_sub.isChecked = charge.isSubscibe

        ctv_sub.setOnClickListener {
            if (!ctv_sub.isChecked) {
                ctv_sub.isChecked = true
                mPresenter?.addAutoSubscribe(mBook.novelInfo.novelId)
            }
        }

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

    private fun refreshCollectState() {
        val drawable = resources.getDrawable(if (isCollect) R.drawable.ic_collected else R.drawable.ic_like_uncheck);
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight);
        tv_collect.setCompoundDrawables(null, drawable, null, null)
    }

    override fun collectionSuccess() {
        isCollect = true
        refreshCollectState()
    }

    override fun cancelCollection() {
        isCollect = false
        refreshCollectState()
    }

    /**
     * 打开-》从缓存获取->从服务器获取
     */

    override fun loadChapterSuccess() {
        if (mPageLoader.pageStatus == PageLoader.STATUS_LOADING) {
            mPageLoader.openChapter()
        }
    }

    override fun showVolume(data: MutableList<Volume>?) {


    }

    override fun showChapterList(volume: String?, data: ChapterBean) {

        /*  seekbar.max = data.list.size - 1

          mAdapter.setNewData(data.list)
          val list = ArrayList<TxtChapter>(data.list.size)
          data.list.forEach {
              val txt = TxtChapter()

              txt.bookId = mBook.novelInfo.novelId
              txt.link = it.id
              txt.title = it.title
              txt.isFree = it.isFree

              list.add(txt)
          }

          mPageLoader.setChapterList(list)*/
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
        EventBusManager.getInstance().post(BookshelfEvent())
        super.onDestroy()
    }

    override fun showLoading() {
        tipDialog.show()
    }

    override fun hideLoading() {
        tipDialog.hide()
    }
}
