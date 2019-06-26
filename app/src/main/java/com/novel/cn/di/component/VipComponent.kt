package com.novel.cn.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.novel.cn.di.module.VipModule

import com.jess.arms.di.scope.ActivityScope
import com.novel.cn.mvp.ui.activity.VipActivity


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
@ActivityScope
@Component(modules = arrayOf(VipModule::class), dependencies = arrayOf(AppComponent::class))
interface VipComponent {
    fun inject(activity: VipActivity)
}
