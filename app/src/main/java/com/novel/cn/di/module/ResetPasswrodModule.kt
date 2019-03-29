package com.novel.cn.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.ResetPasswrodContract
import com.novel.cn.mvp.model.ResetPasswrodModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/20/2019 14:43
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建ResetPasswrodModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ResetPasswrodModule(private val view: ResetPasswrodContract.View) {
    @ActivityScope
    @Provides
    fun provideResetPasswrodView(): ResetPasswrodContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideResetPasswrodModel(model: ResetPasswrodModel): ResetPasswrodContract.Model {
        return model
    }
}
