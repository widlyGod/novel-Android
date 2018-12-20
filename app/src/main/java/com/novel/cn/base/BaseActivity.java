
package com.novel.cn.base;

import android.os.Bundle;

import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;


/**
 * @author 咖枯
 * @version 1.0 2016/5/19
 */
public abstract class BaseActivity extends AutoLayoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);

        initViews();
    }

    protected abstract int getLayoutId();

    protected abstract void initViews();


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
