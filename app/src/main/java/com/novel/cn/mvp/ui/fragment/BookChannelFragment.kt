package com.novel.cn.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.jess.arms.utils.LogUtils
import com.novel.cn.R
import com.novel.cn.di.component.DaggerBookChannelComponent
import com.novel.cn.di.module.BookChannelModule
import com.novel.cn.ext.bindToLifecycle
import com.novel.cn.mvp.contract.BookChannelContract
import com.novel.cn.mvp.presenter.BookChannelPresenter
import com.novel.cn.mvp.ui.adapter.BookChannelAdapter
import com.novel.cn.view.MultiStateView
import kotlinx.android.synthetic.main.fragment_book_channel.*
import kotlinx.android.synthetic.main.fragment_book_channel.multiStateView
import kotlinx.android.synthetic.main.fragment_book_channel.recyclerView
import kotlinx.android.synthetic.main.fragment_ranking.*
import org.greenrobot.eventbus.Subscribe
import javax.inject.Inject


class BookChannelFragment : BaseFragment<BookChannelPresenter>(), BookChannelContract.View {


    companion object {
        /**
         * Type：
         *      2 精选
         *      3 男频
         *      4 女频
         */
        fun newInstance(type: Int): BookChannelFragment {
            val fragment = BookChannelFragment()
            val bundle = Bundle()
            bundle.putInt("TYPE", type)
            fragment.arguments = bundle
            return fragment
        }
    }

    @Inject
    lateinit var mAdapter: BookChannelAdapter


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerBookChannelComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .bookChannelModule(BookChannelModule(this))
                .build()
                .inject(this)
    }


    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_book_channel, container, false)
    }

    var type = 0

    override fun initData(savedInstanceState: Bundle?) {
        type = arguments?.get("TYPE") as Int
        recyclerView.adapter = mAdapter.also { it.type = type }
        mPresenter?.getChannel(type)
    }



    override fun changeState(state: Int) {
        multiStateView.viewState = state
        if (state == MultiStateView.VIEW_STATE_ERROR) {
            multiStateView.clicks().subscribe {
                mPresenter?.getChannel(type)
            }.bindToLifecycle(this)
        } else {
            multiStateView.setOnClickListener(null)
        }
    }

}
