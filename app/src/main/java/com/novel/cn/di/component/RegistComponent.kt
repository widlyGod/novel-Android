package com.novel.cn.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.novel.cn.di.module.RegistModule

import com.jess.arms.di.scope.ActivityScope
import com.novel.cn.di.module.LoginModule
import com.novel.cn.mvp.ui.activity.RegistActivity


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/19/2019 18:11
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
@Component(modules = [RegistModule::class, LoginModule::class], dependencies = arrayOf(AppComponent::class))
interface RegistComponent {
    fun inject(activity: RegistActivity)
}
