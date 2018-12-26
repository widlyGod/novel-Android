package com.novel.cn.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.novel.cn.R;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主界面 支付与技术难点
 * Created by jackieli on 2018/12/19.
 */

public class MainActivity extends AutoLayoutActivity {


    @Bind(R.id.fragment_main)
    FrameLayout fragmentMain;
    @Bind(R.id.rb_home)
    RadioButton rbHome;
    @Bind(R.id.rb_book)
    RadioButton rbBook;
    @Bind(R.id.rb_recharge)
    RadioButton rbRecharge;
    @Bind(R.id.radiobutton_my)
    RadioButton radiobuttonMy;
    @Bind(R.id.home_rg)
    RadioGroup homeRg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        checkRequiredPermission();
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

}
