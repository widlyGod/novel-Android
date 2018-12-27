package com.novel.cn.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.novel.cn.R;
import com.novel.cn.interfaceFolder.FragmentListener;
import com.novel.cn.ui.book.fragment.FragmentBook;
import com.novel.cn.ui.home.fragment.FragmentHome;
import com.novel.cn.ui.my.fragment.FragmentMy;
import com.novel.cn.ui.recharge.fragment.FragmentRecharge;
import com.novel.cn.view.wight.Dialog_Loading;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *  支付与技术难点
 * Created by jackieli on 2018/12/19.
 */

public class MainActivity extends AutoLayoutActivity implements FragmentListener{


    @Bind(R.id.fragment_main)
    FrameLayout fragmentMain;
    @Bind(R.id.rb_home)
    RadioButton rbHome;
    @Bind(R.id.rb_book)
    RadioButton rbBook;
    @Bind(R.id.rb_recharge)
    RadioButton rbRecharge;
    @Bind(R.id.rb_my)
    RadioButton radiobuttonMy;
    @Bind(R.id.home_rg)
    RadioGroup homeRg;


    private ArrayList<Fragment> framgnets;
    private int lastIndex = 0;
    private FragmentHome fragmentHome;
    public Dialog_Loading dialog_loading;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViews();
        checkRequiredPermission();

        dialog_loading = new Dialog_Loading(this, R.style.Dialog);
        dialog_loading.setCancelable(false);
    }

    private void initViews() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        framgnets = new ArrayList<Fragment>();
        fragmentHome = new FragmentHome();
        framgnets.add(fragmentHome);
        framgnets.add(new FragmentBook());
        framgnets.add(new FragmentRecharge());
        framgnets.add(new FragmentMy());

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
                switch (checkedId) {
                    case R.id.rb_home:
                        index = 0;
                        break;
                    case R.id.rb_book:
                        index = 1;
                        break;
                    case R.id.rb_recharge:
                        index = 2;
                        break;
                    case R.id.rb_my:
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
    public void onBackPressed() {
//        if(radiobuttonFolder.isChecked() && NoteApplication.currentlFolderId !=0){
//            backListener.backFolder();
//        }else{
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
//        }
    }

    @Override
    public void onBackFragment() {

    }
}
