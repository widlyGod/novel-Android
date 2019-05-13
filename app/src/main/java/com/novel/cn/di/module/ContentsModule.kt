package com.novel.cn.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.ContentsContract
import com.novel.cn.mvp.model.ContentsModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/30/2019 14:24
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建ContentsModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ContentsModule(private val view: ContentsContract.View) {
    @ActivityScope
    @Provides
    fun provideContentsView(): ContentsContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideContentsModel(model: ContentsModel): ContentsContract.Model {
        return model
    }
}
