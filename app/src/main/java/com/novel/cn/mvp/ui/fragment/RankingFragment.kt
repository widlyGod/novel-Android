package com.novel.cn.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils
import com.novel.cn.R
import com.novel.cn.di.component.DaggerRankingComponent
import com.novel.cn.di.module.RankingModule
import com.novel.cn.ext.bindToLifecycle
import com.novel.cn.mvp.contract.RankingContract
import com.novel.cn.mvp.presenter.RankingPresenter
import com.novel.cn.mvp.ui.adapter.RankAdapter
import com.novel.cn.view.MultiStateView
import com.novel.cn.view.decoration.LinearItemDecoration
import kotlinx.android.synthetic.main.fragment_ranking.*
import javax.inject.Inject

class RankingFragment : BaseFragment<RankingPresenter>(), RankingContract.View {

    @Inject
    lateinit var mAdapter: RankAdapter

    companion object {
        fun newInstance(): RankingFragment {
            val fragment = RankingFragment()
            return fragment
        }
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerRankingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .rankingModule(RankingModule(this))
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_ranking, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {
        recyclerView.adapter = mAdapter
        recyclerView.addItemDecoration(LinearItemDecoration(ArmsUtils.dip2px(activity!!, 8f)))
        mPresenter?.getRankList()
    }

    override fun showState(state: Int) {
        multiStateView.viewState = state
        if (state == MultiStateView.VIEW_STATE_ERROR) {
            multiStateView.clicks().subscribe {
                mPresenter?.getRankList()
            }.bindToLifecycle(this)
        } else {
            multiStateView.setOnClickListener(null)
        }
    }

}
