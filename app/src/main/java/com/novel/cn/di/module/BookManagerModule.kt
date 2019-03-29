package com.novel.cn.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.BookManagerContract
import com.novel.cn.mvp.model.BookManagerModel
import com.novel.cn.mvp.ui.adapter.BookManagerAdapter


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/22/2019 10:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建BookManagerModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class BookManagerModule(private val view: BookManagerContract.View) {
    @ActivityScope
    @Provides
    fun provideBookManagerView(): BookManagerContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideBookManagerModel(model: BookManagerModel): BookManagerContract.Model {
        return model
    }
    @ActivityScope
    @Provides
    fun provideBookManagerAdapter() = BookManagerAdapter()
}
