package com.novel.cn.view.wight;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.novel.cn.R;

/**
 * Created by jackieli on 2019/1/22.
 */

public class PopWindowReward extends PopupWindow {

    private Context context;

    public PopWindowReward(Context context) {
        super(context);
        inint(context);
    }

    public PopWindowReward(Context context, AttributeSet attrs) {
        super(context, attrs);
        inint(context);
    }


    public TabLayout tablayout;

    private void inint(Context context) {
        this.context=context;
        View view = LayoutInflater.from(context).inflate(R.layout.pop_dasan, null);
        setContentView(view);

        setTouchable(true);
        setFocusable(true);  //原来true
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        ImageView iv_close=view.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        tablayout = (TabLayout) view.findViewById(R.id.tablayout);
        tablayout.addTab(tablayout.newTab().setText("推荐票"));
        tablayout.addTab(tablayout.newTab().setText("月票"));
        tablayout.addTab(tablayout.newTab().setText("砖石"));
        tablayout.addTab(tablayout.newTab().setText("打赏"));
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                type=tab.getPosition();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

}
