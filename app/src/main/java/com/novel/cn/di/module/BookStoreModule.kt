package com.novel.cn.di.module

import android.support.v4.app.Fragment
import com.flyco.tablayout.listener.CustomTabEntity
import com.jess.arms.base.AdapterViewPager
import com.jess.arms.di.scope.FragmentScope
import com.novel.cn.mvp.contract.BookStoreContract
import com.novel.cn.mvp.model.BookStoreModel
import com.novel.cn.mvp.model.entity.TabEntity
import com.novel.cn.mvp.ui.fragment.BookChannelFragment
import com.novel.cn.mvp.ui.fragment.CategoryFragment
import com.novel.cn.mvp.ui.fragment.RankingFragment
import dagger.Module
import dagger.Provides
import javax.inject.Named


@Module
//构建BookStoreModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class BookStoreModule(private val view: BookStoreContract.View) {
    @FragmentScope
    @Provides
    fun provideBookStoreView(): BookStoreContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideBookStoreModel(model: BookStoreModel): BookStoreContract.Model {
        return model
    }

    @FragmentScope
    @Provides
    @Named("TabEntity")
    fun provideTabEnty(): ArrayList<CustomTabEntity> {
        return arrayListOf(TabEntity("精选"), TabEntity("男频"), TabEntity("女频"), TabEntity("排行"), TabEntity("分类"))
    }

    @FragmentScope
    @Provides
    @Named("ChannelFragments")
    fun provideFragmentList(): ArrayList<Fragment> {
        return arrayListOf(
                BookChannelFragment.newInstance(2),
                BookChannelFragment.newInstance(3),
                BookChannelFragment.newInstance(4),
                RankingFragment.newInstance(),
                CategoryFragment.newInstance()
        )
    }

    @FragmentScope
    @Provides
    @Named("FragmentAdapter")
    fun provideFragmentAdapter( @Named("ChannelFragments") fragments : ArrayList<Fragment>): AdapterViewPager {

        return AdapterViewPager(view.childFragmentManager(), fragments)
    }
}
