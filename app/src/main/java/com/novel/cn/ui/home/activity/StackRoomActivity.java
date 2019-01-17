package com.novel.cn.ui.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.novel.cn.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jackieli on 2019/1/16.
 */

public class StackRoomActivity extends AutoLayoutActivity {


    @Bind(R.id.iv_left)
    ImageView ivLeft;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stackroom);
        ButterKnife.bind(this);

    }



    @OnClick(R.id.iv_left)
    public void onViewClicked() {
        finish();
    }


}
