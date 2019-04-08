package com.novel.cn.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.novel.cn.di.module.RankListModule

import com.jess.arms.di.scope.ActivityScope
import com.novel.cn.mvp.ui.activity.RankListActivity


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
@ActivityScope
@Component(modules = arrayOf(RankListModule::class), dependencies = arrayOf(AppComponent::class))
interface RankListComponent {
    fun inject(activity: RankListActivity)
}
