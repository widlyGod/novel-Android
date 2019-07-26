package com.novel.cn.di.module

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.CircleContract
import com.novel.cn.mvp.model.CircleModel
import com.novel.cn.mvp.ui.adapter.BookCommentAdapter
import com.novel.cn.mvp.ui.adapter.CircleAdapter


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/19/2019 16:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建CircleModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class CircleModule(private val view: CircleContract.View) {
    @FragmentScope
    @Provides
    fun provideCircleView(): CircleContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideCircleModel(model: CircleModel): CircleContract.Model {
        return model
    }

    @FragmentScope
    @Provides
    fun provideCircleAdapter(): CircleAdapter {
        return CircleAdapter()
    }
}
