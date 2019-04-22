package com.novel.cn.view.read;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.novel.cn.view.read.animation.ScrollAnimation;
import com.novel.cn.view.read.animation.BaseAnimation;

public class ReadView extends View {

    //唤醒菜单的区域
    private RectF mCenterRect;

    private boolean isMove;

    //记录按下的坐标
    private float mStartX;
    private float mStartY;
    //view的宽高
    private float mWidth;
    private float mHeight;

    private BaseAnimation mAnimation;

    private Context mContext;

    private OnTouchListener mOnTouchListener;

    private PageLoader mPageLoader;


    public ReadView(Context context) {
        this(context, null);
    }

    public ReadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mContext = context;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        mWidth = displayMetrics.widthPixels;
        mHeight = displayMetrics.heightPixels;

        mAnimation = new ScrollAnimation(this, (int) mWidth, (int) mHeight);
        mPageLoader = new NetPageLoader(this, (int) mWidth, (int) mHeight);
    }

    public PageLoader getPageLoader() {
        return mPageLoader;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isMove = false;
                mStartX = x;
                mStartY = y;
                mAnimation.onTouchEvent(event);
                break;

            case MotionEvent.ACTION_MOVE:
                //最小滑动值
                int slop = ViewConfiguration.get(mContext).getScaledTouchSlop();

                if (!isMove) {
                    isMove = Math.abs(mStartX - event.getX()) > slop || Math.abs(mStartY - event.getY()) > slop;
                }

                if (isMove) {
                    mAnimation.onTouchEvent(event);
                }

                break;
            case MotionEvent.ACTION_UP:
                if (!isMove) {
                    if (mCenterRect == null) {
                        mCenterRect = new RectF(mWidth / 5f, mHeight / 5f, mWidth / 5f * 4, mHeight / 5f * 4);
                    }

                    if (mCenterRect.contains(x, y)) {
                        if (mOnTouchListener != null) mOnTouchListener.onMenuClick();
                    } else {
                        if (mOnTouchListener != null) mOnTouchListener.onClick();
                    }

                }
                mAnimation.onTouchEvent(event);
                break;
        }

        return true;
    }


    public void setOnTouchListener(OnTouchListener listener) {
        this.mOnTouchListener = listener;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mAnimation.draw(canvas);
    }

    @Override
    public void computeScroll() {
        mAnimation.scrollAnimation();
        super.computeScroll();
    }


    public Bitmap getBgBitmap() {
        return mAnimation.getBgBitmap();
    }

    public Bitmap getBitmap() {
        return mAnimation.getBitmap();
    }

    public interface OnTouchListener {
        void onMenuClick();

        void onClick();
    }
}
