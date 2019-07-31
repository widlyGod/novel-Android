package com.novel.cn.di.module

import com.jess.arms.di.scope.ActivityScope
import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.CircleCommentContract
import com.novel.cn.mvp.model.CircleCommentModel
import com.novel.cn.mvp.ui.adapter.CircleAdapter
import com.novel.cn.mvp.ui.adapter.CircleCommentAdapter


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 07/29/2019 11:37
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建CircleCommentModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class CircleCommentModule(private val view: CircleCommentContract.View) {
    @ActivityScope
    @Provides
    fun provideCircleCommentView(): CircleCommentContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideCircleCommentModel(model: CircleCommentModel): CircleCommentContract.Model {
        return model
    }

    @ActivityScope
    @Provides
    fun provideCircleCommentAdapter(): CircleCommentAdapter {
        return CircleCommentAdapter()
    }
}
