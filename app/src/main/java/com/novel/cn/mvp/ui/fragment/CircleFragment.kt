package com.novel.cn.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.BookEvent
import com.jess.arms.utils.CircleEvent
import com.novel.cn.R
import com.novel.cn.app.Constant
import com.novel.cn.app.JumpManager
import com.novel.cn.app.Preference
import com.novel.cn.app.isNull
import com.novel.cn.di.component.DaggerCircleComponent
import com.novel.cn.di.module.CircleModule
import com.novel.cn.ext.bindToLifecycle
import com.novel.cn.ext.clicks
import com.novel.cn.ext.toast
import com.novel.cn.mvp.contract.CircleContract
import com.novel.cn.mvp.model.entity.LoginInfo
import com.novel.cn.mvp.presenter.CirclePresenter
import com.novel.cn.mvp.ui.activity.LoginActivity
import com.novel.cn.mvp.ui.adapter.CircleAdapter
import com.novel.cn.mvp.ui.dialog.CommentDialog
import com.novel.cn.view.CustomLoadMoreView
import com.novel.cn.view.MultiStateView
import kotlinx.android.synthetic.main.fragment_book_channel.*
import kotlinx.android.synthetic.main.fragment_circle.*
import kotlinx.android.synthetic.main.fragment_circle.multiStateView
import kotlinx.android.synthetic.main.fragment_circle.recyclerView
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/19/2019 16:14
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
 * @FragmentScope(請注意命名空間) class NullObjectPresenterByFragment
 * @Inject constructor() : IPresenter {
 * override fun onStart() {
 * }
 *
 * override fun onDestroy() {
 * }
 * }
 */
class CircleFragment : BaseFragment<CirclePresenter>(), CircleContract.View {

    companion object {
        fun newInstance(): CircleFragment {
            val fragment = CircleFragment()
            return fragment
        }
    }

    @Inject
    lateinit var mCircleAdapter: CircleAdapter

    private lateinit var user: LoginInfo
    private var replyPosition = 0

    private val dialog by lazy {
        val dialog = CommentDialog(context!!)
        dialog.setOnReleaseClickListener {
            val user = Preference.getDeviceData<LoginInfo?>(Constant.LOGIN_INFO)
            if (!user?.userId.isNullOrEmpty()) {
                mPresenter?.chapterComment(mCircleAdapter.data[replyPosition].momentId, it, replyPosition)
                if (dialog.isShowing)
                    dialog.dismiss()
                hideSoftKeyboard()
            } else {
                toast("请先登录")
            }
        }
        dialog
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerCircleComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .circleModule(CircleModule(this))
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_circle, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        user = Preference.getDeviceData(Constant.LOGIN_INFO)!!
        recyclerView.adapter = mCircleAdapter.apply {
            setEnableLoadMore(true)
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({
                mPresenter?.getAllMoments(false)
            }, recyclerView)
            setOnLikeClickListener {
                mPresenter?.agree(it)
            }
            setOnUnLikeClickListener {
                mPresenter?.disAgree(it)
            }
            setOnReplyClickListener {
                if (user.isNull() || user.userId.isBlank()) {
                    context?.startActivity<LoginActivity>()
                    return@setOnReplyClickListener
                }
                replyPosition = it
                dialog.show("@${this.data[it].userName}")
            }
            setOnItemClickListener { _, _, position ->
                JumpManager.toCircleComment(context, mCircleAdapter.data[position - mCircleAdapter.headerLayoutCount].momentId)
            }
        }
        mPresenter?.getAllMoments()
        refreshLayout.setOnRefreshListener { mPresenter?.getAllMoments() }
    }

    override fun refreshComplete() {
        refreshLayout.finishRefresh()
    }

    override fun showState(state: Int) {
        multiStateView.viewState = state
        if (state == MultiStateView.VIEW_STATE_ERROR) {
            multiStateView.clicks().subscribe {
                mPresenter?.getAllMoments()
            }.bindToLifecycle(this)
        } else {
            multiStateView.setOnClickListener(null)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCircleEvent(event: CircleEvent) {
        mPresenter?.getAllMoments()
    }
}
