package com.novel.cn.view.read

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.jess.arms.utils.LogUtils
import org.jetbrains.anko.displayMetrics

class PageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {


    // 唤醒菜单的区域
    private var mCenterRect: RectF? = null


    //是否在移动
    private var isMove = false

    //记录按下的坐标
    private var mStartX = 0f
    private var mStartY = 0f

    private lateinit var mPageLoader: PageLoader2

    var mDisplayWidth = 0 // 当前View的宽
    var mDisplayHeight = 0 // 当前View的高

    //屏幕的间距
    var mMarginLeft: Int = 0
    var mMarginRight: Int = 0
    var mMarginTop: Int = 0
    var mMarginBottom: Int = 0


    //中间区域菜单点击

    private var onTouchListener: OnTouchListener? = null


    private var mAnimation: BaseAnimation


    init {
        mMarginLeft = 0
        mMarginTop = 0
        mMarginRight = 0
        mMarginBottom = 0

        mDisplayWidth = context.displayMetrics.widthPixels
        mDisplayHeight = context.displayMetrics.heightPixels

        mAnimation = ScrollAnimation(this, mDisplayWidth, mDisplayHeight, 0, mMarginTop, 0, mMarginBottom)
        mPageLoader = PageLoader2(this,mDisplayWidth, mDisplayHeight)
        mPageLoader.loadPage()
    }


    fun setPageMode() {
//        mAnimation = ScrollAnimation(this, mWidth, mHeight)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)
        //当前点击的x坐标
        val x = event.x
        //当前点击的y坐标
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                isMove = false
                mStartX = x
                mStartY = y
                mAnimation.onTouchEvent(event)
            }
            MotionEvent.ACTION_MOVE -> {
                // 判断是否大于最小滑动值。
                val slop = ViewConfiguration.get(context).scaledTouchSlop

                if (!isMove) {
                    //大于最小滑动值，说明滑动了
                    isMove = Math.abs(mStartX - event.x) > slop || Math.abs(mStartY - event.y) > slop
                }

                if (isMove) {
                    //滑动后处理
                    mAnimation.onTouchEvent(event)
                }
            }
            MotionEvent.ACTION_UP -> {
                //没有滑动的话，说明是点击事件
                if (!isMove) {
                    if (mCenterRect == null) {
                        //设置菜单点击的区域
                        mCenterRect = RectF((width / 5).toFloat(), (height / 5).toFloat(),
                                (width / 5 * 4).toFloat(), (height / 5 * 4).toFloat())
                    }

                    if (mCenterRect?.contains(x, y)!!) {
                        //点击了中间菜单区域
                        onTouchListener?.onMenuClick()
                    } else {
                        onTouchListener?.onClick()
                    }
                }
                mAnimation.onTouchEvent(event)
            }

        }
        return true
    }

    fun setOnTouchListener(listener: OnTouchListener) {
        this.onTouchListener = listener
    }


    override fun onDraw(canvas: Canvas) {
        mAnimation.draw(canvas)
    }

    fun init() {
        mPageLoader.drawBackground(mAnimation.getBackroundBitmap())
        mPageLoader.drawPage(mAnimation.getBitmap())
        invalidate()
    }


    private fun drawBackground(bitmap: Bitmap) {
        val canvas = Canvas(bitmap)
        canvas.drawColor(ContextCompat.getColor(context, PageStyle.BG_2.bgColor))
    }


    /**
     * 绘制页面
     */
    private fun drawPage(bitmap: Bitmap) {
        val canvas = Canvas(bitmap)
        if (mAnimation is ScrollAnimation) {
            canvas.drawColor(ContextCompat.getColor(context, PageStyle.BG_2.bgColor))
        }
        val mTextPaint = TextPaint()
        mTextPaint.isAntiAlias = true
        mTextPaint.textSize = 33f
        mTextPaint.color = Color.parseColor("#000000")
        LogUtils.warnInfo("==========================>>>>")
        canvas.drawText("数据的话房间卡电话费肯", 0f, 400f, mTextPaint)

    }


    override fun computeScroll() {
        mAnimation.scrollAnimation()
        super.computeScroll()
    }

    interface OnTouchListener {
        fun onMenuClick()

        fun onClick()
    }

}
