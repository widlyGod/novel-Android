package com.novel.cn.mvp.ui.activity

import android.os.Bundle
import android.support.v7.widget.SimpleItemAnimator
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.EventBusManager
import com.novel.cn.R
import com.novel.cn.app.Constant
import com.novel.cn.app.JumpManager
import com.novel.cn.app.Preference
import com.novel.cn.di.component.DaggerRankListComponent
import com.novel.cn.di.module.RankListModule
import com.novel.cn.eventbus.BookshelfEvent
import com.novel.cn.mvp.contract.RankListContract
import com.novel.cn.mvp.model.entity.LoginInfo
import com.novel.cn.mvp.model.entity.RankWeek
import com.novel.cn.mvp.presenter.RankListPresenter
import com.novel.cn.mvp.ui.adapter.RankListAdapter
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.CustomLoadMoreView
import kotlinx.android.synthetic.main.activity_rank_list.*
import kotlinx.android.synthetic.main.include_title.*
import org.jetbrains.anko.startActivity
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

    override fun initStatusBar(savedInstanceState: Bundle?) {
        //白底黑字
        StatusBarUtils.darkMode(this)
        //给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, toolbar)
    }


    override fun initData(savedInstanceState: Bundle?) {
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

            //收藏按钮点击
            setOnConllectClickListener {
                val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
                if(user!!.sessionId.isBlank()){
                    startActivity<LoginActivity>()
                    return@setOnConllectClickListener
                }else{
                    val item = mAdapter.getItem(it) as RankWeek
                    mPresenter?.addConllection(item.novelId, it)
                }

            }

        }
        recyclerView.adapter = mAdapter
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        refreshLayout.setOnRefreshListener { onRefresh() }
        onRefresh()
    }

    private fun onRefresh() {
        mPresenter?.getRank(code, true)
    }

    override fun refreshComplete() {
        refreshLayout.finishRefresh()
    }

    override fun conllectionSuccess(position: Int) {
        //收藏成功后，更新页面，并通知书架
        val item = mAdapter.getItem(position)
        item?.let {
            it.isCollection = true
            mAdapter.notifyItemChanged(position)
            EventBusManager.getInstance().post(BookshelfEvent())
        }
    }


}
