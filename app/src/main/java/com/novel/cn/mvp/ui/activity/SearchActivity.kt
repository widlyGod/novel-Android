package com.novel.cn.mvp.ui.activity

import android.animation.ObjectAnimator
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.ArmsUtils
import com.jess.arms.utils.DeviceUtils
import com.jess.arms.utils.LoginEvent
import com.novel.cn.R
import com.novel.cn.app.JumpManager
import com.novel.cn.app.visible
import com.novel.cn.di.component.DaggerSearchComponent
import com.novel.cn.di.module.SearchModule
import com.novel.cn.ext.bindToLifecycle
import com.novel.cn.ext.clicks
import com.novel.cn.mvp.contract.SearchContract
import com.novel.cn.mvp.model.entity.BookInfo
import com.novel.cn.mvp.model.entity.SearchInfo
import com.novel.cn.mvp.presenter.SearchPresenter
import com.novel.cn.mvp.ui.adapter.HotWordAdapter
import com.novel.cn.mvp.ui.adapter.SearchRecordAdapter
import com.novel.cn.mvp.ui.adapter.SearchResultAdapter
import com.novel.cn.utils.StatusBarUtils
import com.novel.cn.view.CustomLoadMoreView
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.multiStateView
import kotlinx.android.synthetic.main.activity_search.recyclerView
import kotlinx.android.synthetic.main.activity_search.refreshLayout
import kotlinx.android.synthetic.main.fragment_bookshelf.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


class SearchActivity : BaseActivity<SearchPresenter>(), SearchContract.View {


    private val hotNovels by lazy { intent.getParcelableArrayListExtra<BookInfo>("hotNovels") }
    private val type by lazy { intent.getIntExtra("type", 0) }

    @Inject
    lateinit var mHotWordAdapter: HotWordAdapter

    @Inject
    lateinit var mSearchRecordAdapter: SearchRecordAdapter

    @Inject
    lateinit var mSearchResultAdapter: SearchResultAdapter

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerSearchComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .searchModule(SearchModule(this))
                .build()
                .inject(this)
    }


    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_search //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    private var oldX = 0

    private var isStarted = false
    private var searchWord = ""

    override fun initStatusBar(savedInstanceState: Bundle?) {
        //白底黑字
        StatusBarUtils.darkMode(this)
        //给toolbar加个上边距，避免顶上去
        StatusBarUtils.setPaddingSmart(this, ll_search)
    }

    override fun initData(savedInstanceState: Bundle?) {

        search_record_recyclerview.adapter = mSearchRecordAdapter

        mSearchRecordAdapter.clicks().subscribe {
            searchWord = mSearchRecordAdapter.data[it.second].text
            et_keyword.setText(searchWord)
            et_keyword.requestFocus()
            et_keyword.setSelection(et_keyword.text.length)
            mPresenter?.getSearchResult(searchWord, type = type)
        }.bindToLifecycle(this)

        val flexBoxLayoutManager = FlexboxLayoutManager(this)
        flexBoxLayoutManager.flexWrap = FlexWrap.WRAP      //按正常方向换行

        recyclerView.layoutManager = flexBoxLayoutManager
        recyclerView.adapter = mHotWordAdapter

        /*
        //有bug 当刚好一行的情况下，会错位
        val itemDecoration = FlexboxItemDecoration(this)
        itemDecoration.setDrawable(ContextCompat.getDrawable(this,R.drawable.divider_decoration))
        recyclerView.addItemDecoration(itemDecoration)
        */
        mHotWordAdapter.setNewData(hotNovels)

        mHotWordAdapter.setOnItemClickListener { _, _, position ->
            val item = mHotWordAdapter.getItem(position)
            JumpManager.jumpBookDetail(this, item?.novelId)
        }

        iv_clean.setOnClickListener { mPresenter?.cleanRecord(type) }

        mPresenter?.getSearchRecordList(type)

        et_keyword.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (et_keyword.text.toString().isNotBlank()) {
                    searchWord = et_keyword.text.toString()
                    mPresenter?.saveKeyword(et_keyword.text.toString(), type)
                    mPresenter?.getSearchResult(searchWord, type = type)
                }
            }
            false
        }

        rlv_search_result.adapter = mSearchResultAdapter
        mSearchResultAdapter.apply {
            setLoadMoreView(CustomLoadMoreView())
            setOnLoadMoreListener({
                mPresenter?.getSearchResult(searchWord, false, type = type)
            }, recyclerView)
        }
        refreshLayout.setOnRefreshListener { mPresenter?.getSearchResult(searchWord, type = type) }

        et_keyword.viewTreeObserver.addOnGlobalLayoutListener(
                object : ViewTreeObserver.OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        //获取输入框的坐标
                        oldX = et_keyword.left

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            et_keyword.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        }
                    }
                })

        //输入框第一次获取焦点的时候执行动画
        et_keyword.setOnFocusChangeListener { v, hasFocus ->
            if (!isStarted && hasFocus) {
                isStarted = true
                startAnim(isStarted)
            }
        }
        //取消点击
        tv_cancel.setOnClickListener { finish() }

        fl_search.setOnClickListener {
            et_keyword.isFocusable = true
            et_keyword.isFocusableInTouchMode = true
            et_keyword.requestFocus()
            DeviceUtils.showSoftKeyboard(this, et_keyword)
        }

    }

    override fun onPause() {
        hideSoftKeyboard()
        super.onPause()
    }

    private fun startAnim(hasFocus: Boolean) {
        var x = oldX.toFloat()
        if (hasFocus) {
            x = -oldX.toFloat() + ArmsUtils.dip2px(this, 10f)
        }
        val anim = ObjectAnimator.ofFloat(et_keyword, "translationX", 0f, x)
        anim.duration = 300
        anim.start()
    }

    override fun complete(pullToRefresh: Boolean) {
        if (pullToRefresh)
            refreshLayout.finishRefresh()
        else
            mSearchResultAdapter.loadMoreComplete()
    }

    override fun noMore() {
        mSearchResultAdapter.loadMoreEnd()
    }

    override fun showState(state: Int) {
        ll_search_index.visible(false)
        multiStateView.visible(true)
        multiStateView.viewState = state
    }

    override fun showsearchResult(pullToRefresh: Boolean, searchInfos: List<SearchInfo>) {
        hideSoftKeyboard()
        if (pullToRefresh)
            mSearchResultAdapter.setNewData(searchInfos)
        else
            mSearchResultAdapter.addData(searchInfos)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onLoginChange(event: LoginEvent) {
    }

}
