package com.novel.cn.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.ReadRecordContract
import com.novel.cn.mvp.model.ReadRecordModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/21/2019 10:13
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建ReadRecordModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ReadRecordModule(private val view: ReadRecordContract.View) {
    @ActivityScope
    @Provides
    fun provideReadRecordView(): ReadRecordContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideReadRecordModel(model: ReadRecordModel): ReadRecordContract.Model {
        return model
    }
}
