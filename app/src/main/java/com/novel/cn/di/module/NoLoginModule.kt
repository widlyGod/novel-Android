package com.novel.cn.di.module

import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.NoLoginContract
import com.novel.cn.mvp.model.NoLoginModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/20/2019 10:52
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建NoLoginModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class NoLoginModule(private val view: NoLoginContract.View) {
    @FragmentScope
    @Provides
    fun provideNoLoginView(): NoLoginContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideNoLoginModel(model: NoLoginModel): NoLoginContract.Model {
        return model
    }
}
