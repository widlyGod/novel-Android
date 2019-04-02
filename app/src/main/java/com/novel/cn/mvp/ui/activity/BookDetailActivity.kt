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
import com.novel.cn.app.loadImage
import com.novel.cn.mvp.model.entity.NovelInfoBean
import com.novel.cn.mvp.ui.adapter.BookCommentAdapter
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.decoration.LinearItemDecoration
import kotlinx.android.synthetic.main.activity_book_detail.*
import kotlinx.android.synthetic.main.include_title.*
import javax.inject.Inject


class BookDetailActivity : BaseActivity<BookDetailPresenter>(), BookDetailContract.View {


    private val bookId by lazy { intent.getStringExtra("bookId") }

    @Inject
    lateinit var mAdapter: BookCommentAdapter

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
        StatusBarUtils.setPaddingSmart(this, toolbar)
        recyclerView.adapter = mAdapter
        val decoration = LinearItemDecoration()
        //分割线与左右两边的间距
        decoration.leftMargin = ArmsUtils.dip2px(this, 18f)
        decoration.rightMargin = ArmsUtils.dip2px(this, 18f)
        recyclerView.addItemDecoration(decoration)

        mPresenter?.getBookDetail(bookId)
    }

    override fun showBookDetail(data: NovelInfoBean) {
        tv_book_name.text = data.novelInfo.novelTitle
        iv_book_image.loadImage(data.novelInfo.novelPhoto)
        tv_author.text = data.novelInfo.novelAuthor
        tv_words.text = "${data.novelInfo.novelWords}字"
        tv_click_num.text = "${data.novelInfo.clickNum}次点击"
        tv_novelIntro.text = data.novelInfo.novelIntro
        tv_chapter.text = "更新至${data.novelInfo.chapterCount}章"
        tv_review_count.text = data.comment.totalCount.toString()
        mAdapter.setNewData(data.comment.comments)
    }

}
