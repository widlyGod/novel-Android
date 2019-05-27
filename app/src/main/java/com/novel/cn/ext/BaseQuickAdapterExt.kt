package com.novel.cn.ext

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by hy on 2018/10/26
 */

fun <T, K : BaseViewHolder> BaseQuickAdapter<T, K>.clicks(): Observable<Pair<View, Int>> {
    val subject = PublishSubject.create<Pair<View, Int>>()
    setOnItemClickListener { _, view, position ->
        subject.onNext(Pair(view, position))
    }
    return subject
}

fun <T, K : BaseViewHolder> BaseQuickAdapter<T, K>.childClicks(): Observable<Pair<View, Int>> {
    val subject = PublishSubject.create<Pair<View, Int>>()
    setOnItemChildClickListener { _, view, position ->
        subject.onNext(Pair(view, position))
    }
    return subject
}

fun <T, K : BaseViewHolder> BaseQuickAdapter<T, K>.setPaddingTop(context: Context, dimenRes: Int) {
    addHeaderView(View(context).apply {
        layoutParams = LinearLayout.LayoutParams(0, resources.getDimensionPixelOffset(dimenRes))
    })
}