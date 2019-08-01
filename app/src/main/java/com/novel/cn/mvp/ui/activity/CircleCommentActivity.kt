package com.novel.cn.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater

import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.lzy.ninegrid.ImageInfo
import com.lzy.ninegrid.preview.NineGridViewClickAdapter

import com.novel.cn.di.component.DaggerCircleCommentComponent
import com.novel.cn.di.module.CircleCommentModule
import com.novel.cn.mvp.contract.CircleCommentContract
import com.novel.cn.mvp.presenter.CircleCommentPresenter

import com.novel.cn.R
import com.novel.cn.app.*
import com.novel.cn.ext.toast
import com.novel.cn.mvp.model.entity.Circle
import com.novel.cn.mvp.model.entity.LoginInfo
import com.novel.cn.mvp.ui.adapter.CircleCommentAdapter
import com.novel.cn.mvp.ui.dialog.CommentDialog
import kotlinx.android.synthetic.main.activity_circle_comment.*
import kotlinx.android.synthetic.main.activity_circle_comment.recyclerView
import kotlinx.android.synthetic.main.activity_circle_comment.rl_loading
import kotlinx.android.synthetic.main.activity_contents.*
import kotlinx.android.synthetic.main.item_circle.view.*
import org.jetbrains.anko.startActivity
import java.util.ArrayList
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

    val header by lazy { LayoutInflater.from(this).inflate(R.layout.item_circle, recyclerView, false) }

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
                mPresenter?.chapterComment(commentId, it,toReplyUserId)
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
            setOnReplyClickListener {
                if (user.isNull() || user.userId.isBlank()) {
                    startActivity<LoginActivity>()
                    return@setOnReplyClickListener
                }
                commentId = data[it].commentId
                toReplyUserId = ""
                dialogComment.show("@${this.data[it].commentUserName}")
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

    override fun RefreshFinsh() {
        refreshLayout.finishRefresh()
    }


    override fun getMomentDetailSuccess(circle: Circle) {
        header.iv_avatar.loadHeadImage(circle.userPhoto)
        header.tv_user_name.text = circle.userName
        header.tv_circle_title.visible(circle.momentTitle.isNotEmpty())
        header.tv_circle_title.text = circle.momentTitle
        header.tv_circle_content.text = circle.momentContent
        header.tv_num.text = circle.likeNum.toString()
        tv_agree_num.text = circle.likeNum.toString()
        header.tv_comment_num.text = circle.commentNum.toString()
        tv_comment_num.text = circle.commentNum.toString()
        header.tv_isAuthor.visible(circle.beNovelAuthor)
        header.iv_thumbUp.setImageResource(if (circle.hadThumbed) R.drawable.ic_zan_check else R.drawable.ic_zan_uncheck)
        iv_agree_num.setImageResource(if (circle.hadThumbed) R.drawable.ic_zan_check else R.drawable.ic_zan_uncheck)
        if (!circle.hadThumbed) {
            rl_agree_num.setOnClickListener {
                if (user.isNull() || user.userId.isBlank()) {
                    startActivity<LoginActivity>()
                    return@setOnClickListener
                }
                mPresenter?.agree(momentId)
            }
            header.ll_like.setOnClickListener {
                if (user.isNull() || user.userId.isBlank()) {
                    startActivity<LoginActivity>()
                    return@setOnClickListener
                }
                mPresenter?.agree(momentId)
            }
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
                header.nineGrid.visible(false)
                header.iv_book_image.loadImage(circle?.novelInfo?.novelPhoto)
                header.tv_book_name.text = circle?.novelInfo?.novelTitle
                header.tv_book_detail.text = "书评${circle?.novelInfo?.commentNum}  书友${circle?.novelInfo?.readNum}  周排名${circle?.novelInfo?.weeklyRank}"
            }
            0 -> {
                header.rl_book_detail.visible(false)
                header.nineGrid.visible(false)
            }
            1 -> {
                header.rl_book_detail.visible(false)
                header.nineGrid.visible(true)
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
                header.nineGrid.setAdapter(NineGridViewClickAdapter(this, imageInfo))
            }
        }
    }

    override fun agreeSuccess() {
        rl_agree_num.setOnClickListener(null)
        header.iv_thumbUp.setOnClickListener(null)
        mPresenter?.getMomentDetail(momentId)
    }

    override fun chapterCommentSuccess() {
        mPresenter?.getComments(momentId)
    }

    override fun showLoading() {
        rl_loading.visible(true)
    }

    override fun hideLoading() {
        rl_loading.visible(false)
    }

}
