package com.novel.cn.di.module

import com.jess.arms.di.scope.ActivityScope
import com.novel.cn.mvp.contract.CategoryListContract
import com.novel.cn.mvp.model.CategoryListModel
import com.novel.cn.mvp.ui.adapter.CategoryBookAdapter
import dagger.Module
import dagger.Provides


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 05/23/2019 17:25
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建CategoryListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class CategoryListModule(private val view: CategoryListContract.View) {
    @ActivityScope
    @Provides
    fun provideCategoryListView(): CategoryListContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideCategoryListModel(model: CategoryListModel): CategoryListContract.Model {
        return model
    }

    @ActivityScope
    @Provides
    fun provideCategoryBookAdapter() = CategoryBookAdapter()
}
