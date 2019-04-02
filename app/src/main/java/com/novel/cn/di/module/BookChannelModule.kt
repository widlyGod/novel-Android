package com.novel.cn.di.module

import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.BookChannelContract
import com.novel.cn.mvp.model.BookChannelModel
import com.novel.cn.mvp.ui.adapter.BookChannelAdapter



@Module
//构建BookChannelModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class BookChannelModule(private val view: BookChannelContract.View) {
    @FragmentScope
    @Provides
    fun provideBookChannelView(): BookChannelContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideBookChannelModel(model: BookChannelModel): BookChannelContract.Model {
        return model
    }
    @FragmentScope
    @Provides
    fun provideBookChannelAdapter() = BookChannelAdapter()
}
