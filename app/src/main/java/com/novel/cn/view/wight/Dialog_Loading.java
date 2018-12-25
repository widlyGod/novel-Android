package com.novel.cn.view.wight;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import com.novel.cn.R;


/**
 * Created by wangtao on 2018/1/fprgetpsfour.
 */
//不定时加载框
public class Dialog_Loading extends Dialog {

    public Dialog_Loading(@NonNull Context context) {
        super(context);
    }

    private View progressBar;
    private AlphaAnimation alphaAnimation;

    public Dialog_Loading(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        setContentView(R.layout.dialog_loading);
        /**
         * @param fromAlpha 开始的透明度，取值是0.0f~1.0f，0.0f表示完全透明， 1.0f表示和原来一样
         * @param toAlpha 结束的透明度，同上
         */
        alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        //设置动画持续时长
        alphaAnimation.setDuration(800);
        alphaAnimation.setStartTime(0);
        //设置动画结束之后的状态是否是动画的最终状态，true，表示是保持动画结束时的最终状态
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                alphaAnimation.cancel();
                dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        initview();
    }

    private void initview() {
        progressBar = findViewById(R.id.progressBar);
    }

    public void delayDismiss(){
        progressBar.startAnimation(alphaAnimation);
//        dismiss();
    }


}
