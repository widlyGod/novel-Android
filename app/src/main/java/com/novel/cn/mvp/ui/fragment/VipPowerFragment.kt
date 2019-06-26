package com.novel.cn.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.novel.cn.R
import com.novel.cn.app.visible
import com.novel.cn.mvp.presenter.NothingPresenter
import kotlinx.android.synthetic.main.fragment_vip_power.*

class VipPowerFragment : BaseFragment<NothingPresenter>() {

    companion object {
        private const val KEY_TYPE = "key"
    }

    private var type: Int = 0

    fun setArguments(type: Int) {
        arguments = Bundle().apply {
            putInt(KEY_TYPE, type)
        }
    }

    private fun loadArguments() {
        arguments?.apply {
            type = getInt(KEY_TYPE)
        }
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {

    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_vip_power, container, false)

    }

    override fun initData(savedInstanceState: Bundle?) {
        loadArguments()
        when (type) {
            0 -> {
                ll_view_1.visible(true)
                ll_view_2.visible(false)
            }
            1 -> {
                ll_view_1.visible(false)
                ll_view_2.visible(true)
            }
        }
    }

}