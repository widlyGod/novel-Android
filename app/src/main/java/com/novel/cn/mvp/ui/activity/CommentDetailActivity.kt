package com.novel.cn.mvp.ui.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import com.google.gson.Gson

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.LogUtils

import com.novel.cn.di.component.DaggerCommentDetailComponent
import com.novel.cn.di.module.CommentDetailModule
import com.novel.cn.mvp.contract.CommentDetailContract
import com.novel.cn.mvp.presenter.CommentDetailPresenter

import com.novel.cn.R
import com.novel.cn.app.Constant
import com.novel.cn.app.loadImage
import com.novel.cn.app.visible
import com.novel.cn.mvp.model.entity.Comment
import com.novel.cn.mvp.model.entity.CommentInfo
import com.novel.cn.mvp.model.entity.Test
import com.novel.cn.mvp.ui.adapter.BookReplyAdapter
import com.novel.cn.mvp.ui.dialog.CommentDialog
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.utils.TimeUtils
import com.novel.cn.view.CustomLoadMoreView
import com.novel.cn.view.decoration.LinearItemDecoration
import kotlinx.android.synthetic.main.activity_comment_detail.*
import kotlinx.android.synthetic.main.include_title.*
import kotlinx.android.synthetic.main.item_reply.*
import java.text.SimpleDateFormat
import javax.inject.Inject


class CommentDetailActivity : BaseActivity<CommentDetailPresenter>(), CommentDetailContract.View {


    private val mComment by lazy { intent.getParcelableExtra<Comment>("comment") }

    @Inject
    lateinit var mAdapter: BookReplyAdapter


    private val mDialog by lazy {
        val dialog = CommentDialog(this)
        dialog.setOnReleaseClickListener {

        }
        dialog
    }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerCommentDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .commentDetailModule(CommentDetailModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_comment_detail //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }


    override fun initData(savedInstanceState: Bundle?) {
        StatusBarUtils.darkMode(this)
        StatusBarUtils.setPaddingSmart(this, toolbar)

        mComment?.let {
            //初始化数据
            val ss = SpannableString("共${it.counts}条")
            ss.setSpan(ForegroundColorSpan(Color.parseColor("#EE4B1A")), 1, (1 + it.counts.toString().length), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            tv_count.text = ss
            iv_avatar.loadImage(it.commentUser.userPhoto)
            tv_nickname.text = it.commentUser.userNickName
            tv_content.text = it.content
            tv_time.text = TimeUtils.millis2String(it.commentTime, SimpleDateFormat("yyyy-MM-dd HH:mm"))
            tv_isAuthor.visible(it.isAuthor)
            tv_from.text = Constant.DEVICE_TYPE[it.deviceType]

            val decoration = LinearItemDecoration()
            //分割线与左右两边的间距
            decoration.leftMargin = ArmsUtils.dip2px(this, 18f)
            decoration.rightMargin = ArmsUtils.dip2px(this, 18f)

            recyclerView.addItemDecoration(decoration)
            recyclerView.adapter = mAdapter
            mAdapter.apply {
                setEnableLoadMore(true)
                setLoadMoreView(CustomLoadMoreView())
                setOnLoadMoreListener({
                    mPresenter?.getReplyList(it.commentId, false)
                }, recyclerView)
            }
            tv_comment.setOnClickListener { mDialog.show() }
            mPresenter?.getReplyList(it.commentId, true)
        }
    }

    override fun showState(state: Int) {
        multiStateView.viewState = state
    }

}
