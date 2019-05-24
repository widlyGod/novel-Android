package com.xmssx.common.ext

import com.jess.arms.base.IRxLifecycleProvider
import com.jess.arms.mvp.IView
import com.xmssx.common.log.Timber
import com.xmssx.common.log.error
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * 绑定到生命周期
 */
fun Disposable.bindToLifecycle(lifecycleProvider: IRxLifecycleProvider): Disposable {
    lifecycleProvider.bindToLifecycle(this)
    return this
}


fun <T> Observable<T>.subscribeOnMain(): Observable<T> {
    return subscribeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.subscribeOnIO(): Observable<T> {
    return subscribeOn(Schedulers.io())
}

fun <T> Observable<T>.observeOnMain(): Observable<T> {
    return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.observeOnIO(): Observable<T> {
    return observeOn(Schedulers.io())
}


fun <T> Flowable<T>.subscribeOnMain(): Flowable<T> {
    return subscribeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.subscribeOnIO(): Flowable<T> {
    return subscribeOn(Schedulers.io())
}

fun <T> Flowable<T>.observeOnMain(): Flowable<T> {
    return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.observeOnIO(): Flowable<T> {
    return observeOn(Schedulers.io())
}


fun <T> Single<T>.subscribeOnMain(): Single<T> {
    return subscribeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.subscribeOnIO(): Single<T> {
    return subscribeOn(Schedulers.io())
}

fun <T> Single<T>.observeOnMain(): Single<T> {
    return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.observeOnIO(): Single<T> {
    return observeOn(Schedulers.io())
}


fun <T> Maybe<T>.subscribeOnMain(): Maybe<T> {
    return subscribeOn(AndroidSchedulers.mainThread())
}

fun <T> Maybe<T>.subscribeOnIO(): Maybe<T> {
    return subscribeOn(Schedulers.io())
}

fun <T> Maybe<T>.observeOnMain(): Maybe<T> {
    return observeOn(AndroidSchedulers.mainThread())
}

fun <T> Maybe<T>.observeOnIO(): Maybe<T> {
    return observeOn(Schedulers.io())
}


fun Completable.subscribeOnMain(): Completable {
    return subscribeOn(AndroidSchedulers.mainThread())
}

fun Completable.subscribeOnIO(): Completable {
    return subscribeOn(Schedulers.io())
}

fun Completable.observeOnMain(): Completable {
    return observeOn(AndroidSchedulers.mainThread())
}

fun Completable.observeOnIO(): Completable {
    return observeOn(Schedulers.io())
}


fun <T> Observable<T>.applySchedulers(view: IView?, loadingMessage: String? = null, autoDispose: Boolean = true): Observable<T> {
    var disposable: Disposable? = null
    return subscribeOn(Schedulers.io()) // 控制订阅线程
            .doOnSubscribe {
                disposable = it
                view?.showLoading() // 显示进度条
            }
            .subscribeOn(AndroidSchedulers.mainThread()) // 控制doOnSubscribe线程
            .observeOn(AndroidSchedulers.mainThread()) // 控制doFinally线程
            .doFinally {
                view?.hideLoading() // 隐藏进度条
                if (autoDispose)
                    disposable?.let {
                        view?.removeFromLifecycle(it)
                    }
            }
            .observeOn(AndroidSchedulers.mainThread()) // 控制后面观察线程
}

fun <T> Flowable<T>.applySchedulers(view: IView?, loadingMessage: String? = null): Flowable<T> {
    return subscribeOn(Schedulers.io()) // 控制订阅线程
            .doOnSubscribe {
                view?.showLoading() // 显示进度条
            }
            .subscribeOn(AndroidSchedulers.mainThread()) // 控制doOnSubscribe线程
            .observeOn(AndroidSchedulers.mainThread()) // 控制doFinally线程
            .doFinally {
                view?.hideLoading() // 隐藏进度条
            }
            .observeOn(AndroidSchedulers.mainThread()) // 控制后面观察线程
}

fun <T> Single<T>.applySchedulers(view: IView?, loadingMessage: String? = null, autoDispose: Boolean = true): Single<T> {
    var disposable: Disposable? = null
    return subscribeOn(Schedulers.io()) // 控制订阅线程
            .doOnSubscribe {
                disposable = it
                view?.showLoading() // 显示进度条
            }
            .subscribeOn(AndroidSchedulers.mainThread()) // 控制doOnSubscribe线程
            .observeOn(AndroidSchedulers.mainThread()) // 控制doFinally线程
            .doFinally {
                view?.hideLoading() // 隐藏进度条
                if (autoDispose)
                    disposable?.let {
                        view?.removeFromLifecycle(it)
                    }
            }
            .observeOn(AndroidSchedulers.mainThread()) // 控制后面观察线程
}

fun <T> Maybe<T>.applySchedulers(view: IView?, loadingMessage: String? = null, autoDispose: Boolean = true): Maybe<T> {
    var disposable: Disposable? = null
    return subscribeOn(Schedulers.io()) // 控制订阅线程
            .doOnSubscribe {
                disposable = it
                view?.showLoading() // 显示进度条
            }
            .subscribeOn(AndroidSchedulers.mainThread()) // 控制doOnSubscribe线程
            .observeOn(AndroidSchedulers.mainThread()) // 控制doFinally线程
            .doFinally {
                view?.hideLoading() // 隐藏进度条
                if (autoDispose)
                    disposable?.let {
                        view?.removeFromLifecycle(it)
                    }
            }
            .observeOn(AndroidSchedulers.mainThread()) // 控制后面观察线程
}

fun Completable.applySchedulers(view: IView?, loadingMessage: String? = null, autoDispose: Boolean = true): Completable {
    var disposable: Disposable? = null
    return subscribeOn(Schedulers.io()) // 控制订阅线程
            .doOnSubscribe {
                disposable = it
                view?.showLoading() // 显示进度条
            }
            .subscribeOn(AndroidSchedulers.mainThread()) // 控制doOnSubscribe线程
            .observeOn(AndroidSchedulers.mainThread()) // 控制doFinally线程
            .doFinally {
                view?.hideLoading() // 隐藏进度条
                if (autoDispose)
                    disposable?.let {
                        view?.removeFromLifecycle(it)
                    }
            }
            .observeOn(AndroidSchedulers.mainThread()) // 控制后面观察线程
}

fun <T> Single<T>.lessTime(time: Long, unit: TimeUnit): Single<T> {
    var startTime = 0L
    return doOnSubscribe { startTime = System.currentTimeMillis() }
            .doOnSuccess {
                val time2 = unit.toMillis(time) + startTime - System.currentTimeMillis()
                if (time2 > 0)
                    Thread.sleep(time2)
            }
}

fun <T> Observable<T>.lessTime(time: Long, unit: TimeUnit): Observable<T> {
    var startTime = 0L
    return doOnSubscribe { startTime = System.currentTimeMillis() }
            .doOnNext {
                val time2 = unit.toMillis(time) + startTime - System.currentTimeMillis()
                if (time2 > 0)
                    Thread.sleep(time2)
            }
}

/**
 * 解决RxJava异常后自动取消订阅，解决方案：
 * onError调用时，重新订阅
 */
fun <T> Observable<T>.subscribeIgnoreError(
        lifecycleProvider: IRxLifecycleProvider,
        onNext: (t: T) -> Unit,
        onError: (e: Throwable) -> Unit = {
            Timber.error(it) {
                it.message
            }
        },
        onComplete: () -> Unit = {},
        onSubscribe: (d: Disposable) -> Unit = {}): Disposable {

    var disposable: Disposable? = null
    disposable = subscribe(onNext, {
        onError(it)
        // 错误的时候继续订阅
        subscribeIgnoreError(lifecycleProvider, onNext, onError, onComplete, onSubscribe)
        // 移除无效的disposable
        if (disposable != null)
            lifecycleProvider.removeFromLifecycle(disposable!!)
    }, onComplete, onSubscribe).bindToLifecycle(lifecycleProvider)
    return disposable
}

/**
 * 解决RxJava异常后自动取消订阅，解决方案：
 * onError调用时，重新订阅
 */
fun <T> Flowable<T>.subscribeIgnoreError(
        lifecycleProvider: IRxLifecycleProvider,
        onNext: (t: T) -> Unit,
        onError: (e: Throwable) -> Unit = {
            Timber.error(it) {
                it.message
            }
        },
        onComplete: () -> Unit = {}): Disposable {

    var disposable: Disposable? = null
    disposable = subscribe(onNext, {
        onError(it)
        // 错误的时候继续订阅
        subscribeIgnoreError(lifecycleProvider, onNext, onError, onComplete)
        // 移除无效的disposable
        if (disposable != null)
            lifecycleProvider.removeFromLifecycle(disposable!!)
    }, onComplete).bindToLifecycle(lifecycleProvider)
    return disposable
}

@CheckReturnValue
fun <T> Single<T>.subscribeWithView(
        onNext: (t: T) -> Unit,
        view: IView?,
        defaultMsg: String,
        onError: (e: Throwable) -> Boolean = { false }
): Disposable {
    return subscribe(onNext, {
        val consume = onError(it)
        if (!consume)
            handleError(it, view, defaultMsg)
    })
}

@CheckReturnValue
fun <T> Single<T>.subscribeWithView(
        view: IView?,
        defaultMsg: String,
        onError: (e: Throwable) -> Boolean = { false },
        onNext: (t: T) -> Unit
): Disposable {
    return subscribe(onNext, {
        val consume = onError(it)
        if (!consume)
            handleError(it, view, defaultMsg)
    })
}

@CheckReturnValue
fun <T> Maybe<T>.subscribeWithView(
        view: IView?,
        defaultMsg: String,
        onError: (e: Throwable) -> Boolean = { false },
        onNext: (t: T) -> Unit
): Disposable {
    return subscribe(onNext, {
        val consume = onError(it)
        if (!consume)
            handleError(it, view, defaultMsg)
    })
}

@CheckReturnValue
fun <T> Observable<T>.subscribeWithView(
        onNext: (t: T) -> Unit,
        view: IView?,
        defaultMsg: String,
        onError: (e: Throwable) -> Boolean = { false },
        onComplete: () -> Unit = {}
): Disposable {
    return subscribe(onNext, {
        val consume = onError(it)
        if (!consume)
            handleError(it, view, defaultMsg)
    }, onComplete)
}

@CheckReturnValue
fun <T> Observable<T>.subscribeWithView(
        view: IView?,
        defaultMsg: String,
        onError: (e: Throwable) -> Boolean = { false },
        onNext: (t: T) -> Unit
): Disposable {
    return subscribe(onNext, {
        val consume = onError(it)
        if (!consume)
            handleError(it, view, defaultMsg)
    })
}

@CheckReturnValue
fun <T> Flowable<T>.subscribeWithView(
        onNext: (t: T) -> Unit,
        view: IView?,
        defaultMsg: String,
        onError: (e: Throwable) -> Boolean = { false },
        onComplete: () -> Unit = {}
): Disposable {
    return subscribe(onNext, {
        val consume = onError(it)
        if (!consume)
            handleError(it, view, defaultMsg)
    }, onComplete)
}