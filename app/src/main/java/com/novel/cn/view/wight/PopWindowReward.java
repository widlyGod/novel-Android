package com.novel.cn.view.wight;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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

    private void inint(Context context) {
        this.context=context;
        View view = LayoutInflater.from(context).inflate(R.layout.pop_dasan, null);
        setContentView(view);

        setTouchable(true);
        setFocusable(true);  //原来true
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

}
