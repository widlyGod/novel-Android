package com.novel.cn.mvp.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.novel.cn.R
import com.novel.cn.app.Constant
import com.novel.cn.app.JumpManager
import com.novel.cn.app.Preference
import com.novel.cn.app.visible
import com.novel.cn.di.component.DaggerCommentComponent
import com.novel.cn.di.module.CommentModule
import com.novel.cn.ext.toast
import com.novel.cn.mvp.contract.CommentContract
import com.novel.cn.mvp.model.entity.Comment
import com.novel.cn.mvp.model.entity.LoginInfo
import com.novel.cn.mvp.model.entity.NovelInfoBean
import com.novel.cn.mvp.presenter.CommentPresenter
import com.novel.cn.mvp.ui.adapter.BookCommentAdapter
import com.novel.cn.mvp.ui.dialog.CommentDialog
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.CustomLoadMoreView
import com.novel.cn.view.decoration.LinearItemDecoration
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.include_title.*
import javax.inject.Inject


class CommentActivity : BaseActivity<CommentPresenter>(), CommentContract.View {


    private val book by lazy { intent.getParcelableExtra<NovelInfoBean?>("book") }

    private val dialog by lazy {
        val dialog = CommentDialog(this)
        dialog.setOnReleaseClickListener {
            val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
            if (!user?.userId.isNullOrEmpty()) {
                val isAuthor = if (user?.userId == book?.novelInfo?.authorId) "1" else "0"
                mPresenter?.comment(book?.novelInfo?.novelId,
                        book?.novelInfo?.novelTitle,
                        book?.novelInfo?.authorId,
                        book?.novelInfo?.novelAuthor, isAuthor, it)
            } else {
                toast("请先登录")
            }
        }
        dialog
    }

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

    override fun initStatusBar(savedInstanceState: Bundle?) {
        StatusBarUtils.darkMode(this)
        StatusBarUtils.setPaddingSmart(this, toolbar)
    }


    override fun initData(savedInstanceState: Bundle?) {

        recyclerView.adapter = mAdapter

        val decoration = LinearItemDecoration()
        //分割线与左右两边的间距
        decoration.leftMargin = ArmsUtils.dip2px(this, 18f)
        decoration.rightMargin = ArmsUtils.dip2px(this, 18f)
        recyclerView.addItemDecoration(decoration)
        //取消默认动画
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        mAdapter.apply {
            //下拉刷新设置
            setEnableLoadMore(true)
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({
                mPresenter?.getCommentList(book?.novelInfo?.novelId, false)
            }, recyclerView)
            //回复按钮点击
            setOnReplyClickListener { position ->
                JumpManager.toCommentDetail(this@CommentActivity, this.getItem(position), book!!)
            }
            setOnLikeClickListener {
                val item = mAdapter.getItem(it) as Comment
                mPresenter?.agree(it)
            }
            setOnDeleteClickListener {
                val item = mAdapter.getItem(it) as Comment
                mPresenter?.deleteComment(it)
            }
        }
        mAdapter.setBookDetail(book!!.novelInfo)
        //快速定位到顶部
        iv_back_top.setOnClickListener { recyclerView.scrollToPosition(0) }
        //滑动监听
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val position = (recyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
                iv_back_top.visible(position > 1)
            }
        })

        tv_comment.setOnClickListener { dialog.show() }

        mPresenter?.getCommentList(book?.novelInfo?.novelId, true)
    }

    override fun commentSuccess(message: String) {
        toast(message)
        dialog.dismiss()
        mPresenter?.getCommentList(book?.novelInfo?.novelId, true)
    }


    override fun showCommentCount(count: Int) {
        val ss = SpannableString("共${count}条")
        ss.setSpan(ForegroundColorSpan(Color.parseColor("#EE4B1A")), 1, (1 + count.toString().length), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_count.text = ss
    }

    override fun showState(state: Int) {
        multiStateView.viewState = state
    }

}
