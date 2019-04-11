package com.novel.cn.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.RechargeContract
import com.novel.cn.mvp.model.RechargeModel
import com.novel.cn.mvp.ui.adapter.RechargeOptionAdapter


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/10/2019 16:41
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建RechargeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class RechargeModule(private val view: RechargeContract.View) {
    @ActivityScope
    @Provides
    fun provideRechargeView(): RechargeContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideRechargeModel(model: RechargeModel): RechargeContract.Model {
        return model
    }
    @ActivityScope
    @Provides
    fun provideRechargeOptionAdapter() = RechargeOptionAdapter()
}
