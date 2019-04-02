package com.novel.cn.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.novel.cn.di.module.BookStoreModule

import com.jess.arms.di.scope.FragmentScope
import com.novel.cn.mvp.ui.fragment.BookStoreFragment



@FragmentScope
@Component(modules = arrayOf(BookStoreModule::class), dependencies = arrayOf(AppComponent::class))
interface BookStoreComponent {
    fun inject(fragment: BookStoreFragment)
}
