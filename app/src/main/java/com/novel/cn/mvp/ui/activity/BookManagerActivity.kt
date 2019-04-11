package com.novel.cn.mvp.ui.activity

import android.os.Bundle
import android.support.v7.widget.SimpleItemAnimator
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.EventBusManager
import com.novel.cn.R
import com.novel.cn.app.click
import com.novel.cn.di.component.DaggerBookManagerComponent
import com.novel.cn.di.module.BookManagerModule
import com.novel.cn.eventbus.BookshelfEvent
import com.novel.cn.mvp.contract.BookManagerContract
import com.novel.cn.mvp.presenter.BookManagerPresenter
import com.novel.cn.mvp.ui.adapter.BookManagerAdapter
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.CustomLoadMoreView
import kotlinx.android.synthetic.main.activity_book_manager.*
import javax.inject.Inject


class BookManagerActivity : BaseActivity<BookManagerPresenter>(), BookManagerContract.View {


    @Inject
    lateinit var mAdapter: BookManagerAdapter

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerBookManagerComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .bookManagerModule(BookManagerModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_book_manager
    }


    override fun initData(savedInstanceState: Bundle?) {
        //白底黑字
        StatusBarUtils.darkMode(this)
        //给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, title_height)


        mAdapter.apply {
            //加载更多设置
            setEnableLoadMore(true)
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({
                mPresenter?.getBookList(false)
            }, recyclerView)
            //监听
            onCheckChange {
                tv_delete.text = "删除（${it}）"
            }
        }
        recyclerView.adapter = mAdapter
        //取消默认动画,避免notify闪烁
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        //点击事件
        click(tv_done, tv_move, tv_checkAll, tv_delete) {
            when (it) {
                tv_done -> finish()
                tv_checkAll -> mAdapter.checkAll()
                tv_delete -> mPresenter?.deleteBook(mAdapter.getCheckList())
            }
        }
        mPresenter?.getBookList(true)
    }

    /**
     * 服务器删除成功了，把本地保存的也清空
     */
    override fun deleteSuccess() {
        mAdapter.cleanCheck()
        EventBusManager.getInstance().post(BookshelfEvent())
    }

}
