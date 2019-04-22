package com.novel.cn.view.read.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

public abstract class BaseAnimation {


    protected View mView;
    //view 的大小
    protected int mWidth;
    protected int mHeight;
    //显示大小
    protected int mVisibleWidth;
    protected int mVisibleHeight;
    //屏幕的间距
    protected int mMarginWidth;
    protected int mMarginHeight;

    //滑动器
    protected Scroller mScroller;

    //起始点
    protected float mStartX;
    protected float mStartY;

    //触摸点
    protected float mTouchX;
    protected float mTouchY;

    //上一次的触摸点
    protected float mLastX;
    protected float mLastY;

    protected boolean isRunning;

    public BaseAnimation(View view, int width, int height) {
        this.mView = view;
        this.mWidth = width;
        this.mHeight = height;

        this.mVisibleWidth = mWidth;
        this.mVisibleHeight = mHeight;

        this.mScroller = new Scroller(view.getContext(), new LinearInterpolator());
    }


    protected void setStartPoint(float x, float y) {
        this.mStartX = x;
        this.mStartY = y;

        this.mLastX = x;
        this.mLastY = y;
    }

    protected void setTouchPoint(float x, float y) {
        this.mLastX = x;
        this.mLastY = y;

        this.mTouchX = x;
        this.mTouchY = y;
    }


    public abstract void draw(Canvas canvas);

    public abstract boolean onTouchEvent(MotionEvent event);

    public abstract void abortAnimation();

    public abstract void startAnimation();

    public abstract void scrollAnimation();

    public abstract Bitmap getBgBitmap();

    public abstract Bitmap getBitmap();
}
