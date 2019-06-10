package com.novel.cn.mvp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.novel.cn.R
import com.novel.cn.di.component.DaggerBookChannelComponent
import com.novel.cn.di.module.BookChannelModule
import com.novel.cn.mvp.contract.BookChannelContract
import com.novel.cn.mvp.presenter.BookChannelPresenter
import com.novel.cn.mvp.ui.adapter.BookChannelAdapter
import kotlinx.android.synthetic.main.fragment_book_channel.*
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
        return inflater.inflate(R.layout.fragment_book_channel, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {
        val type = arguments?.get("TYPE") as Int
        recyclerView.adapter = mAdapter.also { it.type = type }
        mPresenter?.getChannel(type)
    }
    override fun changeState(state: Int) {
        multiStateView.viewState = state
    }

}
