package com.novel.cn.di.module

import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.BookChannelContract
import com.novel.cn.mvp.model.BookChannelModel
import com.novel.cn.mvp.ui.adapter.BookChannelAdapter


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/25/2019 09:48
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
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
