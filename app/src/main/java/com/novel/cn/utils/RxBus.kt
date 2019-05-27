package com.novel.cn.utils

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * RxJava实现EventBus，但不建议大量地方订阅同一个RxBus，否则性能不好
 * Created by hy on 2018/10/23
 */
open class RxBus {

    private val mBus: Subject<EventWrap> by lazy { PublishSubject.create<EventWrap>().toSerialized() }

    /**
     * 发送事件
     */
    fun post(event: Any, tag: String? = null) {
        mBus.onNext(EventWrap(tag, event))
    }

    /**
     * 发送事件，订阅event类型必须是Unit
     */
    fun post(tag: String) {
        mBus.onNext(EventWrap(tag, Unit))
    }

    /**
     * 返回特定类型的被观察者
     */
    inline fun <reified T> toObservable(tag: String? = null): Observable<T> {
        return toObservable(T::class.java, tag)
    }

    /**
     * 返回Unit类型的被观察者、即只用tag post事件
     * @see post(tag: String)
     */
    fun toObservableUnit(tag: String): Observable<Unit> {
        return mBus
                .filter { it.tag == tag }
                .map { it.event }
                .ofType(Unit::class.java)
    }

    /**
     * 返回特定类型的被观察者
     */
    @Suppress("UNCHECKED_CAST")
    fun <T> toObservable(clazz: Class<T>, tag: String? = null): Observable<T> {
        return mBus
                .filter { it.tag == tag }
                .map { it.event }
                .ofType(clazz)
    }

    /**
     * 返回list类型被观察者，因泛型擦除，所以这里使用集合手动判断类型
     */
    @Suppress("USELESS_IS_CHECK")
    inline fun <reified T> toObservableList(tag: String? = null): Observable<List<T>> {
        return toObservable<List<T>>(tag)
                .filter { it.isNotEmpty() && it.first() is T }
    }

    /**
     * 判断是否有订阅者
     */
    fun hasObservers(): Boolean {
        return mBus.hasObservers()
    }

    private data class EventWrap(
            var tag: String?,
            var event: Any
    )
}