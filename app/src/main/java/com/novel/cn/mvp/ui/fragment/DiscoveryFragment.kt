package com.novel.cn.mvp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.jakewharton.rxbinding3.view.clicks
import com.jess.arms.base.BaseFragment
import com.jess.arms.di.component.AppComponent
import com.novel.cn.R
import com.novel.cn.app.JumpManager
import com.novel.cn.di.component.DaggerDiscoveryComponent
import com.novel.cn.di.module.DiscoveryModule
import com.novel.cn.ext.bindToLifecycle
import com.novel.cn.ext.dp2px
import com.novel.cn.mvp.contract.DiscoveryContract
import com.novel.cn.mvp.model.entity.BookInfo
import com.novel.cn.mvp.presenter.DiscoveryPresenter
import com.novel.cn.mvp.ui.dialog.CircleMorePopup
import com.novel.cn.utils.StatusBarUtils
import kotlinx.android.synthetic.main.fragment_book_store.*
import kotlinx.android.synthetic.main.fragment_discovery.*
import kotlinx.android.synthetic.main.fragment_discovery.tabLayout
import javax.inject.Inject
import javax.inject.Named


class DiscoveryFragment : BaseFragment<DiscoveryPresenter>(), DiscoveryContract.View {

    @Inject
    lateinit var mCircleMorePopup: CircleMorePopup

    @Inject
    @field:Named("TabEntity")
    lateinit var mTabEntities: ArrayList<CustomTabEntity>

    private var hotNovels = mutableListOf<BookInfo>()

    companion object {
        fun newInstance(): DiscoveryFragment {
            val fragment = DiscoveryFragment()
            return fragment
        }
    }

    override fun initStatusBar(savedInstanceState: Bundle?) {
        //给布局加一个状态栏高度
        StatusBarUtils.setPaddingSmart(activity, ll_tab)
    }


    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerDiscoveryComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .discoveryModule(DiscoveryModule(this))
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_discovery, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {
        iv_circle_add.clicks().subscribe {
            mCircleMorePopup.showAsDropDown(iv_circle_add, dp2px(20), -dp2px(10))
        }.bindToLifecycle(this)
        iv_circle_search.clicks().subscribe {
            if (hotNovels.isEmpty()) {
                mPresenter?.getHotSearch()
            } else
                JumpManager.jumpSearch(context, hotNovels, 0)
        }.bindToLifecycle(this)
        initTab()

    }

    private fun initTab() {
        tabLayout.setTabData(mTabEntities)
        tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
            override fun onTabSelect(position: Int) {
                vp_circle.currentItem = position
            }

            override fun onTabReselect(position: Int) {
            }
        })
        vp_circle.adapter = object : FragmentPagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return when (position) {
                    0 -> VipPowerFragment().apply { setArguments(0) }
                    else -> CircleFragment()
                }
            }

            override fun getCount() = 2
        }
        vp_circle.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                tabLayout.currentTab = position
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }

    override fun getHotSearchSuccess(list: List<BookInfo>) {
        hotNovels.addAll(list)
        JumpManager.jumpSearch(context, hotNovels, 0)
    }

    override fun getContext(): Context = super.getContext()!!


}
