package com.novel.cn.mvp.ui.activity

import android.os.Bundle
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.EventBusManager
import com.jess.arms.utils.ArmsUtils
import com.novel.cn.R
import com.novel.cn.app.JumpManager
import com.novel.cn.app.click
import com.novel.cn.app.loadImage
import com.novel.cn.di.component.DaggerBookDetailComponent
import com.novel.cn.di.module.BookDetailModule
import com.novel.cn.eventbus.BookshelfEvent
import com.novel.cn.mvp.contract.BookDetailContract
import com.novel.cn.mvp.model.entity.Comment
import com.novel.cn.mvp.model.entity.NovelInfoBean
import com.novel.cn.mvp.presenter.BookDetailPresenter
import com.novel.cn.mvp.ui.adapter.BookCommentAdapter
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.decoration.LinearItemDecoration
import kotlinx.android.synthetic.main.activity_book_detail.*
import kotlinx.android.synthetic.main.include_title.*
import org.jetbrains.anko.toast
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


        mAdapter.apply {
            setOnItemClickListener { adapter, view, position ->
                JumpManager.toCommentList(this@BookDetailActivity, bookId)
            }
            //回复按钮点击
            setOnReplyClickListener { position ->
                JumpManager.toCommentDetail(this@BookDetailActivity, this.getItem(position))
            }
            setOnLikeClickListener {
                val item = mAdapter.getItem(it) as Comment
                mPresenter?.agree(it)
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
        tv_novelIntro.text = data.novelInfo.novelIntro
        tv_chapter.text = "更新至${data.novelInfo.chapterCount}章"
        tv_review_count.text = data.comment.totalCount.toString()
        tv_add_bookself.text = if (data.novelInfo.isCollection) "已在书架" else "加入书架"

        mAdapter.setNewData(data.comment.comments)

        click(tv_read, tv_add_bookself, ll_comment, tv_comment, fl_contents) {
            when (it) {
                ll_comment, tv_comment -> JumpManager.toCommentList(this, data.novelInfo.novelId)
                tv_read -> JumpManager.jumpRead(this, data)
                tv_add_bookself -> {
                    if (data.novelInfo.isCollection) {
                        toast("该本小说已被主人收藏啦~")
                    } else {
                        mPresenter?.addCollection(data.novelInfo.novelId)
                    }
                }
                fl_contents -> JumpManager.jumpContents(this, data)
            }
        }
    }

    override fun conllectionSuccess() {
        tv_add_bookself.text = "已在书架"
        //通知书架页面
        EventBusManager.getInstance().post(BookshelfEvent())

    }


}
