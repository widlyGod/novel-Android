package com.novel.cn.view.read

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.Scroller

abstract class BaseAnimation constructor(view: PageView, w: Int, h: Int, marginWidth: Int = 0, marginHeihgt: Int = 0) {

    //屏幕的尺寸
    protected var mScreenWidth: Int = 0
    protected var mScreenHeight: Int = 0
    //屏幕的间距
    protected var mMarginWidth: Int = 0
    protected var mMarginHeight: Int = 0
    //视图的尺寸
    protected var mViewWidth: Int = 0
    protected var mViewHeight: Int = 0

    protected var mPageView: PageView

    //滑动装置
    protected var mScroller: Scroller

    //起始点
    protected var mStartX: Float = 0f
    protected var mStartY: Float = 0f
    //触碰点
    protected var mTouchX: Float = 0f
    protected var mTouchY: Float = 0f
    //上一个触碰点
    protected var mLastX: Float = 0f
    protected var mLastY: Float = 0f

    protected var isRunning = false

    //监听器
//    protected var mListener: OnPageChangeListener

    init {
        mScreenWidth = w
        mScreenHeight = h

        mMarginWidth = marginWidth
        mMarginHeight = marginHeihgt

        mViewWidth = mScreenWidth - marginWidth * 2
        mViewHeight = mScreenHeight - marginHeihgt * 2

        mPageView = view

        mScroller = Scroller(mPageView.context, LinearInterpolator())
    }


    fun setStartPoint(x: Float, y: Float) {
        mStartX = x
        mStartY = y

        mLastX = mStartX
        mLastY = mStartY
    }

    fun setTouchPoint(x: Float, y: Float) {
        mLastX = mTouchX
        mLastY = mTouchY

        mTouchX = x
        mTouchY = y
    }

    abstract fun getBackroundBitmap(): Bitmap

    abstract fun getBitmap(): Bitmap

    /**
     * 绘制图形
     * @param canvas
     */
    abstract fun draw(canvas: Canvas)

    /**
     * 点击事件的处理
     * @param event
     */
    abstract fun onTouchEvent(event: MotionEvent): Boolean

    /**
     * 取消动画
     */
    abstract fun abortAnimation()

    /**
     * 开始动画
     */
    abstract fun startAnimation()

    /**
     * 滚动动画
     * 必须放在computeScroll()方法中执行
     */
    abstract fun scrollAnimation()


    interface OnPageChangeListener {
        fun hasPrev(): Boolean
        operator fun hasNext(): Boolean
        fun pageCancel()
    }
}