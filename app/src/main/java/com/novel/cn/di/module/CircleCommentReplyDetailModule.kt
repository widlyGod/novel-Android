package com.novel.cn.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.CircleCommentReplyDetailContract
import com.novel.cn.mvp.model.CircleCommentReplyDetailModel
import com.novel.cn.mvp.ui.adapter.CircleCommentReplyAdapter


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/01/2019 14:21
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建CircleCommentReplyDetailModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class CircleCommentReplyDetailModule(private val view: CircleCommentReplyDetailContract.View) {
    @ActivityScope
    @Provides
    fun provideCircleCommentReplyDetailView(): CircleCommentReplyDetailContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideCircleCommentReplyDetailModel(model: CircleCommentReplyDetailModel): CircleCommentReplyDetailContract.Model {
        return model
    }

    @ActivityScope
    @Provides
    fun provideCircleCommentReplyAdapter(): CircleCommentReplyAdapter {
        return CircleCommentReplyAdapter()
    }
}
