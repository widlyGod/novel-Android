package com.novel.cn.mvp.ui.activity

import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.android.flexbox.*
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.LogUtils
import com.novel.cn.R
import com.novel.cn.di.component.DaggerSearchComponent
import com.novel.cn.di.module.SearchModule
import com.novel.cn.mvp.contract.SearchContract
import com.novel.cn.mvp.model.entity.Book
import com.novel.cn.mvp.model.entity.BookInfo
import com.novel.cn.mvp.presenter.SearchPresenter
import com.novel.cn.mvp.ui.adapter.HotWordAdapter
import com.novel.cn.utils.StatusBarUtils
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.doAsync
import javax.inject.Inject


class SearchActivity : BaseActivity<SearchPresenter>(), SearchContract.View {


    private val hotNovels by lazy { intent.getParcelableArrayListExtra<BookInfo>("hotNovels") }

    @Inject
    lateinit var mHotWordAdapter: HotWordAdapter

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

        recyclerView.layoutManager = flexBoxLayoutManager
        recyclerView.adapter = mHotWordAdapter
        /*
        //有bug 当刚好一行的情况下，会错位
        val itemDecoration = FlexboxItemDecoration(this)
        itemDecoration.setDrawable(ContextCompat.getDrawable(this,R.drawable.divider_decoration))
        recyclerView.addItemDecoration(itemDecoration)
        */

        mHotWordAdapter.setNewData(hotNovels)


    }


}
