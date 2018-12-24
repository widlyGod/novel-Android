package com.novel.cn.view.wight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.novel.cn.R;
import com.novel.cn.util.LogUtil;

/**自定义清除的edittext  TextInputEditText
 * Created by jackieli on 2018/12/21.
 */

public class ClearEditText extends TextInputEditText implements View.OnFocusChangeListener, TextWatcher {


    public ClearEditText(Context context) {
        super(context);
        LogUtil.e("执行1参数");
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        LogUtil.e("执行2参数");

        TypedArray array = context.getTheme()
                .obtainStyledAttributes(new int[] {android.R.attr.colorAccent});
        colorAccent = array.getColor(0, 0xFF00FF);
        array.recycle();
        initClearDrawable(context);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LogUtil.e("执行3参数");
    }


    private Drawable mClearDrawable;// 一键删除的按钮
    private int colorAccent;//获得主题的颜色



    @SuppressLint("NewApi")
    private void initClearDrawable(Context context) {
        mClearDrawable = getCompoundDrawables()[2];// 获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(R.drawable.login_delete, context.getTheme());
        }
        DrawableCompat.setTint(mClearDrawable, colorAccent);//设置删除按钮的颜色和TextColor的颜色一致
        mClearDrawable.setBounds(0, 0, (int) getTextSize(), (int) getTextSize());//设置Drawable的宽高和TextSize的大小一致
        setClearIconVisible(true);

        //监听是否显示删除图标
        // 设置焦点改变的监听
        setOnFocusChangeListener(this);
        // 设置输入框里面内容发生改变的监听
        addTextChangedListener(this);

    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    private void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
//        Drawable right = mClearDrawable;
//        setCompoundDrawables(right, getCompoundDrawables()[1], getCompoundDrawables()[2], getCompoundDrawables()[3]);
    }

    //点击事件一键删除
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mClearDrawable != null && event.getAction() == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            // 判断触摸点是否在水平范围内
            boolean isInnerWidth = (x > (getWidth() - getTotalPaddingRight()))
                    && (x < (getWidth() - getPaddingRight()));
            // 获取删除图标的边界，返回一个Rect对象
            Rect rect = mClearDrawable.getBounds();
            // 获取删除图标的高度
            int height = rect.height();
            int y = (int) event.getY();
            // 计算图标底部到控件底部的距离
            int distance = (getHeight() - height) / 2;
            // 判断触摸点是否在竖直范围内(可能会有点误差)
            // 触摸点的纵坐标在distance到（distance+图标自身的高度）之内，则视为点中删除图标
            boolean isInnerHeight = (y > distance) && (y < (distance + height));
            if (isInnerHeight && isInnerWidth) {
                this.setText("");
                Toast.makeText(getContext(), "一键清除", Toast.LENGTH_SHORT).show();//为了看清效果，测试
            }
        }
        return super.onTouchEvent(event);

    }

    private boolean hasFocus;// 控件是否有焦点


    //监听看是否显示删除图标
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }
    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (hasFocus) {
            setClearIconVisible(text.length() > 0);
        }
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

}
