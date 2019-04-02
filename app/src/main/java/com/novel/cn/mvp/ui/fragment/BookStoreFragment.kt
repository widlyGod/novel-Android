package com.novel.cn.mvp.ui.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.novel.cn.R
import com.novel.cn.di.component.DaggerBookStoreComponent
import com.novel.cn.di.module.BookStoreModule
import com.novel.cn.mvp.contract.BookStoreContract
import com.novel.cn.mvp.presenter.BookStorePresenter
import com.novel.cn.mvp.ui.activity.MainActivity
import com.novel.cn.utils.StatusBarUtils
import kotlinx.android.synthetic.main.fragment_book_store.*
import javax.inject.Inject
import javax.inject.Named


class BookStoreFragment : BaseFragment<BookStorePresenter>(), BookStoreContract.View {


    @Inject
    @field:Named("TabEntity")
    lateinit var mTabEntities: ArrayList<CustomTabEntity>

    @Inject
    @field:Named("ChannelFragments")
    lateinit var mFragments: ArrayList<Fragment>

    private var mCurrentFragment: Fragment? = null

    companion object {
        fun newInstance(): BookStoreFragment {
            val fragment = BookStoreFragment()
            return fragment
        }
    }


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerBookStoreComponent
                .builder()
                .appComponent(appComponent)
                .bookStoreModule(BookStoreModule(this))
                .build()
                .inject(this)
    }


    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_book_store, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {
        //给布局加一个状态栏高度
        StatusBarUtils.setPaddingSmart(activity, tv_title)
        tv_title.typeface = (activity as MainActivity).mTitleTypeface

        tabLayout.setTabData(mTabEntities)
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                switchFragment(position)
            }

            override fun onTabReselect(position: Int) {
            }
        })
        switchFragment(0)
    }


    /**
     * 切换fragment页面
     */
    private fun switchFragment(position: Int) {
        val fragment = mFragments[position]
        val transaction = childFragmentManager.beginTransaction()
        if (fragment != mCurrentFragment) {
            if (mCurrentFragment == null) {
                transaction.add(R.id.fl_content, fragment).commit()
            } else if (!fragment.isAdded) {
                transaction.hide(mCurrentFragment!!).add(R.id.fl_content, fragment).commit()
            } else {
                transaction.hide(mCurrentFragment!!).show(fragment).commit()
            }
            mCurrentFragment = fragment
        }
    }

    override fun childFragmentManager(): FragmentManager = childFragmentManager

}
