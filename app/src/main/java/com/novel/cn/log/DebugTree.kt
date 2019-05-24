package com.xmssx.common.log

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import java.util.regex.Pattern

/** [Tree] for debug builds. Automatically infers the tag from the calling class. */
open class DebugTree(callStackIndex: Int = 4) : Tree() {

    private val MAX_LOG_LENGTH = 4000
    private val MAX_TAG_LENGTH = 23
    private val CALL_STACK_INDEX = callStackIndex
    private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")

    final override fun performLog(priority: Int, tag: String?, throwable: Throwable?, message: String?) {
        log(priority, tag ?: getTag(), message, throwable)
    }

    @SuppressLint("LogNotTimber")
    open fun log(priority: Int, tag: String, message: String?, throwable: Throwable?) {
        if (message == null || message.length < MAX_LOG_LENGTH) {
            if (priority == Log.ASSERT) {
                Log.wtf(tag, message)
            } else {
                Log.println(priority, tag, message)
            }
            return
        }

        // Split by line, then ensure each line can fit into Log's maximum length.
        var i = 0
        val length = message.length
        while (i < length) {
            var newline = message.indexOf('\n', i)
            newline = if (newline != -1) newline else length
            do {
                val end = Math.min(newline, i + MAX_LOG_LENGTH)
                val part = message.substring(i, end)
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, part)
                } else {
                    Log.println(priority, tag, part)
                }
                i = end
            } while (i < newline)
            i++
        }
    }

    protected open fun getTag(): String {
        // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
        // because Robolectric runs them on the JVM but on Android the elements are different.
        val stackTrace = Throwable().stackTrace
        if (stackTrace.size <= CALL_STACK_INDEX) {
            throw IllegalStateException(
                    "Synthetic stacktrace didn't have enough elements: are you using proguard?")
        }
        return createStackElementTag(stackTrace[CALL_STACK_INDEX])
    }

    /**
     * Extract the tag which should be used for the message from the `element`. By default
     * this will use the class name without any anonymous class suffixes (e.g., `Foo$1`
     * becomes `Foo`).
     *
     *
     * Note: This will not be called if a [manual tag][.tag] was specified.
     */
    protected open fun createStackElementTag(element: StackTraceElement): String {
        var tag = element.className
        val m = ANONYMOUS_CLASS.matcher(tag)
        if (m.find()) {
            tag = m.replaceAll("")
        }
        tag = tag.substring(tag.lastIndexOf('.') + 1)
        // Tag length limit was removed in API 24.
        return if (tag.length <= MAX_TAG_LENGTH || Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tag
        } else tag.substring(0, MAX_TAG_LENGTH)
    }
}