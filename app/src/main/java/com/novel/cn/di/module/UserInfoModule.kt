package com.novel.cn.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.UserInfoContract
import com.novel.cn.mvp.model.UserInfoModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/11/2019 17:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建UserInfoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class UserInfoModule(private val view: UserInfoContract.View) {
    @ActivityScope
    @Provides
    fun provideUserInfoView(): UserInfoContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideUserInfoModel(model: UserInfoModel): UserInfoContract.Model {
        return model
    }
}
