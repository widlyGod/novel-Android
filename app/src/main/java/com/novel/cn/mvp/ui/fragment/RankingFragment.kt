package com.novel.cn.mvp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.novel.cn.di.component.DaggerRankingComponent
import com.novel.cn.di.module.RankingModule
import com.novel.cn.mvp.contract.RankingContract
import com.novel.cn.mvp.presenter.RankingPresenter

import com.novel.cn.R

 class RankingFragment : BaseFragment<RankingPresenter>(), RankingContract.View {
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

    }


}
