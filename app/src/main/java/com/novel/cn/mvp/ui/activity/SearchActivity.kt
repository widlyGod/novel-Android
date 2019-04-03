package com.novel.cn.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import com.google.android.flexbox.*

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils

import com.novel.cn.di.component.DaggerSearchComponent
import com.novel.cn.di.module.SearchModule
import com.novel.cn.mvp.contract.SearchContract
import com.novel.cn.mvp.presenter.SearchPresenter

import com.novel.cn.R
import com.novel.cn.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_search.*


class SearchActivity : BaseActivity<SearchPresenter>(), SearchContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerSearchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .searchModule(SearchModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_search //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        //白底黑字
        StatusBarUtils.darkMode(this)
        //给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, ll_search)

        val flexBoxLayoutManager = FlexboxLayoutManager(this)
        flexBoxLayoutManager.flexWrap = FlexWrap.WRAP      //按正常方向换行
        flexBoxLayoutManager.flexDirection = FlexDirection.ROW   //主轴为水平方向，起点在左端
        flexBoxLayoutManager.alignItems = AlignItems.CENTER    //定义项目在副轴轴上如何对齐
        flexBoxLayoutManager.justifyContent = JustifyContent.FLEX_START  //多个轴对齐方式
        recyclerView.layoutManager = flexBoxLayoutManager

    }


}
