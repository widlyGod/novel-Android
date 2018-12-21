package com.novel.cn.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.novel.cn.R;
import com.novel.cn.view.wight.ClearEditText;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录界面
 * Created by jackieli on 2018/12/20.
 */

public class LoginActivity extends AutoLayoutActivity {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.toolbar)
    TextView toolbar;
    @Bind(R.id.appbar)
    RelativeLayout appbar;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.et_username)
    TextInputEditText etUsername;
    @Bind(R.id.tl_username)
    TextInputLayout tlUsername;
    @Bind(R.id.et_ps)
    ClearEditText etPs;

    //    TextInputEditText etPs;
    @Bind(R.id.tl_pw)
    TextInputLayout tlPw;
    @Bind(R.id.ll_zhdl)
    LinearLayout llZhdl;
    @Bind(R.id.ll_yxzc)
    LinearLayout llYxzc;
    @Bind(R.id.ll_dsf)
    LinearLayout llDsf;
    @Bind(R.id.iv_qq)
    ImageButton ivQq;
    @Bind(R.id.iv_wx)
    ImageButton ivWx;
    @Bind(R.id.iv_wb)
    ImageButton ivWb;
    @Bind(R.id.login_ll)
    LinearLayout loginLl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Log.e("tag", "程序运行");

        tablayout.addTab(tablayout.newTab().setText("账号登录"));
        tablayout.addTab(tablayout.newTab().setText("邮箱注册"));
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0: {
                        llZhdl.setVisibility(View.VISIBLE);
                        llYxzc.setVisibility(View.GONE);
                    }break;
                    case 1: {
                        llYxzc.setVisibility(View.VISIBLE);
                        llZhdl.setVisibility(View.GONE);
                    }break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });




    }


    @OnClick({R.id.iv_left, R.id.iv_qq, R.id.iv_wx, R.id.iv_wb})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:{

            }break;
            case R.id.iv_qq:{

            }break;
            case R.id.iv_wx:{

            }break;
            case R.id.iv_wb:{

            }break;
        }
    }



}
