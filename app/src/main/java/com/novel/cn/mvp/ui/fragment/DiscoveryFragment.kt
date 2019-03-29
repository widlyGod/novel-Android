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

import com.novel.cn.di.component.DaggerDiscoveryComponent
import com.novel.cn.di.module.DiscoveryModule
import com.novel.cn.mvp.contract.DiscoveryContract
import com.novel.cn.mvp.presenter.DiscoveryPresenter

import com.novel.cn.R



class DiscoveryFragment : BaseFragment<DiscoveryPresenter>(), DiscoveryContract.View {
    companion object {
        fun newInstance(): DiscoveryFragment {
            val fragment = DiscoveryFragment()
            return fragment
        }
    }


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerDiscoveryComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .discoveryModule(DiscoveryModule(this))
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_discovery, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {

    }


}
