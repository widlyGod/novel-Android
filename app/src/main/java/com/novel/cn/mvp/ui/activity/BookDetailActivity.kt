package com.novel.cn.mvp.ui.activity

import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.AppManager
import com.jess.arms.integration.EventBusManager
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.IndexEvent
import com.jess.arms.utils.LoginEvent
import com.novel.cn.R
import com.novel.cn.app.*
import com.novel.cn.di.component.DaggerBookDetailComponent
import com.novel.cn.di.module.BookDetailModule
import com.novel.cn.eventbus.BookshelfEvent
import com.novel.cn.ext.toast
import com.novel.cn.mvp.contract.BookDetailContract
import com.novel.cn.mvp.model.entity.Comment
import com.novel.cn.mvp.model.entity.LoginInfo
import com.novel.cn.mvp.model.entity.NovelInfoBean
import com.novel.cn.mvp.presenter.BookDetailPresenter
import com.novel.cn.mvp.ui.adapter.BookCommentAdapter
import com.novel.cn.mvp.ui.dialog.CommentDialog
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.decoration.LinearItemDecoration
import kotlinx.android.synthetic.main.activity_book_detail.*
import kotlinx.android.synthetic.main.activity_book_detail.recyclerView
import kotlinx.android.synthetic.main.activity_message.*
import kotlinx.android.synthetic.main.fragment_bookshelf.*
import kotlinx.android.synthetic.main.include_title.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject


class BookDetailActivity : BaseActivity<BookDetailPresenter>(), BookDetailContract.View {


    private val bookId by lazy { intent.getStringExtra("bookId") }

    @Inject
    lateinit var mAdapter: BookCommentAdapter

    private val dialog by lazy {
        val dialog = CommentDialog(this)

        dialog
    }

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

    override fun initStatusBar(savedInstanceState: Bundle?) {
        StatusBarUtils.darkMode(this)
        StatusBarUtils.setPaddingSmart(this, toolbar)
    }

    override fun initData(savedInstanceState: Bundle?) {

        recyclerView.adapter = mAdapter


        mAdapter.apply {
            //回复按钮点击

            setOnLikeClickListener {
                mPresenter?.agree(it)
            }
            setOnDeleteClickListener {
                mPresenter?.deleteComment(it)
            }
        }
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
        expandable_text.text = data.novelInfo.novelIntro
        expand_text_view.setText(data.novelInfo.novelIntro)
        tv_chapter.text = "更新至${data.novelInfo.chapterCount}章"
        tv_review_count.text = data.comment.totalCount.toString()
        tv_add_bookself.text = if (data.novelInfo.isCollection) "已在书架" else "加入书架"
        mAdapter.setBookDetail(data.novelInfo)
        mAdapter.setNewData(data.comment.comments)
        mAdapter.setOnReplyClickListener { position ->
            JumpManager.toCommentDetail(this@BookDetailActivity, mAdapter.getItem(position), data)
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            JumpManager.toCommentList(this@BookDetailActivity, data)
        }

        click(tv_read, tv_add_bookself, ll_comment, tv_comment, fl_contents) {
            val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
            when (it) {
                ll_comment -> JumpManager.toCommentList(this, data)
                tv_comment -> {
                    if (user!!.userId.isBlank()) {
                        startActivity<LoginActivity>()
                        return@click
                    }
                    dialog.show()
                }
                tv_read -> JumpManager.jumpRead(this, data)
                tv_add_bookself -> {
                    if (user!!.userId.isBlank()) {
                        startActivity<LoginActivity>()
                        return@click
                    }
                    if (data.novelInfo.isCollection) {
                        toast("该本小说已被主人收藏啦~")
                    } else {
                        mPresenter?.addCollection(data.novelInfo.novelId)
                    }
                }
                fl_contents -> JumpManager.jumpContents(this, data)
            }
        }
        dialog.setOnReleaseClickListener {
            val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
            if (!user?.userId.isNullOrEmpty()) {
                val isAuthor = if (user?.userId == data.novelInfo.authorId) "1" else "0"
                mPresenter?.comment(data.novelInfo.novelId,
                        data.novelInfo.novelTitle,
                        data.novelInfo.authorId,
                        data.novelInfo.novelAuthor, isAuthor, it)
            } else {
                toast("请先登录")
            }
        }
    }

    override fun commentSuccess(message: String) {
        dialog.dismiss()
        mPresenter?.getBookDetail(bookId)
    }


    override fun conllectionSuccess() {
        tv_add_bookself.text = "已在书架"
        //通知书架页面
        EventBusManager.getInstance().post(BookshelfEvent())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bookshelf, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
        if (user!!.sessionId.isNotEmpty()) {
            EventBusManager.getInstance().post(IndexEvent())
            AppManager.getAppManager().killAll(MainActivity::class.java)
        } else
            startActivity<LoginActivity>()
        return super.onOptionsItemSelected(item)
    }


}
