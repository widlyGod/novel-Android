package com.novel.cn.view.read

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import org.jetbrains.anko.displayMetrics
import java.util.*

class PageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {


    // 唤醒菜单的区域
    private var mCenterRect: RectF? = null


    //是否在移动
    private var isMove = false

    //记录按下的坐标
    private var mStartX = 0f
    private var mStartY = 0f

    private var mWidth = 0 // 当前View的宽
    private var mHeight = 0 // 当前View的高



    //中间区域菜单点击

    private var onTouchListener: OnTouchListener? = null


    private var mAnimation: BaseAnimation


    init {
        mWidth = context.displayMetrics.widthPixels
        mHeight = context.displayMetrics.heightPixels
        mAnimation = ScrollAnimation(this, mWidth, mHeight)

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

        if(iss) {
            drawBackground(mAnimation.getBackroundBitmap())
            drawPage(mAnimation.getBitmap())

            invalidate()
        }
    }
    var iss = false
    override fun onFinishInflate() {
        super.onFinishInflate()
        iss = true
    }

    private fun drawBackground(bitmap: Bitmap) {
        val canvas = Canvas(bitmap)
        canvas.drawColor(ContextCompat.getColor(context, PageStyle.BG_2.bgColor))
    }

    private fun drawPage(bitmap: Bitmap) {
        val canvas = Canvas(bitmap)
        if (mAnimation is ScrollAnimation) {
            canvas.drawColor(ContextCompat.getColor(context, PageStyle.BG_2.bgColor))
        }
        val mTextPaint = TextPaint()
        mTextPaint.isAntiAlias = true
        mTextPaint.textSize = 33f
        mTextPaint.color = Color.parseColor("#000000")
        for (i in 0..100) {
            canvas.drawText("数据的话房间卡电话费肯定撒大声地是即可将", 50f, (0 + i * 40).toFloat(), mTextPaint)
        }

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
