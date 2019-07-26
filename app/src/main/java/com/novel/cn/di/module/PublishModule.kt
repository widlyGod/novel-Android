package com.novel.cn.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.PublishContract
import com.novel.cn.mvp.model.PublishModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/18/2019 15:58
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建PublishModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class PublishModule(private val view: PublishContract.View) {
    @ActivityScope
    @Provides
    fun providePublishView(): PublishContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun providePublishModel(model: PublishModel): PublishContract.Model {
        return model
    }
}
