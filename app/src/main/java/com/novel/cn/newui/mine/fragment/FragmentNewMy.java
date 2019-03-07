package com.novel.cn.newui.mine.fragment;

import android.content.Intent;
import android.widget.Button;

import com.novel.cn.R;
import com.novel.cn.base.BaseFragment;
import com.novel.cn.newui.mine.activity.LoginActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by jackieli on 2019/3/6.
 */

public class FragmentNewMy extends BaseFragment {


    @Bind(R.id.btn_login)
    Button btnLogin;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_newmy;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        Intent intent=new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }


}
