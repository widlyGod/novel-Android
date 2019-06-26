package com.novel.cn.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.VipContract
import com.novel.cn.mvp.model.VipModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/24/2019 18:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建VipModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class VipModule(private val view: VipContract.View) {
    @ActivityScope
    @Provides
    fun provideVipView(): VipContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideVipModel(model: VipModel): VipContract.Model {
        return model
    }
}
