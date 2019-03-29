package com.novel.cn.mvp.ui.activity

import android.content.Intent
import android.os.Bundle

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.novel.cn.di.component.DaggerMessageComponent
import com.novel.cn.di.module.MessageModule
import com.novel.cn.mvp.contract.MessageContract
import com.novel.cn.mvp.presenter.MessagePresenter

import com.novel.cn.R
import com.novel.cn.mvp.ui.adapter.MessageAdapter
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.decoration.LinearItemDecoration
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.include_title.*
import javax.inject.Inject


class MessageActivity : BaseActivity<MessagePresenter>(), MessageContract.View {

    @Inject
    lateinit var mAdapter: MessageAdapter

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
        //给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, toolbar)

        recyclerView.adapter = mAdapter
        val decoration = LinearItemDecoration()
        decoration.leftMargin = ArmsUtils.dip2px(this, 18f)
        decoration.rightMargin = ArmsUtils.dip2px(this, 18f)
        recyclerView.addItemDecoration(decoration)

        refreshLayout.setOnRefreshListener {

        }
        mPresenter?.getMessageList()
    }

}
