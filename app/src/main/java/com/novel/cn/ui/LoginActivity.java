package com.novel.cn.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.novel.cn.R;
import com.zhy.autolayout.AutoLayoutActivity;

/**登录界面
 * Created by jackieli on 2018/12/20.
 */

public class LoginActivity extends AutoLayoutActivity{




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.e("tag","程序运行");


    }



}
