package com.novel.cn.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.jess.arms.utils.ArmsUtils;

public class ArcContainer extends RelativeLayout {

    private Paint mPaint;

    private int mWidth;

    private int mHeight;

    private int mArcHeight;

    public ArcContainer(Context context) {
        this(context, null);
    }

    public ArcContainer(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mArcHeight = ArmsUtils.dip2px(context, 15);


    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mHeight = getMeasuredHeight();
        mWidth = getMeasuredWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 使用离屏缓存，新建一个srcRectF区域大小的图层
//        canvas.saveLayer(new RectF(0, 0, mWidth, mHeight), null, Canvas.ALL_SAVE_FLAG);

        Path path = new Path();
        path.moveTo(0, mHeight - mArcHeight);
        path.quadTo(mWidth / 2, mHeight, mWidth, mHeight - mArcHeight);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawPath(path, mPaint);
        // 清除Xfermode
        mPaint.setXfermode(null);
        // 恢复画布状态
//        canvas.restore();
    }
}