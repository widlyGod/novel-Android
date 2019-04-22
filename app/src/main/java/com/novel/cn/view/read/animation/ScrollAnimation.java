package com.novel.cn.view.read.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.jess.arms.utils.LogUtils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;

public class ScrollAnimation extends BaseAnimation {

    // 滑动追踪的时间
    private static final int VELOCITY_DURATION = 1000;
    private VelocityTracker mVelocity;

    // 整个Bitmap的背景显示
    private Bitmap mBgBitmap;

    // 下一个展示的图片
    private Bitmap mNextBitmap;

    // 被废弃的图片列表
    private ArrayDeque<BitmapView> mScrapViews;
    // 正在被利用的图片列表
    private ArrayList<BitmapView> mActiveViews = new ArrayList<>(2);

    // 是否处于刷新阶段
    private boolean isRefresh = true;

    public ScrollAnimation(View view, int width, int height) {
        super(view, width, height);

        mBgBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.RGB_565);
        mScrapViews = new ArrayDeque<>(2);
        for (int i = 0; i < 2; ++i) {
            BitmapView v = new BitmapView();
            v.bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.RGB_565);
            v.srcRect = new Rect(0, 0, mWidth, mHeight);
            v.destRect = new Rect(0, 0, mWidth, mHeight);
            v.top = 0;
            v.bottom = v.bitmap.getHeight();

            mScrapViews.push(v);
        }
        onLayout();
        isRefresh = false;
    }

    private void onLayout() {
        // 如果还没有开始加载，则从上到下进行绘制
        if (mActiveViews.size() == 0) {
            fillDown(0, 0);
        } else {
            int offset = (int) (mTouchY - mLastY);
            // 判断是下滑还是上拉 (下滑)
            if (offset > 0) {
                int topEdge = mActiveViews.get(0).top;
                fillUp(topEdge, offset);
            }
            // 上拉
            else {
                // 底部的距离 = 当前底部的距离 + 滑动的距离 (因为上滑，得到的值肯定是负的)
                int bottomEdge = mActiveViews.get(mActiveViews.size() - 1).bottom;
                fillDown(bottomEdge, offset);
            }
        }
    }

    /**
     * 创建View填充底部空白部分
     *
     * @param bottomEdge :当前最后一个View的底部，在整个屏幕上的位置,即相对于屏幕顶部的距离
     * @param offset     :滑动的偏移量
     */
    private void fillDown(int bottomEdge, int offset) {

        Iterator<BitmapView> iterator = mActiveViews.iterator();
        BitmapView view;

        while (iterator.hasNext()) {
            view = iterator.next();
            view.top = view.top + offset;
            view.bottom = view.bottom + offset;
            // 设置允许显示的范围
            view.destRect.top = view.top;
            view.destRect.bottom = view.bottom;

            // 判断是否越界了
            if (view.bottom <= 0) {
                // 添加到废弃的View中
                mScrapViews.add(view);
                // 从Active中移除
                iterator.remove();
            }
        }


        // 滑动之后的最后一个 View 的距离屏幕顶部上的实际位置
        int realEdge = bottomEdge + offset;


        // 进行填充
        while (realEdge < mVisibleHeight && mActiveViews.size() < 2) {
            LogUtils.warnInfo("===========");
            // 从废弃的Views中获取一个
            view = mScrapViews.getFirst();
/*          //擦除其Bitmap(重新创建会不会更好一点)
            eraseBitmap(view.bitmap,view.bitmap.getWidth(),view.bitmap.getHeight(),0,0);*/
            if (view == null) return;

            Bitmap cancelBitmap = mNextBitmap;
            mNextBitmap = view.bitmap;

            // 如果加载成功，那么就将View从ScrapViews中移除
            mScrapViews.removeFirst();
            // 添加到存活的Bitmap中
            mActiveViews.add(view);

            // 设置Bitmap的范围
            view.top = realEdge;
            view.bottom = realEdge + view.bitmap.getHeight();
            // 设置允许显示的范围
            view.destRect.top = view.top;
            view.destRect.bottom = view.bottom;

            realEdge += view.bitmap.getHeight();
        }

    }


    /**
     * 创建View填充顶部空白部分
     *
     * @param topEdge : 当前第一个View的顶部，到屏幕顶部的距离
     * @param offset  : 滑动的偏移量
     */
    private void fillUp(int topEdge, int offset) {
        Iterator<BitmapView> iterator = mActiveViews.iterator();
        BitmapView view;
        while (iterator.hasNext()) {
            view = iterator.next();
            view.top = view.top + offset;
            view.bottom = view.bottom + offset;
            //设置允许显示的范围
            view.destRect.top = view.top;
            view.destRect.bottom = view.bottom;

            // 判断是否越界了
            if (view.top >= mVisibleHeight) {
                // 添加到废弃的View中
                mScrapViews.add(view);
                // 从Active中移除
                iterator.remove();
            }
        }

        // 滑动之后，第一个 View 的顶部距离屏幕顶部的实际位置。
        int realEdge = topEdge + offset;

        // 对布局进行View填充
        while (realEdge > 0 && mActiveViews.size() < 2) {
            // 从废弃的Views中获取一个
            view = mScrapViews.getFirst();
            if (view == null) return;

            // 判断是否存在上一章节
            Bitmap cancelBitmap = mNextBitmap;
            mNextBitmap = view.bitmap;

            // 如果加载成功，那么就将View从ScrapViews中移除
            mScrapViews.removeFirst();
            // 加入到存活的对象中
            mActiveViews.add(0, view);
            // 设置Bitmap的范围
            view.top = realEdge - view.bitmap.getHeight();
            view.bottom = realEdge;

            // 设置允许显示的范围
            view.destRect.top = view.top;
            view.destRect.bottom = view.bottom;
            realEdge -= view.bitmap.getHeight();

        }

    }

    @Override
    public void draw(Canvas canvas) {
        //进行布局
        onLayout();

        //绘制背景
        canvas.drawBitmap(mBgBitmap, 0, 0, null);
        //绘制内容
        canvas.save();
        //移动位置
        canvas.translate(0, mMarginHeight);

        canvas.clipRect(0, 0, mVisibleWidth, mVisibleHeight);

        //绘制Bitmap
        for (BitmapView view : mActiveViews) {
            canvas.drawBitmap(view.bitmap, view.srcRect, view.destRect, null);
        }
        
        canvas.restore();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        // 初始化速度追踪器
        if (mVelocity == null) {
            mVelocity = VelocityTracker.obtain();
        }

        mVelocity.addMovement(event);
        // 设置触碰点
        setTouchPoint(x, y);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isRunning = false;
                // 设置起始点
                setStartPoint(x, y);
                // 停止动画
                abortAnimation();
                break;
            case MotionEvent.ACTION_MOVE:
                mVelocity.computeCurrentVelocity(VELOCITY_DURATION);
                isRunning = true;
                // 进行刷新
                mView.postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                isRunning = false;
                // 开启动画
                startAnimation();
                // 删除检测器
                mVelocity.recycle();
                mVelocity = null;
                break;

            case MotionEvent.ACTION_CANCEL:
                try {
                    mVelocity.recycle(); // if velocityTracker won't be used should be recycled
                    mVelocity = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        return true;
    }

    @Override
    public void abortAnimation() {
        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
            isRunning = false;
        }

    }

    @Override
    public synchronized void startAnimation() {
        isRunning = true;
        mScroller.fling(0, (int) mTouchY, 0, (int) mVelocity.getYVelocity()
                , 0, 0, Integer.MAX_VALUE * -1, Integer.MAX_VALUE);
    }

    @Override
    public void scrollAnimation() {
        if (mScroller.computeScrollOffset()) {
            int x = mScroller.getCurrX();
            int y = mScroller.getCurrY();
            setTouchPoint(x, y);
            if (mScroller.getFinalX() == x && mScroller.getFinalY() == y) {
                isRunning = false;
            }
            mView.postInvalidate();
        }
    }

    @Override
    public Bitmap getBgBitmap() {
        return mBgBitmap;
    }

    @Override
    public Bitmap getBitmap() {
        return mNextBitmap;
    }

    private static class BitmapView {
        Bitmap bitmap;
        Rect srcRect;
        Rect destRect;
        int top;
        int bottom;
    }
}
