package com.novel.cn.di.module

import com.flyco.tablayout.listener.CustomTabEntity
import com.jess.arms.di.scope.FragmentScope
import com.novel.cn.mvp.contract.DiscoveryContract
import com.novel.cn.mvp.model.DiscoveryModel
import com.novel.cn.mvp.model.entity.TabEntity
import com.novel.cn.mvp.ui.dialog.CircleMorePopup
import dagger.Module
import dagger.Provides
import javax.inject.Named


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/18/2019 11:03
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建DiscoveryModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class DiscoveryModule(private val view: DiscoveryContract.View) {
    @FragmentScope
    @Provides
    fun provideDiscoveryView(): DiscoveryContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideDiscoveryModel(model: DiscoveryModel): DiscoveryContract.Model {
        return model
    }

    @FragmentScope
    @Provides
    fun provideMorePopup() = CircleMorePopup(view.getContext())

    @FragmentScope
    @Provides
    @Named("TabEntity")
    fun provideTabEnty(): ArrayList<CustomTabEntity> {
        return arrayListOf(TabEntity("聊天"), TabEntity("圈子"))
    }
}
