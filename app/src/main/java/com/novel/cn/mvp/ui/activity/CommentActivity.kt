package com.novel.cn.mvp.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils

import com.novel.cn.di.component.DaggerCommentComponent
import com.novel.cn.di.module.CommentModule
import com.novel.cn.mvp.contract.CommentContract
import com.novel.cn.mvp.presenter.CommentPresenter

import com.novel.cn.R
import com.novel.cn.app.JumpManager
import com.novel.cn.app.visible
import com.novel.cn.mvp.ui.adapter.BookCommentAdapter
import com.novel.cn.mvp.ui.dialog.CommentDialog
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.CustomLoadMoreView
import com.novel.cn.view.decoration.LinearItemDecoration
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.include_title.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject


class CommentActivity : BaseActivity<CommentPresenter>(), CommentContract.View {


    private val bookId by lazy { intent.getStringExtra("bookId") }

    private val dialog by lazy { CommentDialog(this) }

    @Inject
    lateinit var mAdapter: BookCommentAdapter

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerCommentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .commentModule(CommentModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_comment
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
        mAdapter.apply {
            setEnableLoadMore(true)
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({
                mPresenter?.getCommentList(bookId, false)
            }, recyclerView)
            setOnReplyClickListener { position ->
                JumpManager.toCommentDetail(this@CommentActivity, this.getItem(position))
            }
        }
        iv_back_top.setOnClickListener {
            //快速定位到顶部
            recyclerView.scrollToPosition(0)
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //获取显示在屏幕上的 第一个item的坐标，大于0说明不再顶部
                val position = (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                iv_back_top.visible(position > 1)
            }
        })

        tv_comment.setOnClickListener {
            dialog.show()
        }

        mPresenter?.getCommentList(bookId, true)
    }

    override fun showCommentCount(count: Int) {
        val ss = SpannableString("共${count}条")
        ss.setSpan(ForegroundColorSpan(Color.parseColor("#EE4B1A")), 1, (1 + count.toString().length), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_count.text = ss
    }

}
