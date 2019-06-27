package com.novel.cn.mvp.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.AppManager
import com.jess.arms.integration.EventBusManager
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.DeviceUtils
import com.jess.arms.utils.IndexEvent
import com.novel.cn.R
import com.novel.cn.app.Constant
import com.novel.cn.app.JumpManager
import com.novel.cn.app.Preference
import com.novel.cn.di.component.DaggerChapterCommentComponent
import com.novel.cn.di.module.ChapterCommentModule
import com.novel.cn.ext.textWatcher
import com.novel.cn.ext.toast
import com.novel.cn.mvp.contract.ChapterCommentContract
import com.novel.cn.mvp.model.entity.BookInfo
import com.novel.cn.mvp.model.entity.LoginInfo
import com.novel.cn.mvp.model.entity.NovelInfoBean
import com.novel.cn.mvp.presenter.ChapterCommentPresenter
import com.novel.cn.mvp.ui.adapter.ChapterCommentAdapter
import com.novel.cn.mvp.ui.dialog.CommentDialog
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.CustomLoadMoreView
import com.novel.cn.view.decoration.LinearItemDecoration
import kotlinx.android.synthetic.main.activity_chapter_comment.*
import kotlinx.android.synthetic.main.include_title.*
import kotlinx.android.synthetic.main.layout_chapter_comment_header.*
import kotlinx.android.synthetic.main.layout_chapter_comment_header.view.*
import kotlinx.android.synthetic.main.layout_unlogin_header.view.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject


class ChapterCommentActivity : BaseActivity<ChapterCommentPresenter>(), ChapterCommentContract.View {


    override fun getHotSearchSuccess(list: List<BookInfo>) {
        hotNovels.addAll(list)
        JumpManager.jumpSearch(this, hotNovels, 0)
    }

    private val mChapterId by lazy { intent.getStringExtra("chapterId") }

    private val mBookId by lazy { intent.getStringExtra("bookId") }

    private val mVolumeId by lazy { intent.getStringExtra("volumeId") }

    private val mAuthorId by lazy { intent.getStringExtra("authorId") }
    private val book by lazy { intent.getParcelableExtra<NovelInfoBean?>("book") }

    private val dialog by lazy {
        val dialog = CommentDialog(this)
        dialog.setOnReleaseClickListener {
            val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
            if (!user?.userId.isNullOrEmpty()) {
                val isAuthor = if (user?.userId == book?.novelInfo?.authorId) "1" else "0"
                mPresenter?.chapterComment(mBookId, mChapterId, mVolumeId, it, mAdapter.data[replyPosition].chapterCommentUser.userId, 1, isAuthor)
                if (dialog.isShowing)
                    dialog.dismiss()
                hideSoftKeyboard()
            } else {
                toast("请先登录")
            }
        }
        dialog
    }

    var replyPosition = 0

    @Inject
    lateinit var mAdapter: ChapterCommentAdapter

    private lateinit var user: LoginInfo
    private var hotNovels = mutableListOf<BookInfo>()

    private val mHeaderView by lazy {
        LayoutInflater.from(this).inflate(R.layout.layout_chapter_comment_header, recyclerView, false)
    }

    private val mUnLoginHeaderView by lazy {
        LayoutInflater.from(this).inflate(R.layout.layout_unlogin_header, recyclerView, false)
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

    override fun initStatusBar(savedInstanceState: Bundle?) {
        StatusBarUtils.darkMode(this)
        StatusBarUtils.setPaddingSmart(this, toolbar)

    }

    override fun initData(savedInstanceState: Bundle?) {
        user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)!!
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
                mPresenter?.deleteChapterComment(mBookId, mChapterId, mAdapter.data[it].replyId)
            }
            setOnReplyClickListener {
                val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
                if (user!!.userId.isBlank()) {
                    startActivity<LoginActivity>()
                    return@setOnReplyClickListener
                }
                replyPosition = it
                if (mAuthorId == mAdapter.data[it].chapterCommentUser.userId)
                    dialog.show("@${book!!.novelInfo.novelAuthor}")
                else
                    dialog.show("@${mAdapter.data[it].chapterCommentUser.userNickName}")
            }
            setOnLikeClickListener {
                val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
                if (user!!.userId.isBlank()) {
                    startActivity<LoginActivity>()
                    return@setOnLikeClickListener
                }
                mPresenter?.agree(it)
            }
        }
        if (user!!.sessionId.isBlank()) {
            mAdapter.setHeaderView(mUnLoginHeaderView)
            mUnLoginHeaderView.tv_un_login.setOnClickListener {
                startActivity<LoginActivity>()
            }
        } else {
            mAdapter.setHeaderView(mHeaderView)
            mHeaderView.et_content.textWatcher {
                onTextChanged { charSequence, start, before, count ->
                    mHeaderView.tv_words.text = "${mHeaderView.et_content.text.length}/50"

                    if (mHeaderView.et_content.text.isNotEmpty()) {
                        mHeaderView.tv_release.delegate.backgroundColor = Color.parseColor("#5e8fca")
                    } else {
                        mHeaderView.tv_release.delegate.backgroundColor = Color.parseColor("#c1c1c1")
                    }
                }
            }

            mHeaderView.tv_release.setOnClickListener {
                if (user.sessionId.isBlank()) {
                    startActivity<LoginActivity>()
                    return@setOnClickListener
                }
                val content = mHeaderView.et_content.text.toString()
                if (content.isEmpty()) {
                    toast("内容不能为空！")
                    return@setOnClickListener
                }
                val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
                val isAuthor = if (mAuthorId == user?.userId) "1" else "0"

                mPresenter?.chapterComment(mBookId, mChapterId, mVolumeId, content, mAuthorId, 0, isAuthor)
            }
        }

        mAdapter.setBookDetail(book!!.novelInfo)
        mPresenter?.getChapterComment(mBookId, mChapterId, true)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_chapter_comment, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
        if (user!!.sessionId.isNotEmpty()) {
            when (item!!.itemId) {
                R.id.action_search -> {
                    if(hotNovels.isEmpty()){
                        mPresenter?.getHotSearch()
                    }else
                        JumpManager.jumpSearch(this, hotNovels, 0)
                }
                R.id.action_bookstore -> {
                    EventBusManager.getInstance().post(IndexEvent().apply { index = 1 })
                    AppManager.getAppManager().killAll(MainActivity::class.java)
                }
            }

        } else
            startActivity<LoginActivity>()
        return super.onOptionsItemSelected(item)
    }


}
