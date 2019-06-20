package com.novel.cn.di.module

import android.support.v4.app.Fragment
import com.flyco.tablayout.listener.CustomTabEntity
import com.jess.arms.di.scope.ActivityScope
import com.novel.cn.R

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.MainContract
import com.novel.cn.mvp.model.MainModel
import com.novel.cn.mvp.model.entity.TabEntity
import com.novel.cn.mvp.ui.fragment.*
import java.util.ArrayList
import javax.inject.Named


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/18/2019 10:17
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建MainModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class MainModule(private val view: MainContract.View) {
    @ActivityScope
    @Provides
    fun provideMainView(): MainContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideMainModel(model: MainModel): MainContract.Model {
        return model
    }


    @ActivityScope
    @Provides
    @Named("NavTabEntity")
    fun provideNavTabEntiy(): ArrayList<CustomTabEntity> {
        val title = arrayOf("书架", "书城", "发现", "我的")
        val unselectIcon = intArrayOf(R.drawable.main_bs,
                R.drawable.main_bc, R.drawable.main_find, R.drawable.main_mine)
        val selectIcon = intArrayOf(R.drawable.mian_bss, R.drawable.main_bcs,
                R.drawable.main_finds, R.drawable.main_mines)
        val tabEntities = ArrayList<CustomTabEntity>()
        (0 until title.size).mapTo(tabEntities) { TabEntity(title[it], selectIcon[it], unselectIcon[it]) }
        return tabEntities
    }


    @ActivityScope
    @Provides
    @Named("NavFragment")
    fun provideNavFragment():Array<Fragment> = arrayOf(BookshelfFragment.newInstance(), BookStoreFragment.newInstance(), DiscoveryFragment.newInstance(), MyFragment.newInstance(), NoLoginFragment.newInstance())



}
