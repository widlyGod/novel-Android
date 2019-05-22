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
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.novel.cn.R
import com.novel.cn.app.JumpManager
import com.novel.cn.app.click
import com.novel.cn.app.isVisible
import com.novel.cn.app.visible
import com.novel.cn.di.component.DaggerReadComponent
import com.novel.cn.di.module.ReadModule
import com.novel.cn.ext.dp2px
import com.novel.cn.mvp.contract.ReadContract
import com.novel.cn.mvp.model.entity.*
import com.novel.cn.mvp.presenter.ReadPresenter
import com.novel.cn.mvp.ui.adapter.ChapterAdapter
import com.novel.cn.mvp.ui.dialog.ReadSettingDialog
import com.novel.cn.mvp.ui.dialog.RewardDialog
import com.novel.cn.mvp.ui.dialog.VolumePopup
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.TipDialog
import com.novel.cn.view.TipDialog.Builder.ICON_TYPE_LOADING
import com.novel.cn.view.readpage.*
import kotlinx.android.synthetic.main.activity_read.*
import kotlinx.android.synthetic.main.layout_header_volume.*
import kotlinx.android.synthetic.main.layout_menu_chapter.*
import kotlinx.android.synthetic.main.layout_shoufei.*
import java.util.*
import kotlin.collections.ArrayList


class ReadActivity : BaseActivity<ReadPresenter>(), ReadContract.View {


    private val mBook by lazy { intent.getParcelableExtra<NovelInfoBean>("book") }

    private val mRewardDialog by lazy { RewardDialog(this) }

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

    private val mPageLoader by lazy { readView.getPageLoader(mBook.novelInfo.novelId) }

    private val mAdapter by lazy { ChapterAdapter() }

    private var mVolume: VolumeBean? = null

    private val mPopup by lazy { VolumePopup(this) }

    private var isCollect = false


    private val tipDialog by lazy {
        TipDialog.Builder(this)
                .setTipWord("正在加载")
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
//        ll_info.setBackgroundColor(ContextCompat.getColor(this, ReadSettingManager.getInstance().pageStyle.bgColor))
        mAdapter.addHeaderView(header)

        mAdapter.setOnItemClickListener { adapter, view, position ->
            mPageLoader.skipToChapter(position)
            drawerLayout.closeDrawer(Gravity.LEFT)
        }
        isCollect = mBook.novelInfo.isCollection
        refreshCollectState()


        tv_collect.setOnClickListener {
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

        })

        mPageLoader.setOnPageChangeListener(object : PageLoader.OnPageChangeListener {
            override fun parseSuccess() {
                ll_info.visible(false)
            }

            override fun noFree(txtChapter: TxtChapter, mCurChapterPos: Int) {
                if (!tipDialog.isShowing) {
                    tipDialog.show()
                }
                mPresenter?.isChargeChapter(mBook.novelInfo.novelId, mVolume?.volumeId, txtChapter, mCurChapterPos)
            }

            override fun onChapterChange(pos: Int) {
                seekbar.progress = pos
                mAdapter.setCurrentPosition(pos)
                val item = mAdapter.getItem(pos) as Calalogue
                tv_chapter_name.text = "${item.chapter}：${item.chapterTitle}"
                tv_chapter_info.text = "${item.chapter}章节/${mAdapter.data.size}章节"
                toolbar_title.text = "第${item.chapter}章 ${item.chapterTitle}"

                if (tipDialog.isShowing) {
                    tipDialog.dismiss()
                }
            }

            override fun requestChapters(requestChapters: MutableList<TxtChapter>?) {
//                tipDialog.show()
//                mPresenter?.readNovel(requestChapters, mBook.novelInfo.novelId)
                mPresenter?.preDownload(requestChapters)
            }

            override fun onCategoryFinish(chapters: MutableList<TxtChapter>?) {

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
                iv_reward -> mRewardDialog.show()
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
                    val chapter = mAdapter.getCurrentChapter()
                    chapter?.let {
                        hideSystemBar()
                        JumpManager.jumpChapterComment(this, mBook.novelInfo.novelId, it.chapterId, mVolume?.volumeId, mBook.novelInfo.authorId)
                    }
                }
            }
        }
    }

    override fun showCalalogueInfo(list: ArrayList<VolumeBean>) {
        val data = list[0]
        tv_volume_title.text = data.volumeName
        tv_count.text = "共${data.calalogue.size}章"
        mPopup.setData(list)
        seekbar.max = data.calalogue.size - 1

        mAdapter.setNewData(data.calalogue)
        val chapterList = ArrayList<TxtChapter>(data.calalogue.size)
        data.calalogue.forEach {
            val txt = TxtChapter()

            txt.bookId = mBook.novelInfo.novelId
            txt.chapterId = it.chapterId
            txt.title = it.chapterTitle
            txt.isFree = it.isFree
            txt.isLocked = it.isLocked
            txt.filePath = it.filePath
            txt.words = it.words
            txt.money = it.money
            chapterList.add(txt)
        }
        mPageLoader.setChapterList(chapterList)
    }

    override fun isChargeChapter(data: ChargeChapter) {
        if (!data.isSubscibe) {

        } else {
        }
    }

    override fun subscribeError() {
        tipDialog.dismiss()
    }

    override fun openBook(mCurChapterPos: Int, txtChapter: TxtChapter?) {
        ll_info.visible(false)
        txtChapter?.isFree = true
        tipDialog.dismiss()
        mPageLoader.skipToChapter(mCurChapterPos)
    }

    override fun onPause() {
        super.onPause()
        mPageLoader?.saveRecord()
    }

    override fun showChapter(data: ChapterInfoBean, txtChapter: TxtChapter?, mCurChapterPos: Int, charge: ChargeChapter) {
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
                        mBook.novelInfo.novelId, data.chapterInfo.volumeId, txtChapter, mCurChapterPos)
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
        super.onDestroy()
    }
}
