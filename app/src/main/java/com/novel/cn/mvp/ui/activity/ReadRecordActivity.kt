package com.novel.cn.mvp.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.novel.cn.R
import com.novel.cn.di.component.DaggerReadRecordComponent
import com.novel.cn.di.module.ReadRecordModule
import com.novel.cn.mvp.contract.ReadRecordContract
import com.novel.cn.mvp.model.entity.Book
import com.novel.cn.mvp.presenter.ReadRecordPresenter
import com.novel.cn.mvp.ui.adapter.ReadRecordAdapter
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.CustomLoadMoreView
import com.novel.cn.view.decoration.LinearItemDecoration
import kotlinx.android.synthetic.main.activity_read_record.*
import kotlinx.android.synthetic.main.include_title.*


class ReadRecordActivity : BaseActivity<ReadRecordPresenter>(), ReadRecordContract.View {


    private val mAdapter by lazy { ReadRecordAdapter() }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerReadRecordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .readRecordModule(ReadRecordModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_read_record
    }

    override fun initStatusBar(savedInstanceState: Bundle?) {
        //白底黑字
        StatusBarUtils.darkMode(this)
        //给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, toolbar)
    }


    override fun initData(savedInstanceState: Bundle?) {
        recyclerView.adapter = mAdapter
        recyclerView.addItemDecoration(LinearItemDecoration())

        mAdapter.apply {
            setEnableLoadMore(true)
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({
                mPresenter?.getReadRecordList(false)
            }, recyclerView)
        }

        refreshLayout.setOnRefreshListener {
            mPresenter?.getReadRecordList(true)
        }
        refreshLayout.autoRefresh()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_clean, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        mPresenter?.cleanRecord()
        return super.onOptionsItemSelected(item)
    }

    override fun cleanRecordSuccess() {
        refreshLayout.autoRefresh()
    }

    override fun complete(pullToRefresh: Boolean) {
        if (pullToRefresh)
            refreshLayout.finishRefresh()
        else
            mAdapter.loadMoreComplete()
    }

    override fun noMore() {
        mAdapter.loadMoreEnd()
    }

    override fun showReadRecordList(pullToRefresh: Boolean, book: List<Book>) {
        if (pullToRefresh)
            mAdapter.setNewData(book)
        else
            mAdapter.addData(book)
    }
}
