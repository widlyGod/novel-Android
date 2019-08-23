package com.novel.cn.mvp.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.EventBusManager
import com.jess.arms.utils.CircleCommentEvent
import com.jess.arms.utils.CircleEvent
import com.lzy.ninegrid.ImageInfo
import com.lzy.ninegrid.preview.NineGridViewClickAdapter
import com.novel.cn.R
import com.novel.cn.app.*
import com.novel.cn.di.component.DaggerCircleCommentComponent
import com.novel.cn.di.module.CircleCommentModule
import com.novel.cn.ext.toast
import com.novel.cn.mvp.contract.CircleCommentContract
import com.novel.cn.mvp.model.entity.Circle
import com.novel.cn.mvp.model.entity.LoginInfo
import com.novel.cn.mvp.presenter.CircleCommentPresenter
import com.novel.cn.mvp.ui.adapter.CircleCommentAdapter
import com.novel.cn.mvp.ui.adapter.CircleImageAdapter
import com.novel.cn.mvp.ui.dialog.CommentDialog
import com.novel.cn.view.MultiStateView
import kotlinx.android.synthetic.main.activity_book_detail.*
import kotlinx.android.synthetic.main.activity_circle_comment.*
import kotlinx.android.synthetic.main.activity_circle_comment.recyclerView
import kotlinx.android.synthetic.main.item_circle.view.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity
import java.util.*
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/29/2019 11:37
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
class CircleCommentActivity : BaseActivity<CircleCommentPresenter>(), CircleCommentContract.View {

    private val momentId by lazy { intent.getStringExtra("momentId") }

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerCircleCommentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .circleCommentModule(CircleCommentModule(this))
                .build()
                .inject(this)
    }

    private val header by lazy { LayoutInflater.from(this).inflate(R.layout.item_circle, recyclerView, false) }
    val footerEmpty by lazy { LayoutInflater.from(this).inflate(R.layout.layout_empty, recyclerView, false) }
    val footerError by lazy { LayoutInflater.from(this).inflate(R.layout.layout_error, recyclerView, false) }

    private val dialog by lazy {
        val dialog = CommentDialog(this)
        dialog.setOnReleaseClickListener {
            val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
            if (!user?.userId.isNullOrEmpty()) {
                mPresenter?.chapter(momentId, it)
                if (dialog.isShowing)
                    dialog.dismiss()
                hideSoftKeyboard()
            } else {
                toast("请先登录")
            }
        }
        dialog
    }

    private val dialogComment by lazy {
        val dialog = CommentDialog(this)
        dialog.setOnReleaseClickListener {
            val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
            if (!user?.userId.isNullOrEmpty()) {
                mPresenter?.chapterComment(commentId, it, toReplyUserId)
                if (dialog.isShowing)
                    dialog.dismiss()
                hideSoftKeyboard()
            } else {
                toast("请先登录")
            }
        }
        dialog
    }

    @Inject
    lateinit var mCircleCommentAdapter: CircleCommentAdapter

    private lateinit var user: LoginInfo
    private var commentId = ""
    private var toReplyUserId = ""

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_circle_comment //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initData(savedInstanceState: Bundle?) {
        user = Preference.getDeviceData(Constant.LOGIN_INFO)!!
        onRefresh()
        mCircleCommentAdapter.setHeaderView(header)
        recyclerView.adapter = mCircleCommentAdapter.apply {
            setOnLoadMoreListener({
                mPresenter?.getComments(momentId, false)
            }, recyclerView)
            setOnLikeClickListener {
                mPresenter?.agreeComment(it)
            }
            setOnUnLikeClickListener {
                mPresenter?.disAgreeComment(it)
            }
            setOnReplyClickListener {
                if (user.isNull() || user.userId.isBlank()) {
                    startActivity<LoginActivity>()
                    return@setOnReplyClickListener
                }
                commentId = data[it].commentId
                toReplyUserId = data[it].commentUserId
                dialogComment.show("@${this.data[it].commentUserName}")
            }
            setOnCommentReplyMoreClickListenerListener {
                JumpManager.toCircleCommentReplyDetail(this@CircleCommentActivity, this.data[it].commentId)
            }
            setOnDeleteClickListener {
                mPresenter?.deleteCircleComment(data[it].commentId)
            }
        }
        refreshLayout.setOnRefreshListener {
            onRefresh()
        }
    }

    private fun onRefresh() {
        mPresenter?.getComments(momentId)
        mPresenter?.getMomentDetail(momentId)
    }

    override fun refreshFinish() {
        refreshLayout.finishRefresh()
        intent.setClass(this, MainActivity::class.java!!)
    }
    
    override fun getMomentDetailSuccess(circle: Circle) {
        header.iv_avatar.loadHeadImage(circle.userPhoto)
        header.tv_user_name.text = circle.userName
        header.tv_circle_title.visible(circle.momentTitle.isNotEmpty())
        header.tv_circle_title.text = circle.momentTitle
        header.tv_circle_content.text = circle.momentContent
        header.tv_num.text = circle.likeNum.toString()
        header.tv_location.text = circle.address?.address
        header.ll_location_selected_show.visible(!circle.address.isNull())
        header.ll_circle_reply_num.visible(true)
        val spannableString = SpannableString("共${circle.commentNum}条")
        spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#ea4b1a")), 1, spannableString.length - 1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        header.tv_circle_reply_num.text = spannableString
        tv_agree_num.text = circle.likeNum.toString()
        header.tv_comment_num.text = circle.commentNum.toString()
        tv_comment_num_circle.text = circle.commentNum.toString()
        header.tv_isAuthor.visible(circle.beNovelAuthor)
        header.iv_thumbUp.setImageResource(if (circle.hadThumbed) R.drawable.ic_zan_check else R.drawable.ic_zan_uncheck)
        iv_agree_num.setImageResource(if (circle.hadThumbed) R.drawable.ic_zan_check else R.drawable.ic_circle_agree)
        header.rl_book_detail.setOnClickListener {
            JumpManager.jumpBookDetail(this, circle.novelInfo.novelId)
        }
        rl_agree_num.setOnClickListener {
            if (user.isNull() || user.userId.isBlank()) {
                startActivity<LoginActivity>()
                return@setOnClickListener
            }
            if (!circle.hadThumbed)
                mPresenter?.agree(momentId)
            else
                mPresenter?.disAgree(momentId)
        }
        header.ll_like.setOnClickListener {
            if (user.isNull() || user.userId.isBlank()) {
                startActivity<LoginActivity>()
                return@setOnClickListener
            }
            if (!circle.hadThumbed)
                mPresenter?.agree(momentId)
            else
                mPresenter?.disAgree(momentId)
        }

        rl_comment_num.setOnClickListener {
            if (user.isNull() || user.userId.isBlank()) {
                startActivity<LoginActivity>()
                return@setOnClickListener
            }
            dialog.show("@${circle.userName}")
        }
        header.ll_comment.setOnClickListener {
            if (user.isNull() || user.userId.isBlank()) {
                startActivity<LoginActivity>()
                return@setOnClickListener
            }
            dialog.show("@${circle.userName}")
        }
        when (circle.momentType) {
            2 -> {
                header.rl_book_detail.visible(true)
                header.recycler.visible(false)
                header.iv_book_image.loadImage(circle?.novelInfo?.novelPhoto)
                header.tv_book_name.text = circle?.novelInfo?.novelTitle
                header.tv_book_detail.text = "书评${circle?.novelInfo?.commentNum}  书友${circle?.novelInfo?.readNum}  周排名" + if (circle?.novelInfo?.weeklyRank.toInt() > 99) "99+" else circle?.novelInfo?.weeklyRank
            }
            0 -> {
                header.rl_book_detail.visible(false)
                header.recycler.visible(false)
            }
            1 -> {
                header.rl_book_detail.visible(false)
                header.recycler.visible(true)
                val imageInfo = ArrayList<ImageInfo>()
                val images = circle.imgUrls
                if (images != null) {
                    for (image in images) {
                        val info = ImageInfo()
                        info.setThumbnailUrl(image.litUrl)
                        info.setBigImageUrl(image.url)
                        imageInfo.add(info)
                    }
                }
                header.recycler.setAdapter(CircleImageAdapter().apply { setNewData(imageInfo) })
            }
        }
    }

    override fun showState(state: Int) {
        when (state) {
            MultiStateView.VIEW_STATE_EMPTY -> mCircleCommentAdapter.setFooterView(footerEmpty)
            MultiStateView.VIEW_STATE_ERROR -> mCircleCommentAdapter.setFooterView(footerError)
            MultiStateView.VIEW_STATE_CONTENT -> mCircleCommentAdapter.removeAllFooterView()
        }
    }

    override fun agreeSuccess() {
        mPresenter?.getMomentDetail(momentId)
        EventBusManager.getInstance().post(CircleEvent())
    }

    override fun chapterCommentSuccess() {
        mPresenter?.getComments(momentId)
        mPresenter?.getMomentDetail(momentId)
        EventBusManager.getInstance().post(CircleEvent())
    }

    override fun showLoading() {
        rl_loading.visible(true)
    }

    override fun hideLoading() {
        rl_loading.visible(false)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun CircleCommentEvent(event: CircleCommentEvent) {
        mPresenter?.getComments(momentId)
    }

}
