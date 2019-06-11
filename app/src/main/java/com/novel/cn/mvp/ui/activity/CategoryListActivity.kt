package com.novel.cn.mvp.ui.activity

import android.os.Bundle
import android.support.v7.widget.SimpleItemAnimator
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.integration.EventBusManager
import com.novel.cn.R
import com.novel.cn.app.JumpManager
import com.novel.cn.di.component.DaggerCategoryListComponent
import com.novel.cn.di.module.CategoryListModule
import com.novel.cn.eventbus.BookshelfEvent
import com.novel.cn.mvp.contract.CategoryListContract
import com.novel.cn.mvp.model.entity.CategoryBean
import com.novel.cn.mvp.model.entity.CategoryBook
import com.novel.cn.mvp.presenter.CategoryListPresenter
import com.novel.cn.mvp.ui.adapter.CategoryBookAdapter
import com.novel.cn.mvp.ui.adapter.RankListAdapter
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.CustomLoadMoreView
import kotlinx.android.synthetic.main.include_title.*
import kotlinx.android.synthetic.main.activity_category_list.*
import kotlinx.android.synthetic.main.activity_category_list.recyclerView
import kotlinx.android.synthetic.main.activity_category_list.refreshLayout
import kotlinx.android.synthetic.main.fragment_bookshelf.*
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/23/2019 17:25
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
class CategoryListActivity : BaseActivity<CategoryListPresenter>(), CategoryListContract.View {

    private val novelTypeId by lazy { intent.getIntExtra("novelTypeId", 0) }

    private val parentId by lazy { intent.getIntExtra("parentId", 0) }

    private val title by lazy { intent.getStringExtra("title") }

    @Inject
    lateinit var mAdapter: CategoryBookAdapter


    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerCategoryListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .categoryListModule(CategoryListModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_category_list //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initStatusBar(savedInstanceState: Bundle?) {
        //白底黑字
        StatusBarUtils.darkMode(this)
        //给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, toolbar)
    }

    override fun initData(savedInstanceState: Bundle?) {

        setTitle(title)

        mAdapter.apply {
            setEnableLoadMore(true)
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({
                mPresenter?.getCategoryList(novelTypeId, parentId, false)
            }, recyclerView)

            setOnItemClickListener { adapter, view, position ->
                val data = mAdapter.getItem(position) as CategoryBook
                JumpManager.jumpBookDetail(this@CategoryListActivity, data.novelId)
            }

            //收藏按钮点击
            setOnConllectClickListener {
                val item = mAdapter.getItem(it) as CategoryBook
                mPresenter?.addConllection(item.novelId, it)
            }

        }
        recyclerView.adapter = mAdapter
        (recyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false

        refreshLayout.setOnRefreshListener { onRefresh() }
        onRefresh()
    }

    private fun onRefresh() {
        mPresenter?.getCategoryList(novelTypeId, parentId, true)
    }

    override fun refreshComplete() {
        refreshLayout.finishRefresh()
    }

    override fun conllectionSuccess(position: Int) {
        //收藏成功后，更新页面，并通知书架
        val item = mAdapter.getItem(position)
        item?.let {
            //            it.isRecommend = 1
            mAdapter.notifyItemChanged(position)
            EventBusManager.getInstance().post(BookshelfEvent())
        }
    }
}
