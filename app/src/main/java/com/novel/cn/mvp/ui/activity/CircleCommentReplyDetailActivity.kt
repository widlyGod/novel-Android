package com.novel.cn.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.jakewharton.rxbinding3.view.clicks

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.EventBusManager
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.CircleCommentEvent
import com.jess.arms.utils.CircleEvent

import com.novel.cn.di.component.DaggerCircleCommentReplyDetailComponent
import com.novel.cn.di.module.CircleCommentReplyDetailModule
import com.novel.cn.mvp.contract.CircleCommentReplyDetailContract
import com.novel.cn.mvp.presenter.CircleCommentReplyDetailPresenter

import com.novel.cn.R
import com.novel.cn.app.Constant
import com.novel.cn.app.Preference
import com.novel.cn.app.isNull
import com.novel.cn.app.loadHeadImage
import com.novel.cn.ext.bindToLifecycle
import com.novel.cn.ext.toast
import com.novel.cn.mvp.model.entity.Content
import com.novel.cn.mvp.model.entity.LoginInfo
import com.novel.cn.mvp.ui.adapter.CircleCommentReplyAdapter
import com.novel.cn.mvp.ui.dialog.CommentDialog
import com.novel.cn.utils.TimeUtils
import kotlinx.android.synthetic.main.activity_circle_comment_reply_detail.*
import kotlinx.android.synthetic.main.item_circle_comment_reply_head.view.*
import org.jetbrains.anko.startActivity
import java.text.SimpleDateFormat
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/01/2019 14:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
/**
 * 如果没presenter
 * 你可以这样写
 *
 * @ActivityScope(請注意命名空間) class NullObjectPresenterByActivity
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */
class CircleCommentReplyDetailActivity : BaseActivity<CircleCommentReplyDetailPresenter>(), CircleCommentReplyDetailContract.View {

    override fun getReplyDetaillSuccess(content: Content) {
        header.iv_avatar.loadHeadImage(content?.commentUserPhoto)
        header.tv_nickname.text = content?.commentUserName
        header.tv_content.text = content?.commentContent
        header.tv_time.text = TimeUtils.millis2String(content?.commentTime!!, SimpleDateFormat("yyyy-MM-dd HH:mm"))
        header.iv_thumbUp.setImageResource(if (content?.hadThumbed!!) R.drawable.ic_zan_check else R.drawable.ic_zan_uncheck)
        if (!content?.hadThumbed!!) {
            header.iv_thumbUp.clicks().subscribe {
                mPresenter?.agreeReply(content?.commentId!!)
            }.bindToLifecycle(this)
        }
        header.tv_reply_num.clicks().subscribe {
            if (user.isNull() || user.userId.isBlank()) {
                startActivity<LoginActivity>()
                return@subscribe
            }
            toReplyUserId = content.commentUserId
            replyType = "0"
            dialogReply.show("@${content.commentUserName}")
        }.bindToLifecycle(this)
        header.tv_num.text = content?.thumbNum.toString()
    }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerCircleCommentReplyDetailComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .circleCommentReplyDetailModule(CircleCommentReplyDetailModule(this))
                .build()
                .inject(this)
    }

    @Inject
    lateinit var mAdapter: CircleCommentReplyAdapter

    private lateinit var user: LoginInfo
    private val commentId by lazy { intent.getStringExtra("commentId") }
    private var toReplyUserId = ""
    private var replyType = "0"

    private val header by lazy { LayoutInflater.from(this).inflate(R.layout.item_circle_comment_reply_head, recyclerView_reply, false) }

    private val dialogReply by lazy {
        val dialog = CommentDialog(this)
        dialog.setOnReleaseClickListener {
            val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
            if (!user?.userId.isNullOrEmpty()) {
                mPresenter?.chapterComment(commentId, it, toReplyUserId, replyType)
                if (dialog.isShowing)
                    dialog.dismiss()
                hideSoftKeyboard()
            } else {
                toast("请先登录")
            }
        }
        dialog
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_circle_comment_reply_detail //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {
        user = Preference.getDeviceData(Constant.LOGIN_INFO)!!
        mAdapter.addHeaderView(header)
        onRefresh()
        recyclerView_reply.adapter = mAdapter.apply {
            setOnLoadMoreListener({
                mPresenter?.getReplys(commentId, false)
            }, recyclerView_reply)
            setOnLikeClickListener {
                mPresenter?.agreeReplyReply(it)
            }
            setOnReplyClickListener {
                if (user.isNull() || user.userId.isBlank()) {
                    startActivity<LoginActivity>()
                    return@setOnReplyClickListener
                }
                toReplyUserId = data[it].replyUserId
                replyType = "1"
                dialogReply.show("@${this.data[it].replyUserName}")
            }
        }
        refreshLayout.setOnRefreshListener {
            onRefresh()
        }
    }

    override fun RefreshFinsh() {
        refreshLayout.finishRefresh()
    }


    private fun onRefresh() {
        mPresenter?.getReplyDetail(commentId)
        mPresenter?.getReplys(commentId)
    }

    override fun agreeSuccess() {
        mPresenter?.getReplyDetail(commentId)
        header.iv_thumbUp.setOnClickListener(null)
        EventBusManager.getInstance().post(CircleCommentEvent())
    }

    override fun chapterCommentSuccess() {
        mPresenter?.getReplys(commentId)
        EventBusManager.getInstance().post(CircleCommentEvent())
    }

}
