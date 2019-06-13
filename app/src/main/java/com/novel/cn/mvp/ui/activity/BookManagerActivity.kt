package com.novel.cn.mvp.ui.activity

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SimpleItemAnimator
import android.support.v7.widget.helper.ItemTouchHelper
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.EventBusManager
import com.jess.arms.utils.LogUtils
import com.novel.cn.R
import com.novel.cn.app.click
import com.novel.cn.di.component.DaggerBookManagerComponent
import com.novel.cn.di.module.BookManagerModule
import com.novel.cn.eventbus.BookshelfEvent
import com.novel.cn.mvp.contract.BookManagerContract
import com.novel.cn.mvp.presenter.BookManagerPresenter
import com.novel.cn.mvp.ui.adapter.BookManagerAdapter
import com.novel.cn.mvp.ui.weight.MyItemDragAndSwipeCallback
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.CustomLoadMoreView
import com.jess.arms.utils.TipDialog
import com.novel.cn.ext.toast
import com.novel.cn.mvp.ui.dialog.ConfirmDialog
import kotlinx.android.synthetic.main.activity_book_manager.*
import javax.inject.Inject


class BookManagerActivity : BaseActivity<BookManagerPresenter>(), BookManagerContract.View {

    @Inject
    lateinit var mAdapter: BookManagerAdapter

    private val type by lazy { intent.getIntExtra("type", 0) }

    private val tipDialog by lazy {
        TipDialog.Builder(this)
                .setTipWord("请稍后")
                .setIconType(TipDialog.Builder.ICON_TYPE_LOADING)
                .create()
    }

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

    override fun initStatusBar(savedInstanceState: Bundle?) {
        //白底黑字
        StatusBarUtils.darkMode(this)
        //给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, title_height)
    }


    override fun initData(savedInstanceState: Bundle?) {
        mAdapter.apply {
            //加载更多设置
            setEnableLoadMore(true)
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({
                mPresenter?.getBookList(false, type)
            }, recyclerView)
            //监听
            onCheckChange {
                tv_delete.text = "删除（${it}）"
            }
        }
        recyclerView.adapter = mAdapter
        //取消默认动画,避免notify闪烁
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        if (type == 0) {
            //可拖拽
            val callBack = object : MyItemDragAndSwipeCallback(mAdapter) {
                override fun onMoved(recyclerView: RecyclerView, source: RecyclerView.ViewHolder, fromPos: Int, target: RecyclerView.ViewHolder, toPos: Int, x: Int, y: Int) {
                    super.onMoved(recyclerView, source, fromPos, target, toPos, x, y)
                    val map = HashMap<String, Int>()
                    for ((a, i) in (mAdapter.data[0].orderNum downTo mAdapter.data[0].orderNum - mAdapter.data.size + 1).withIndex()) {
                        map[mAdapter.data[a].novelId] = i
                    }
                    LogUtils.warnInfo(map.toString())
                    mPresenter?.moveBook(map)
                }

            }
            val itemTouchHelper = ItemTouchHelper(callBack)

            itemTouchHelper.attachToRecyclerView(recyclerView)
            mAdapter.enableDragItem(itemTouchHelper)
        }

        //点击事件
        click(tv_done, tv_checkAll, tv_delete) {
            when (it) {
                tv_done -> finish()
                tv_checkAll -> mAdapter.checkAll()
                tv_delete -> {
                    if (mAdapter.getCheckList().size > 0)
                        ConfirmDialog(this) {
                            onConfirm = {
                                mPresenter?.deleteBook(mAdapter.getCheckList(), type)
                                dismiss()
                            }
                        }.show("确认删除吗？")
                    else
                        toast("未选中书籍")

                }
            }
        }
        mPresenter?.getBookList(true, type)
    }

    /**
     * 服务器删除成功了，把本地保存的也清空
     */
    override fun deleteSuccess() {
        mAdapter.cleanCheck()
        EventBusManager.getInstance().post(BookshelfEvent())
    }

    override fun moveSuccess() {
        EventBusManager.getInstance().post(BookshelfEvent())
    }

    override fun showLoading() {
        tipDialog.show()
    }

    override fun hideLoading() {
        tipDialog.hide()
    }
}
