package com.novel.cn.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.CommentContract
import com.novel.cn.mvp.model.CommentModel
import com.novel.cn.mvp.ui.adapter.BookCommentAdapter


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 04/09/2019 09:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建CommentModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class CommentModule(private val view: CommentContract.View) {
    @ActivityScope
    @Provides
    fun provideCommentView(): CommentContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideCommentModel(model: CommentModel): CommentContract.Model {
        return model
    }

    @ActivityScope
    @Provides
    fun provideCommentAdapter() = BookCommentAdapter()
}
