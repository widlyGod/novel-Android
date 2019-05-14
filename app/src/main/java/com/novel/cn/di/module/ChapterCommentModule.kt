package com.novel.cn.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.ChapterCommentContract
import com.novel.cn.mvp.model.ChapterCommentModel


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/13/2019 10:00
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建ChapterCommentModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ChapterCommentModule(private val view: ChapterCommentContract.View) {
    @ActivityScope
    @Provides
    fun provideChapterCommentView(): ChapterCommentContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideChapterCommentModel(model: ChapterCommentModel): ChapterCommentContract.Model {
        return model
    }
}
