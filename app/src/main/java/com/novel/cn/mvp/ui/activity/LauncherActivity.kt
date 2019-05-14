package com.novel.cn.mvp.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.jess.arms.mvp.IPresenter
import com.novel.cn.app.Constant
import com.novel.cn.app.Preference

class LauncherActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (intent.flags and Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT != 0) {
            finish()
            return
        }
        val token = Preference.getString(Constant.SESSION_ID)
        val intent = Intent();
       /* if (token.isNullOrEmpty()) {
            intent.setClass(this,LoginActivity::class.java)
        }else{

        }*/
        intent.setClass(this,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}