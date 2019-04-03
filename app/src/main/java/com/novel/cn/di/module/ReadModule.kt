package com.novel.cn.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.ReadContract
import com.novel.cn.mvp.model.ReadModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/03/2019 15:39
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建ReadModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ReadModule(private val view: ReadContract.View) {
    @ActivityScope
    @Provides
    fun provideReadView(): ReadContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideReadModel(model: ReadModel): ReadContract.Model {
        return model
    }
}
