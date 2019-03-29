package com.novel.cn.di.module

import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.BookshelfContract
import com.novel.cn.mvp.model.BookshelfModel
import com.novel.cn.mvp.ui.adapter.BookshelfAdapter
import com.novel.cn.mvp.ui.dialog.MorePopup
import com.novel.cn.mvp.ui.dialog.SignInDialog


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/18/2019 10:59
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建BookshelfModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class BookshelfModule(private val view: BookshelfContract.View) {
    @FragmentScope
    @Provides
    fun provideBookshelfView(): BookshelfContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideBookshelfModel(model: BookshelfModel): BookshelfContract.Model {
        return model
    }

    @FragmentScope
    @Provides
    fun provideMorePopup() = MorePopup(view.getContext())

    @FragmentScope
    @Provides
    fun provideBookshelfAdapter() = BookshelfAdapter()


    @FragmentScope
    @Provides
    fun provideSignInDialog() = SignInDialog(view.getContext())

}
