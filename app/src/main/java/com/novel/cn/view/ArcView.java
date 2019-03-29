package com.novel.cn.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.jess.arms.utils.ArmsUtils;
import com.jess.arms.utils.LogUtils;

public class ArcView extends View {


    private Paint mPaint;

    private int mWidth;

    private int mHeight;

    private int mArcHeight;

    public ArcView(Context context) {
        this(context, null);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

/*    @Override
    protected void dispatchDraw(Canvas canvas) {

        int saveCount = canvas.saveLayer(0, 0, getMeasuredWidth(), getMeasuredHeight(), null, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        Path path = new Path();

        path.moveTo(0, mHeight - mArcHeight);
        path.cubicTo(0, mHeight - mArcHeight, mWidth / 2, mHeight + mArcHeight, mWidth, mHeight - mArcHeight);
        path.lineTo(mWidth, mHeight);
        path.lineTo(0, mHeight);
        path.close();

        canvas.drawPath(path, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(saveCount);
    }*/

    @Override
    protected void onDraw(Canvas canvas) {

        Path path = new Path();
        path.moveTo(0, 0);
        path.cubicTo(0, 0, mWidth / 2, mHeight, mWidth, 0);
        path.lineTo(mWidth, mHeight);
        path.lineTo(0, mHeight);
        path.close();
        canvas.drawPath(path, mPaint);
    }
}
