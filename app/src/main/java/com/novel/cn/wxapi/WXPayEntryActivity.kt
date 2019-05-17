package com.novel.cn.wxapi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.jess.arms.utils.LogUtils
import org.greenrobot.eventbus.EventBus

class WXPayEntryActivity : Activity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        onIntent(getIntent())
    }

    internal fun onIntent(intent: Intent?) {
        LogUtils.warnInfo("=====>" + if (intent == null || intent.extras == null) "" else intent.extras!!.toString())
        EventBus.getDefault().post(WXPayEvent(intent!!))
        finish()
    }

    companion object {

        private val TAG = "WXPayEntryActivity"

    }


}