package com.novel.cn.newui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.novel.cn.R;
import com.novel.cn.app.NovelApplication;
import com.novel.cn.interfaceFolder.FragmentListener;
import com.novel.cn.newui.bookcity.fragment.FragmentBookCity;
import com.novel.cn.newui.bookshelf.fragment.FragmentBookShelf;
import com.novel.cn.newui.find.fragment.FragmentFind;
import com.novel.cn.newui.mine.fragment.FragmentNewMy;
import com.novel.cn.view.wight.Dialog_Loading;
import com.zhy.autolayout.AutoLayoutActivity;
import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jackieli on 2019/3/4.
 */

public class MainActivity extends AutoLayoutActivity implements FragmentListener {

    @Bind(R.id.fragment_main)
    FrameLayout fragmentMain;
    @Bind(R.id.rb_bookshelf)
    RadioButton rb_bookshelf;
    @Bind(R.id.rb_bookcity)
    RadioButton rb_bookcity;
    @Bind(R.id.rb_find)
    RadioButton rb_find;
    @Bind(R.id.rb_mine)
    RadioButton rb_mine;
    @Bind(R.id.home_rg)
    RadioGroup homeRg;
    private ArrayList<Fragment> framgnets;
    private int lastIndex = 0;

    public Dialog_Loading dialog_loading;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmain);
        ButterKnife.bind(this);

        NovelApplication.setMainActivity(this);
        initViews();
        checkRequiredPermission();

        dialog_loading = new Dialog_Loading(this, R.style.Dialog);
        dialog_loading.setCancelable(false);
    }


    private void initViews() {

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        framgnets = new ArrayList<Fragment>();
        FragmentBookShelf bookShelf = new FragmentBookShelf();
        framgnets.add(bookShelf);
        framgnets.add(new FragmentBookCity());
        FragmentFind fragmentFind = new FragmentFind();
        framgnets.add(fragmentFind);
        framgnets.add(new FragmentNewMy());

        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        for (int i = 0; i < framgnets.size(); i++) {
            Fragment fragment = framgnets.get(i);
            transaction.add(R.id.fragment_main, fragment);
            transaction.hide(fragment);
        }
        transaction.show(framgnets.get(0));
        transaction.commit();

        homeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int index = 0;
//                Utils.hintKeyBoard(MainActivity.this);
                switch (checkedId) {
                    case R.id.rb_bookshelf:
                        index = 0;
                        break;
                    case R.id.rb_bookcity:
                        index = 1;
                        break;
                    case R.id.rb_find:
                        index = 2;
                        break;
                    case R.id.rb_mine:
                        index = 3;
                        break;
                }
                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                transaction1.hide(framgnets.get(lastIndex));
                transaction1.show(framgnets.get(index));
                transaction1.commit();
                lastIndex = index;
            }
        });
    }

    //检查权限
    private void checkRequiredPermission() {

    }

    @Override
    public void onBackFragment() {

    }

    @Override
    public void onClickFragment(Object o) {

    }


}
