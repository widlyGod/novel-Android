package com.novel.cn.mvp.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.novel.cn.R
import com.novel.cn.app.Constant
import com.novel.cn.app.Preference
import com.novel.cn.app.loadImage
import com.novel.cn.app.visible
import com.novel.cn.di.component.DaggerCommentDetailComponent
import com.novel.cn.di.module.CommentDetailModule
import com.novel.cn.ext.toast
import com.novel.cn.mvp.contract.CommentDetailContract
import com.novel.cn.mvp.model.entity.Comment
import com.novel.cn.mvp.model.entity.LoginInfo
import com.novel.cn.mvp.model.entity.NovelInfoBean
import com.novel.cn.mvp.presenter.CommentDetailPresenter
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
    private val book by lazy { intent.getParcelableExtra<NovelInfoBean?>("book") }

    @Inject
    lateinit var mAdapter: BookReplyAdapter


    private val mDialog by lazy {
        val dialog = CommentDialog(this)
        dialog.setOnReleaseClickListener {
            val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
            if (!user?.userId.isNullOrEmpty()) {
                val isAuthor = if (user?.userId == mComment?.commentUser?.userId && mComment.isAuthor) "1" else "0"
                mPresenter?.reply(mComment.commentId, it, mComment.commentUser.userId, 0, isAuthor)
            } else {
                toast("请先登录")
            }
        }
        dialog
    }

    enum class LEVEL private constructor(val startValue: Int, val endValue: Int, val color: Long, val text: String) {

        LEVEL_1(1, 499, 0xFFF4B2BD, "见习"),
        LEVEL_2(500, 999, 0xFFFFB05B, "书虫"),
        LEVEL_3(1000, 1999, 0xFF43DDC2, "书迷"),
        LEVEL_4(2000, 4999, 0xFFFF7A8A, "书呆"),
        LEVEL_5(5000, 7999, 0xFF9393FF, "书钰"),
        LEVEL_6(8000, 9999, 0xFFFF97EF, "书侯"),
        LEVEL_7(10000, 19999, 0xFFA1E231, "书尊"),
        LEVEL_8(20000, 29999, 0xFFC5C30E, "书王"),
        LEVEL_9(30000, 49999, 0xFF57D3FC, "书皇"),
        LEVEL_10(50000, 79999, 0xFF3AA3BA, "书仙"),
        LEVEL_11(80000, 99999, 0xFF7598C4, "书神"),
        LEVEL_12(100000, 149999, 0xFFB063A4, "书圣"),
        LEVEL_13(150000, 199999, 0xFF2F26D, "白银十圣"),
        LEVEL_14(200000, 499999, 0xFFF8C108, "黄金十圣"),
        LEVEL_15(500000, 799999, 0xFFB073FA, "白金十圣"),
        LEVEL_16(800000, 999999, 0xFFBB854B, "钻石十圣"),
        LEVEL_17(1000000, Int.MAX_VALUE, 0xFFA5A3AD, "书虫"),
    }

    private val levelList = ArrayList<BookReplyAdapter.LEVEL>()

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

    override fun initStatusBar(savedInstanceState: Bundle?) {
        StatusBarUtils.darkMode(this)
        StatusBarUtils.setPaddingSmart(this, toolbar)
    }

    override fun initData(savedInstanceState: Bundle?) {

        mComment?.let { it ->
            //初始化数据
            val ss = SpannableString("共${it.counts}条")
            ss.setSpan(ForegroundColorSpan(Color.parseColor("#EE4B1A")), 1, (1 + it.counts.toString().length), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            tv_count.text = ss
            iv_avatar.loadImage(it.commentUser.userPhoto)
            if (book?.novelInfo?.authorId == it.commentUser.userId) {
                tv_isAuthor.visible(true)
                tv_nickname.text = book?.novelInfo?.novelAuthor
            } else {
                tv_isAuthor.visible(false)
                tv_nickname.text = it.commentUser.userNickName
            }
            tv_content.text = it.content
            tv_time.text = TimeUtils.millis2String(it.commentTime, SimpleDateFormat("yyyy-MM-dd HH:mm"))

            tv_from.text = Constant.DEVICE_TYPE[it.deviceType]

            levelList.add(BookReplyAdapter.LEVEL.LEVEL_1)
            levelList.add(BookReplyAdapter.LEVEL.LEVEL_2)
            levelList.add(BookReplyAdapter.LEVEL.LEVEL_3)
            levelList.add(BookReplyAdapter.LEVEL.LEVEL_4)
            levelList.add(BookReplyAdapter.LEVEL.LEVEL_5)
            levelList.add(BookReplyAdapter.LEVEL.LEVEL_6)
            levelList.add(BookReplyAdapter.LEVEL.LEVEL_7)
            levelList.add(BookReplyAdapter.LEVEL.LEVEL_8)
            levelList.add(BookReplyAdapter.LEVEL.LEVEL_9)
            levelList.add(BookReplyAdapter.LEVEL.LEVEL_10)
            levelList.add(BookReplyAdapter.LEVEL.LEVEL_11)
            levelList.add(BookReplyAdapter.LEVEL.LEVEL_12)
            levelList.add(BookReplyAdapter.LEVEL.LEVEL_13)
            levelList.add(BookReplyAdapter.LEVEL.LEVEL_14)
            levelList.add(BookReplyAdapter.LEVEL.LEVEL_15)
            levelList.add(BookReplyAdapter.LEVEL.LEVEL_16)
            levelList.add(BookReplyAdapter.LEVEL.LEVEL_17)
            levelList.forEach { its ->
                if (it.commentUser.fansValue in (its.startValue..its.endValue)) {
                    tv_level.apply {
                        delegate.backgroundColor = its.color.toInt()
                        text = it.commentUser.levelName
                    }
                }
            }

            val decoration = LinearItemDecoration()
            //分割线与左右两边的间距
            decoration.leftMargin = ArmsUtils.dip2px(this, 18f)
            decoration.rightMargin = ArmsUtils.dip2px(this, 18f)

            recyclerView.addItemDecoration(decoration)
            recyclerView.adapter = mAdapter
            mAdapter.setBookDetail(book!!.novelInfo)
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

    override fun replySuccess(message: String) {
        toast(message)
        mDialog.dismiss()
        mPresenter?.getReplyList(mComment.commentId, true)
    }

    override fun showState(state: Int) {
        multiStateView.viewState = state
    }

}
