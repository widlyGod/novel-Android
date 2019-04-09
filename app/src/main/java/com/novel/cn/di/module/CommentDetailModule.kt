package com.novel.cn.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.CommentDetailContract
import com.novel.cn.mvp.model.CommentDetailModel
import com.novel.cn.mvp.ui.adapter.BookReplyAdapter

@Module
class CommentDetailModule(private val view: CommentDetailContract.View) {
    @ActivityScope
    @Provides
    fun provideCommentDetailView(): CommentDetailContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideCommentDetailModel(model: CommentDetailModel): CommentDetailContract.Model {
        return model
    }
    @ActivityScope
    @Provides
    fun provideBookReplyAdapter() = BookReplyAdapter()
}
