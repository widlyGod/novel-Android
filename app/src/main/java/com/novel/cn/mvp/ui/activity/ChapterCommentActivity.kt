package com.novel.cn.mvp.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.DeviceUtils
import com.novel.cn.R
import com.novel.cn.app.Constant
import com.novel.cn.app.Preference
import com.novel.cn.di.component.DaggerChapterCommentComponent
import com.novel.cn.di.module.ChapterCommentModule
import com.novel.cn.ext.textWatcher
import com.novel.cn.ext.toast
import com.novel.cn.mvp.contract.ChapterCommentContract
import com.novel.cn.mvp.model.entity.LoginInfo
import com.novel.cn.mvp.presenter.ChapterCommentPresenter
import com.novel.cn.mvp.ui.adapter.ChapterCommentAdapter
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.CustomLoadMoreView
import com.novel.cn.view.decoration.LinearItemDecoration
import kotlinx.android.synthetic.main.activity_chapter_comment.*
import kotlinx.android.synthetic.main.include_title.*
import kotlinx.android.synthetic.main.item_rank.view.*
import kotlinx.android.synthetic.main.layout_chapter_comment_header.*
import kotlinx.android.synthetic.main.layout_chapter_comment_header.view.*
import javax.inject.Inject


class ChapterCommentActivity : BaseActivity<ChapterCommentPresenter>(), ChapterCommentContract.View {


    private val mChapterId by lazy { intent.getStringExtra("chapterId") }

    private val mBookId by lazy { intent.getStringExtra("bookId") }

    private val mVolumeId by lazy { intent.getStringExtra("volumeId") }

    private val mAuthorId by lazy { intent.getStringExtra("authorId") }

    @Inject
    lateinit var mAdapter: ChapterCommentAdapter

    private val mHeaderView by lazy {
        LayoutInflater.from(this).inflate(R.layout.layout_chapter_comment_header, recyclerView, false)
    }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerChapterCommentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .chapterCommentModule(ChapterCommentModule(this))
                .build()
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_chapter_comment
    }

    override fun releaseCommentSuccess() {
        mHeaderView.et_content.text = null
        DeviceUtils.hideSoftKeyboard(this, mHeaderView.et_content)
        mPresenter?.getChapterComment(mBookId, mChapterId, true)
    }

    override fun showCount(counts: Int) {
        tv_count.text = Html.fromHtml("共<font color='#ea4b1a'>$counts</font>条")
    }

    override fun initData(savedInstanceState: Bundle?) {
        StatusBarUtils.darkMode(this)
        StatusBarUtils.setPaddingSmart(this, toolbar)

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
                mPresenter?.getChapterComment(mBookId, mChapterId, false)
            }, recyclerView)
            setOnDeleteClickListener {

            }
        }
        mAdapter.setHeaderView(mHeaderView)
        mPresenter?.getChapterComment(mBookId, mChapterId, true)
        mHeaderView.et_content.textWatcher {
            onTextChanged { charSequence, start, before, count ->
                mHeaderView.tv_words.text = "${ mHeaderView.et_content.text.length}/200"

                if (mHeaderView.et_content.text.isNotEmpty()) {
                    mHeaderView.tv_release.delegate.backgroundColor = Color.parseColor("#5e8fca")
                } else {
                    mHeaderView.tv_release.delegate.backgroundColor = Color.parseColor("#c1c1c1")
                }
            }
        }

        mHeaderView.tv_release.setOnClickListener {
            val content = mHeaderView.et_content.text.toString()
            if (content.isEmpty()) {
                toast("内容不能为空！")
                return@setOnClickListener
            }
            val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
            val isAuthor = if (mAuthorId == user?.userId) "1" else "0"

            mPresenter?.chapterComment(mBookId, mChapterId, mVolumeId, content, isAuthor)
        }
    }


}
