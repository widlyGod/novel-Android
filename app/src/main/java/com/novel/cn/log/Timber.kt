package com.novel.cn.log

import android.util.Log

object Timber {
    private val forestList = mutableListOf<Tree>()
    private var forestArray: Array<Tree> = emptyArray()

    val trees get() = forestArray.toList()
    val size get() = forestArray.size
    val simple = DebugTree()

    fun uprootAll() {
        synchronized(forestList) {
            forestList.clear()
            forestArray = emptyArray()
        }
    }

    fun uproot(tree: Tree) {
        synchronized(forestList) {
            require(forestList.remove(tree)) { "Cannot uproot tree which is not planted: $tree" }
            forestArray = forestList.toTypedArray()
        }
    }

    fun plant(tree: Tree) {
        synchronized(forestList) {
            forestList.add(tree)
            forestArray = forestList.toTypedArray()
        }
    }

    fun plant(vararg trees: Tree) {
        synchronized(forestList) {
            forestList.addAll(trees)
            forestArray = forestList.toTypedArray()
        }
    }

    fun plantAll(trees: Iterable<Tree>) {
        synchronized(forestList) {
            forestList.addAll(trees)
            forestArray = forestList.toTypedArray()
        }
    }

    fun isLoggable(priority: Int, tag: String? = null) = forestArray.any { it.isLoggable(priority, tag) }

    fun log(priority: Int, tag: String?, throwable: Throwable?, message: String?) {
        forestArray.forEach { it.log(priority, tag, throwable, message) }
    }

    /** Invoked only when [isLoggable] has returned true. */
    @PublishedApi
    internal fun rawLog(priority: Int, tag: String?, throwable: Throwable?, message: String?) {
        forestArray.forEach { it.rawLog(priority, tag, throwable, message) }
    }

    fun tagged(tag: String): Tree {
        val taggedTag = tag
        return object : Tree() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return Timber.isLoggable(priority, tag ?: taggedTag)
            }

            override fun performLog(priority: Int, tag: String?, throwable: Throwable?, message: String?) {
                Timber.log(priority, tag ?: taggedTag, throwable, message)
            }
        }
    }

    const val VERBOSE = Log.VERBOSE
    const val DEBUG = Log.DEBUG
    const val INFO = Log.INFO
    const val WARNING = Log.WARN
    const val ERROR = Log.ERROR
    const val ASSERT = Log.ASSERT
}

inline fun Timber.log(priority: Int, throwable: Throwable? = null, message: () -> String?) {
    if (isLoggable(priority, null)) {
        rawLog(priority, null, throwable, message())
    }
}

inline fun Timber.wtf(throwable: Throwable? = null, message: () -> String?) {
    log(ASSERT, throwable, message)
}

inline fun Timber.error(throwable: Throwable? = null, message: () -> String?) {
    log(ERROR, throwable, message)
}

inline fun Timber.warn(throwable: Throwable? = null, message: () -> String?) {
    log(WARNING, throwable, message)
}

inline fun Timber.info(throwable: Throwable? = null, message: () -> String?) {
    log(INFO, throwable, message)
}

inline fun Timber.debug(throwable: Throwable? = null, message: () -> String?) {
    log(DEBUG, throwable, message)
}

inline fun Timber.verbose(throwable: Throwable? = null, message: () -> String?) {
    log(VERBOSE, throwable, message)
}