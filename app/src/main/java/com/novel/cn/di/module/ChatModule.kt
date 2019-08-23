package com.novel.cn.di.module

import com.jess.arms.di.scope.FragmentScope

import dagger.Module
import dagger.Provides

import com.novel.cn.mvp.contract.ChatContract
import com.novel.cn.mvp.model.ChatModel
import com.novel.cn.mvp.ui.adapter.ChatMessageAdapter


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/20/2019 10:54
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@Module
//构建ChatModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class ChatModule(private val view: ChatContract.View) {
    @FragmentScope
    @Provides
    fun provideChatView(): ChatContract.View {
        return this.view
    }

    @FragmentScope
    @Provides
    fun provideChatModel(model: ChatModel): ChatContract.Model {
        return model
    }

    @FragmentScope
    @Provides
    fun provideChatMessageAdapter(): ChatMessageAdapter {
        return ChatMessageAdapter()
    }
}
