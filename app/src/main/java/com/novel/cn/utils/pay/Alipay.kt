package com.novel.cn.utils.pay

import android.app.Activity
import com.alipay.sdk.app.PayTask

object Alipay {
    interface OnResult {
        fun onResult(result: Result)
    }

    fun pay(context: Activity, order: String, onResult: OnResult) {
        object : Thread() {
            override fun run() {
                val task = PayTask(context)
                val r = task.pay(order, true)
                val result = Result(r)
                context.runOnUiThread { onResult.onResult(result) }
            }
        }.start()
    }
}