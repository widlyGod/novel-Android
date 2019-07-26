package com.novel.cn.mvp.ui.fragment

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
import com.jess.arms.integration.EventBusManager
import com.novel.cn.R
import com.novel.cn.di.component.DaggerBookStoreComponent
import com.novel.cn.di.module.BookStoreModule
import com.novel.cn.eventbus.SwitchFragmentEvent
import com.novel.cn.mvp.contract.BookStoreContract
import com.novel.cn.mvp.presenter.BookStorePresenter
import com.novel.cn.mvp.ui.activity.MainActivity
import com.novel.cn.utils.StatusBarUtils
import kotlinx.android.synthetic.main.fragment_book_store.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
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

    var selectTag = 0

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
        return inflater.inflate(R.layout.fragment_book_store, container, false)
    }

    override fun initStatusBar(savedInstanceState: Bundle?) {
        //给布局加一个状态栏高度
        StatusBarUtils.setPaddingSmart(activity, tv_title)
    }

    override fun initData(savedInstanceState: Bundle?) {

        tv_title.typeface = (activity as MainActivity).mTitleTypeface

        tabLayout.setTabData(mTabEntities)
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                switchFragment(position)
            }

            override fun onTabReselect(position: Int) {
            }
        })
        switchFragment(selectTag)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun changeTab(event: SwitchFragmentEvent) {
        var realPosition = 0
        when (event.position) {
            0 -> realPosition = 3
            1 -> realPosition = 4
            2 -> realPosition = 1
            3 -> realPosition = 2
            else -> realPosition = 0
        }
        tabLayout.currentTab = realPosition
        switchFragment(realPosition)
    }


    /**
     * 切换fragment页面
     */
    private fun switchFragment(position: Int) {
        selectTag = position
        val fragment = mFragments[position]
        val transaction = childFragmentManager.beginTransaction()
        if (fragment != mCurrentFragment) {
            if (mCurrentFragment == null) {
                transaction.add(R.id.fl_content, fragment).commitAllowingStateLoss()
            } else if (!fragment.isAdded) {
                transaction.hide(mCurrentFragment!!).add(R.id.fl_content, fragment).commitAllowingStateLoss()
            } else {
                transaction.hide(mCurrentFragment!!).show(fragment).commitAllowingStateLoss()
            }
            mCurrentFragment = fragment
        }
    }

    override fun childFragmentManager(): FragmentManager = childFragmentManager

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun onIndexChange(event: IndexEvent) {
//        if (event.index == 1)
//            switchFragment(0)
//    }

}
