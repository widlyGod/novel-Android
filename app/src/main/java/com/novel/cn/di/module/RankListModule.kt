package com.novel.cn.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.RankListContract
import com.novel.cn.mvp.model.RankListModel
import com.novel.cn.mvp.ui.adapter.RankListAdapter


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/08/2019 14:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建RankListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class RankListModule(private val view: RankListContract.View) {
    @ActivityScope
    @Provides
    fun provideRankListView(): RankListContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideRankListModel(model: RankListModel): RankListContract.Model {
        return model
    }

    @ActivityScope
    @Provides
    fun provideRankListAdapter() = RankListAdapter()
}
