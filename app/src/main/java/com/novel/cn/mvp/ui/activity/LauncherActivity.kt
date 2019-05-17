package com.novel.cn.mvp.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.novel.cn.app.Constant
import com.novel.cn.app.Preference

class LauncherActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }

        val token = Preference.getString(Constant.SESSION_ID)
        val intent = Intent();
        if (token.isNullOrEmpty()) {
            intent.setClass(this, LoginActivity::class.java)
        } else {
            intent.setClass(this@LauncherActivity, MainActivity::class.java)
        }
        startActivity(intent)
        finish()
    }

}