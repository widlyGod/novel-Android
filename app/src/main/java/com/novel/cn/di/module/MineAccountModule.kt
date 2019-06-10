package com.novel.cn.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.MineAccountContract
import com.novel.cn.mvp.model.MineAccountModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/06/2019 15:49
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建MineAccountModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class MineAccountModule(private val view: MineAccountContract.View) {
    @ActivityScope
    @Provides
    fun provideMineAccountView(): MineAccountContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideMineAccountModel(model: MineAccountModel): MineAccountContract.Model {
        return model
    }
}
