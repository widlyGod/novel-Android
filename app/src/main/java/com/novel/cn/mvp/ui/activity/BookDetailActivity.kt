package com.novel.cn.mvp.ui.activity

import android.content.Intent
import android.os.Bundle

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.novel.cn.di.component.DaggerBookDetailComponent
import com.novel.cn.di.module.BookDetailModule
import com.novel.cn.mvp.contract.BookDetailContract
import com.novel.cn.mvp.presenter.BookDetailPresenter

import com.novel.cn.R
import com.novel.cn.utils.StatusBarUtils
import kotlinx.android.synthetic.main.include_title.*


class BookDetailActivity : BaseActivity<BookDetailPresenter>(), BookDetailContract.View {


    private val bookId by lazy { intent.getStringExtra("bookId") }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerBookDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .bookDetailModule(BookDetailModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_book_detail //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        StatusBarUtils.darkMode(this)
        StatusBarUtils.setPaddingSmart(this,toolbar)

        mPresenter?.getBookDetail(bookId)
    }


}
