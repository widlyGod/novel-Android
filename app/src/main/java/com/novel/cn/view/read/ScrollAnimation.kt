package com.novel.cn.view.read

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import java.util.*

/**
 * 上下滚动翻页
 */
class ScrollAnimation(view: PageView, w: Int, h: Int, marginWidth: Int = 0, marginHeight: Int = 0) : BaseAnimation(view, w, h, marginWidth, marginHeight) {


    private var mBackgroundBitmap = Bitmap.createBitmap(mScreenWidth, mScreenHeight, Bitmap.Config.RGB_565)

    private var mBitmap = Bitmap.createBitmap(mScreenWidth, mScreenHeight, Bitmap.Config.RGB_565)

    // 滑动追踪的时间
    private val VELOCITY_DURATION = 1000
    private var mVelocity: VelocityTracker? = null

    // 被废弃的图片列表
    private var mScrapViews = ArrayDeque<BitmapView>(2);
    // 正在被利用的图片列表
    private val mActiveViews = ArrayList<BitmapView>(2)

    // 是否处于刷新阶段
    private var isRefresh = true

    init {

        for (i in 0 until 2) {
            val bitmap = Bitmap.createBitmap(mViewWidth, mViewHeight, Bitmap.Config.RGB_565)
            val srcRect = Rect(0, 0, mViewWidth, mViewHeight)
            val destRect = Rect(0, 0, mViewWidth, mViewHeight)
            val bitmapView = BitmapView(bitmap, srcRect, destRect, 0, bitmap.height)
            mScrapViews.add(bitmapView)
        }
        isRefresh = false
        onLayout()
    }

    private fun onLayout() {
        if (mActiveViews.size == 0) {
            fillDown(0, 0)
        } else {
            val offset = (mTouchY - mLastY).toInt()
            // 判断是下滑还是上拉 (下滑)
            if (offset > 0) {
                val topEdge = mActiveViews[0].top
                fillUp(topEdge, offset)
            } else {
                // 底部的距离 = 当前底部的距离 + 滑动的距离 (因为上滑，得到的值肯定是负的)
                val bottomEdge = mActiveViews[mActiveViews.size - 1].bottom
                fillDown(bottomEdge, offset)
            }// 上拉
        }
    }

    // 底部填充


    /**
     * 创建View填充底部空白部分
     *
     * @param bottomEdge :当前最后一个View的底部，在整个屏幕上的位置,即相对于屏幕顶部的距离
     * @param offset     :滑动的偏移量
     */
    private fun fillDown(bottomEdge: Int, offset: Int) {
        val downIt = mActiveViews.iterator()
        var view: BitmapView

        // 进行删除
        while (downIt.hasNext()) {
            view = downIt.next()
            view.top = view.top + offset
            view.bottom = view.bottom + offset
            // 设置允许显示的范围
            view.destRect.top = view.top
            view.destRect.bottom = view.bottom

            // 判断是否越界了
            if (view.bottom <= 0) {
                // 添加到废弃的View中
                mScrapViews.add(view)
                // 从Active中移除
                downIt.remove()
            }
        }
        // 滑动之后的最后一个 View 的距离屏幕顶部上的实际位置
        var realEdge = bottomEdge + offset

        // 进行填充
        while (realEdge < mViewHeight && mActiveViews.size < 2) {
            // 从废弃的Views中获取一个
            view = mScrapViews.first
            if (view == null) return

            val cancelBitmap = mBitmap
            mBitmap = view.bitmap

            if (!isRefresh) {
                mPageView.init()
//                // 如果加载成功，那么就将View从ScrapViews中移除
//                mScrapViews.removeFirst()
//                // 添加到存活的Bitmap中
//                mActiveViews.add(view)
//
//                // 设置Bitmap的范围
//                view.top = realEdge
//                view.bottom = realEdge + view.bitmap.height
//                // 设置允许显示的范围
//                view.destRect.top = view.top
//                view.destRect.bottom = view.bottom
//
//                realEdge += view.bitmap.height

            }

            // 如果加载成功，那么就将View从ScrapViews中移除
            mScrapViews.removeFirst()
            // 添加到存活的Bitmap中
            mActiveViews.add(view)
//            mDirection = Direction.DOWN

            // 设置Bitmap的范围
            view.top = realEdge
            view.bottom = realEdge + view.bitmap.height
            // 设置允许显示的范围
            view.destRect.top = view.top
            view.destRect.bottom = view.bottom

            realEdge += view.bitmap.height
        }

    }

    private var upIt: MutableIterator<BitmapView>? = null

    private fun fillUp(topEdge: Int, offset: Int) {
         upIt = mActiveViews.iterator()
        // 首先进行布局的调整
        var view: BitmapView
        while (upIt!!.hasNext()) {
            view = upIt!!.next()
            view.top = view.top + offset
            view.bottom = view.bottom + offset
            //设置允许显示的范围
            view.destRect.top = view.top
            view.destRect.bottom = view.bottom

            // 判断是否越界了
            if (view.top >= mViewHeight) {
                // 添加到废弃的View中
                mScrapViews.add(view)
                // 从Active中移除
                upIt!!.remove()

                // 如果原先是下，现在变成从上加载了，则表示取消加载

            }
        }

        // 滑动之后，第一个 View 的顶部距离屏幕顶部的实际位置。
        var realEdge = topEdge + offset

        // 对布局进行View填充
        while (realEdge > 0 && mActiveViews.size < 2) {
            // 从废弃的Views中获取一个
            view = mScrapViews.first
            if (view == null) return

            // 判断是否存在上一章节
            val cancelBitmap = mBitmap
            mBitmap = view.bitmap
            if (!isRefresh) {
                mPageView.init()
//                val hasPrev = mListener.hasPrev() // 如果不成功则无法滑动
                // 如果不存在next,则进行还原
//                if (!hasPrev) {
//                mBitmap = cancelBitmap
//                for (activeView in mActiveViews) {
//                    activeView.top = 0
//                    activeView.bottom = mViewHeight
//                    // 设置允许显示的范围
//                    activeView.destRect.top = activeView.top
//                    activeView.destRect.bottom = activeView.bottom
//                }
//                abortAnimation()
//                return

            }
            // 如果加载成功，那么就将View从ScrapViews中移除
            mScrapViews.removeFirst()
            // 加入到存活的对象中
            mActiveViews.add(0, view)
//            mDirection = Direction.UP
            // 设置Bitmap的范围
            view.top = realEdge - view.bitmap.height
            view.bottom = realEdge

            // 设置允许显示的范围
            view.destRect.top = view.top
            view.destRect.bottom = view.bottom
            realEdge -= view.bitmap.height
        }
    }

    /**
     * 重置位移
     */
    fun resetBitmap() {
        isRefresh = true
        // 将所有的Active加入到Scrap中
        for (view in mActiveViews) {
            mScrapViews.add(view)
        }
        // 清除所有的Active
        mActiveViews.clear()
        // 重新进行布局
        onLayout()
        isRefresh = false
    }

    private var tmpView: BitmapView? = null

    override fun draw(canvas: Canvas) {
        onLayout()

        canvas.drawBitmap(mBackgroundBitmap, 0f, 0f, null)
        //绘制内容
        canvas.save()
        //移动位置
        canvas.translate(0f, mMarginHeight.toFloat())
        //裁剪显示区域
        canvas.clipRect(0, 0, mViewWidth, mViewHeight)

        canvas.drawBitmap(mBitmap, 0f, 0f, null)
        for (i in mActiveViews.indices) {
            tmpView = mActiveViews[i]
            val canvas = Canvas(tmpView!!.bitmap)
//            if(i == 0){
//
//                canvas.drawColor(Color.parseColor("#ff00ff"))
//            }else{
//                canvas.drawColor(Color.parseColor("#00ff00"))
//            }
            canvas.drawBitmap(tmpView!!.bitmap, tmpView!!.srcRect, tmpView!!.destRect, null)
        }
        canvas.restore()
    }


    override fun getBackroundBitmap(): Bitmap {
        return mBackgroundBitmap
    }

    override fun getBitmap(): Bitmap {
        return mBitmap
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        // 初始化速度追踪器
        if (mVelocity == null) {
            mVelocity = VelocityTracker.obtain()
        }
        mVelocity!!.addMovement(event)

    // 设置触碰点
        setTouchPoint(x, y)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isRunning = false
                setStartPoint(x, y)
                abortAnimation()
            }
            MotionEvent.ACTION_MOVE -> {
                mVelocity?.computeCurrentVelocity(VELOCITY_DURATION)
                isRunning = true
                // 进行刷新
                mPageView.postInvalidate()
            }
            MotionEvent.ACTION_UP -> {
                isRunning = false
                startAnimation()
                mVelocity?.recycle()
                mVelocity = null
            }
            MotionEvent.ACTION_CANCEL -> {
                mVelocity?.recycle()
                mVelocity = null
            }
        }
        return true
    }

    override fun abortAnimation() {
        if (!mScroller.isFinished) {
            mScroller.abortAnimation()
            isRunning = false
        }
    }


    @Synchronized
    override fun startAnimation() {
        isRunning = true
        mScroller.fling(0, mTouchY.toInt(), 0, mVelocity!!.yVelocity.toInt(), 0, 0, Integer.MAX_VALUE * -1, Integer.MAX_VALUE)
    }

    override fun scrollAnimation() {
        if (mScroller.computeScrollOffset()) {
            val x = mScroller.currX
            val y = mScroller.currY
            setTouchPoint(x.toFloat(), y.toFloat())
            //动画结束
            if (mScroller.finalX == x && mScroller.finalY == y) {
                isRunning = false
            }
            mPageView.postInvalidate()
        }
    }

    private class BitmapView(var bitmap: Bitmap, var srcRect: Rect, var destRect: Rect, var top: Int, var bottom: Int)
}
