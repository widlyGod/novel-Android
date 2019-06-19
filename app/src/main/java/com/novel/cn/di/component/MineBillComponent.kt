package com.novel.cn.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.novel.cn.di.module.MineBillModule

import com.jess.arms.di.scope.ActivityScope
import com.novel.cn.mvp.ui.activity.MineBillActivity


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
@ActivityScope
@Component(modules = arrayOf(MineBillModule::class), dependencies = arrayOf(AppComponent::class))
interface MineBillComponent {
    fun inject(activity: MineBillActivity)
}
