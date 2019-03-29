package com.novel.cn.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.RegistContract
import com.novel.cn.mvp.model.RegistModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/19/2019 18:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建RegistModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class RegistModule(private val view: RegistContract.View) {
    @ActivityScope
    @Provides
    fun provideRegistView(): RegistContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideRegistModel(model: RegistModel): RegistContract.Model {
        return model
    }
}
