package com.novel.cn.mvp.ui.activity

import android.content.Intent
import android.os.Bundle

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.novel.cn.di.component.DaggerRankListComponent
import com.novel.cn.di.module.RankListModule
import com.novel.cn.mvp.contract.RankListContract
import com.novel.cn.mvp.presenter.RankListPresenter

import com.novel.cn.R
import com.novel.cn.app.JumpManager
import com.novel.cn.mvp.model.entity.RankWeek
import com.novel.cn.mvp.ui.adapter.RankListAdapter
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.CustomLoadMoreView
import kotlinx.android.synthetic.main.activity_rank_list.*
import kotlinx.android.synthetic.main.include_title.*
import javax.inject.Inject


class RankListActivity : BaseActivity<RankListPresenter>(), RankListContract.View {


    private val code by lazy { intent.getStringExtra("code") }

    private val title by lazy { intent.getStringExtra("title") }

    @Inject
    lateinit var mAdapter: RankListAdapter

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerRankListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .rankListModule(RankListModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_rank_list
    }


    override fun initData(savedInstanceState: Bundle?) {
        //白底黑字
        StatusBarUtils.darkMode(this)
        //给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, toolbar)
        setTitle(title)

        mAdapter.apply {
            setEnableLoadMore(true)
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({
                mPresenter?.getRank(code, false)
            }, recyclerView)

            setOnItemClickListener { adapter, view, position ->
                val data = mAdapter.getItem(position) as RankWeek
                JumpManager.jumpBookDetail(this@RankListActivity, data.novelId)
            }

        }
        recyclerView.adapter = mAdapter
        refreshLayout.setOnRefreshListener {
            mPresenter?.getRank(code, true)
        }
        mPresenter?.getRank(code, true)
    }

    override fun refreshComplete() {
        refreshLayout.finishRefresh()
    }


}
