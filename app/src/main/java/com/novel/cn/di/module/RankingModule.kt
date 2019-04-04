package com.novel.cn.di.module

import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.RankingContract
import com.novel.cn.mvp.model.RankingModel
import com.novel.cn.mvp.ui.adapter.RankAdapter


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/26/2019 11:18
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建RankingModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class RankingModule(private val view: RankingContract.View) {
    @FragmentScope
    @Provides
    fun provideRankingView(): RankingContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideRankingModel(model: RankingModel): RankingContract.Model {
        return model
    }


    @FragmentScope
    @Provides
    fun provideRankAdapter() = RankAdapter()
}
