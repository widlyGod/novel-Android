package com.novel.cn.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.novel.cn.di.component.DaggerMessageComponent
import com.novel.cn.di.module.MessageModule
import com.novel.cn.mvp.contract.MessageContract
import com.novel.cn.mvp.presenter.MessagePresenter

import com.novel.cn.R
import com.novel.cn.mvp.ui.adapter.MessageAdapter
import com.novel.cn.mvp.ui.adapter.MessageFilterAdapter
import com.novel.cn.mvp.ui.dialog.MessageFilterDialog
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.CustomLoadMoreView
import com.novel.cn.view.decoration.LinearItemDecoration
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.dialog_message_filter.*
import kotlinx.android.synthetic.main.include_title.*
import javax.inject.Inject


class MessageActivity : BaseActivity<MessagePresenter>(), MessageContract.View {


    @Inject
    lateinit var mAdapter: MessageAdapter

    @Inject
    lateinit var mFilterAdapter: MessageFilterAdapter

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerMessageComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .messageModule(MessageModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_message //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {
        //白底黑字
        StatusBarUtils.darkMode(this)
//        给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, toolbar)

        //禁止手势滑动
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
        filterRecyclerView.adapter = mFilterAdapter
        recyclerView.adapter = mAdapter
        val decoration = LinearItemDecoration()
        //分割线与左右两边的间距
        decoration.leftMargin = ArmsUtils.dip2px(this, 18f)
        decoration.rightMargin = ArmsUtils.dip2px(this, 18f)
        mAdapter.apply {
            setLoadMoreView(CustomLoadMoreView())
            setEnableLoadMore(true)
            setOnLoadMoreListener({
                mPresenter?.getMessageList(false)
            }, recyclerView)
        }
        recyclerView.addItemDecoration(decoration)

        refreshLayout.setOnRefreshListener {
            mPresenter?.getMessageList(true)
        }
        mPresenter?.getFilterList()
        mPresenter?.getMessageList(true)
    }

    override fun showStateView(state: Int) {
        stateView.viewState = state
    }

    override fun refreshComplete() {
        refreshLayout.finishRefresh()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_filter, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        drawer_layout.openDrawer(Gravity.END)
        return super.onOptionsItemSelected(item)
    }
}
