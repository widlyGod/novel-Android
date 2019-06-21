package com.novel.cn.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.MineBillContract
import com.novel.cn.mvp.model.MineBillModel
import com.novel.cn.mvp.ui.adapter.MessageFilterAdapter
import com.novel.cn.mvp.ui.adapter.MyBillAdapter


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/19/2019 11:30
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建MineBillModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class MineBillModule(private val view: MineBillContract.View) {
    @ActivityScope
    @Provides
    fun provideMineBillView(): MineBillContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideMineBillModel(model: MineBillModel): MineBillContract.Model {
        return model
    }

    @ActivityScope
    @Provides
    fun provideMyBillAdapter() = MyBillAdapter()
}
