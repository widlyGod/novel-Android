package com.novel.cn.mvp.ui.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.jess.arms.base.BaseFragment
import com.jess.arms.base.BaseLazyLoadFragment
import com.jess.arms.di.component.AppComponent
import com.novel.cn.R
import com.novel.cn.app.Constant
import com.novel.cn.app.Preference
import com.novel.cn.di.component.DaggerBookshelfComponent
import com.novel.cn.di.module.BookshelfModule
import com.novel.cn.mvp.contract.BookshelfContract
import com.novel.cn.mvp.model.entity.Book
import com.novel.cn.mvp.model.entity.SignIn
import com.novel.cn.mvp.model.entity.UserInfo
import com.novel.cn.mvp.presenter.BookshelfPresenter
import com.novel.cn.mvp.ui.adapter.BookshelfAdapter
import com.novel.cn.mvp.ui.dialog.MorePopup
import com.novel.cn.mvp.ui.dialog.SignInDialog
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.CustomLoadMoreView
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import kotlinx.android.synthetic.main.fragment_bookshelf.*
import javax.inject.Inject


class BookshelfFragment : BaseLazyLoadFragment<BookshelfPresenter>(), BookshelfContract.View {
    override fun lazyLoadData() {

    }


    @Inject
    lateinit var mMorePopup: MorePopup

    @Inject
    lateinit var mSignInDialog: SignInDialog

    @Inject
    lateinit var mAdapter: BookshelfAdapter


    companion object {
        fun newInstance(): BookshelfFragment {
            val fragment = BookshelfFragment()
            return fragment
        }
    }


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerBookshelfComponent
                .builder()
                .appComponent(appComponent)
                .bookshelfModule(BookshelfModule(this))
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_bookshelf, container, false);
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun initData(savedInstanceState: Bundle?) {
        //给布局加一个状态栏高度
        StatusBarUtils.setPaddingSmart(activity, rl_top)

        recyclerView.adapter = mAdapter
        mAdapter.apply {
            setEnableLoadMore(true)
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({
                mPresenter?.getBookshelfList(false)
            }, recyclerView)
        }

        refreshLayout.setOnRefreshListener {
            mPresenter?.validateSignIn()
            mPresenter?.getBookshelfList(true)
        }

        refreshLayout.autoRefresh()
        iv_more.setOnClickListener {
            mMorePopup.showAsDropDown(it, 0, 0)
        }
        tv_signIn.setOnClickListener {
            val user = Preference.getDeviceData<UserInfo>(Constant.USER_INFO)
            user?.let {
                mPresenter?.signIn(it.userId)
            }
        }
    }

    override fun changeSignInInfo(data: SignIn) {
        val signed = data.signed
        tv_signIn.isEnabled = !signed
        if (!signed) {
            tv_signIn.text = "立即签到"
            val drawable = resources.getDrawable(R.drawable.bs_ljqd);
            drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight);
            tv_signIn.setCompoundDrawables(drawable, null, null, null)
        } else {
            tv_signIn.setCompoundDrawables(null, null, null, null)
            tv_signIn.text = "已签到"
        }
    }

    override fun signInSuccess() {
        mSignInDialog.show()
        //签到成功，禁止点击
        tv_signIn.isEnabled = false
        tv_signIn.setCompoundDrawables(null, null, null, null)
        tv_signIn.text = "已签到"

    }


    override fun getContext(): Context = super.getContext()!!


    override fun complete(pullToRefresh: Boolean) {
        if (pullToRefresh)
            refreshLayout.finishRefresh()
        else
            mAdapter.loadMoreComplete()
    }

    override fun noMore() {
        mAdapter.loadMoreEnd()
    }

    override fun showBookshelfList(pullToRefresh: Boolean, book: List<Book>) {
        if (pullToRefresh)
            mAdapter.setNewData(book)
        else
            mAdapter.addData(book)
    }


}
