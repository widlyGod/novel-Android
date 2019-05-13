package com.novel.cn.mvp.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.novel.cn.R
import com.novel.cn.di.component.DaggerContentsComponent
import com.novel.cn.di.module.ContentsModule
import com.novel.cn.ext.dp2px
import com.novel.cn.mvp.contract.ContentsContract
import com.novel.cn.mvp.model.entity.ChapterBean
import com.novel.cn.mvp.model.entity.NovelInfoBean
import com.novel.cn.mvp.model.entity.Volume
import com.novel.cn.mvp.presenter.ContentsPresenter
import com.novel.cn.mvp.ui.adapter.ChapterAdapter
import com.novel.cn.mvp.ui.dialog.VolumePopup
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.decoration.LinearItemDecoration
import kotlinx.android.synthetic.main.activity_contents.*
import kotlinx.android.synthetic.main.include_title.*
import kotlinx.android.synthetic.main.layout_header_volume.*
import kotlinx.android.synthetic.main.layout_header_volume.view.*


class ContentsActivity : BaseActivity<ContentsPresenter>(), ContentsContract.View {


    private val mPopup by lazy {
        VolumePopup(this).apply {
            setListener {
                mVolume = it
                mPresenter?.getChapterList(mBook.novelInfo.novelId, it.volume)
                dismiss()
            }
        }
    }

    private val mAdapter by lazy { ChapterAdapter() }

    private val mBook by lazy { intent.getParcelableExtra<NovelInfoBean>("book") }
    private var mVolume: Volume? = null
    private lateinit var mHeaderView:View

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


    override fun initData(savedInstanceState: Bundle?) {
        //白底黑字
        StatusBarUtils.darkMode(this)
        //给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, toolbar)


        mAdapter.bindToRecyclerView(recyclerView)
        mHeaderView = LayoutInflater.from(this).inflate(R.layout.layout_header_volume, recyclerView, false)
        mAdapter.addHeaderView(mHeaderView)
        mHeaderView.setOnClickListener {
            mPopup.showAsDropDown(it, dp2px(20), 0)
        }
        val decoration = LinearItemDecoration(Color.parseColor("#c7c7c7"), 1)
        decoration.leftMargin = dp2px(15)
        decoration.rightMargin = dp2px(15)
        recyclerView.addItemDecoration(decoration)
        mPresenter?.getVolumeList(mBook.novelInfo.novelId)
    }

    override fun showVolume(data: MutableList<Volume>?) {
        mVolume = data?.get(0)
        mHeaderView.tv_volume_title.text = data?.get(0)?.title
        mHeaderView.tv_count.text = "共${data?.get(0)?.chapterNum}章"
        mPopup.setData(data)
    }

    override fun showChapterList(volume: String?, data: ChapterBean) {
        mAdapter.setNewData(data.list)
    }

    override fun showState(state: Int) {
        multiStateView.viewState = state
    }
}
