package com.novel.cn.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.novel.cn.R
import com.novel.cn.di.component.DaggerCategoryComponent
import com.novel.cn.di.module.CategoryModule
import com.novel.cn.ext.bindToLifecycle
import com.novel.cn.mvp.contract.CategoryContract
import com.novel.cn.mvp.presenter.CategoryPresenter
import com.novel.cn.mvp.ui.adapter.CategoryAdapter
import com.novel.cn.view.MultiStateView
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.fragment_category.multiStateView
import kotlinx.android.synthetic.main.fragment_category.recyclerView
import kotlinx.android.synthetic.main.fragment_ranking.*
import javax.inject.Inject


class CategoryFragment : BaseFragment<CategoryPresenter>(), CategoryContract.View {

    @Inject
    lateinit var mAdapter: CategoryAdapter

    companion object {
        fun newInstance(): CategoryFragment {
            val fragment = CategoryFragment()
            return fragment
        }
    }


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerCategoryComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .categoryModule(CategoryModule(this))
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {
        recyclerView.adapter = mAdapter
        recyclerView.setHasFixedSize(true)
        mPresenter?.getCategory()
    }

    override fun showState(state: Int) {
        multiStateView.viewState = state
        if (state == MultiStateView.VIEW_STATE_ERROR) {
            multiStateView.clicks().subscribe {
                mPresenter?.getCategory()
            }.bindToLifecycle(this)
        } else {
            multiStateView.setOnClickListener(null)
        }
    }

}
