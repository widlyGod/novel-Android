package com.novel.cn.view.readpage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import com.jess.arms.utils.LogUtils;
import com.novel.cn.R;
import com.novel.cn.view.readpage.animation.CoverPageAnim;
import com.novel.cn.view.readpage.animation.HorizonPageAnim;
import com.novel.cn.view.readpage.animation.NonePageAnim;
import com.novel.cn.view.readpage.animation.PageAnimation;
import com.novel.cn.view.readpage.animation.ScrollPageAnim;
import com.novel.cn.view.readpage.animation.SimulationPageAnim;
import com.novel.cn.view.readpage.animation.SlidePageAnim;

/**
 * Created by Administrator on 2016/8/29 0029.
 * 原作者的GitHub Project Path:(https://github.com/PeachBlossom/treader)
 * 绘制页面显示内容的类
 */
public class PageView extends View {

    private final static String TAG = "BookPageWidget";

    private int mViewWidth = 0; // 当前View的宽
    private int mViewHeight = 0; // 当前View的高

    private int mStartX = 0;
    private int mStartY = 0;
    private boolean isMove = false;
    // 初始化参数
    private int mBgColor = 0xFFCEC29C;
    private PageMode mPageMode = PageMode.SIMULATION;
    // 是否允许点击
    private boolean canTouch = true;
    // 唤醒菜单的区域
    private RectF mCenterRect = null;
    private boolean isPrepare;
    // 动画类
    private PageAnimation mPageAnim;

    private boolean isLoading;


    // 动画监听类
    private PageAnimation.OnPageChangeListener mPageAnimListener = new PageAnimation.OnPageChangeListener() {
        @Override
        public boolean hasPrev() {
            LogUtils.warnInfo("===========>>>>");
            return PageView.this.hasPrevPage();
        }

        @Override
        public boolean hasNext() {
            return PageView.this.hasNextPage();
        }

        @Override
        public void pageCancel() {
            PageView.this.pageCancel();
        }
    };

    //点击监听
    private TouchListener mTouchListener;
    //内容加载器
    private PageLoader mPageLoader;

    public PageView(Context context) {
        this(context, null);
    }

    public PageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;

        isPrepare = true;

        if (mPageLoader != null) {
            mPageLoader.prepareDisplay(w, h);
        }
    }

    //设置翻页的模式
    void setPageMode(PageMode pageMode) {
        mPageMode = pageMode;
        //视图未初始化的时候，禁止调用
        if (mViewWidth == 0 || mViewHeight == 0) return;

        switch (mPageMode) {
            case SIMULATION:
                mPageAnim = new SimulationPageAnim(mViewWidth, mViewHeight, this, mPageAnimListener);
                break;
            case COVER:
                mPageAnim = new CoverPageAnim(mViewWidth, mViewHeight, this, mPageAnimListener);
                break;
            case SLIDE:
                mPageAnim = new SlidePageAnim(mViewWidth, mViewHeight, this, mPageAnimListener);
                break;
            case NONE:
                mPageAnim = new NonePageAnim(mViewWidth, mViewHeight, this, mPageAnimListener);
                break;
            case SCROLL:
                mPageAnim = new ScrollPageAnim(mViewWidth, mViewHeight, 0,
                        0, this, mPageAnimListener);
                break;
            default:
                mPageAnim = new SimulationPageAnim(mViewWidth, mViewHeight, this, mPageAnimListener);
        }
    }

    public Bitmap getNextBitmap() {
        if (mPageAnim == null) return null;
        return mPageAnim.getNextBitmap();
    }

    public Bitmap getBgBitmap() {
        if (mPageAnim == null) return null;
        return mPageAnim.getBgBitmap();
    }

    public boolean autoPrevPage() {
        //滚动暂时不支持自动翻页
        if (mPageAnim instanceof ScrollPageAnim) {
            return false;
        } else {
            startPageAnim(PageAnimation.Direction.PRE);
            return true;
        }
    }

    public boolean autoNextPage() {
        if (mPageAnim instanceof ScrollPageAnim) {
            return false;
        } else {
            startPageAnim(PageAnimation.Direction.NEXT);
            return true;
        }
    }

    private void startPageAnim(PageAnimation.Direction direction) {
        if (mTouchListener == null) return;
        //是否正在执行动画
        abortAnimation();
        if (direction == PageAnimation.Direction.NEXT) {
            int x = mViewWidth;
            int y = mViewHeight;
            //初始化动画
            mPageAnim.setStartPoint(x, y);
            //设置点击点
            mPageAnim.setTouchPoint(x, y);
            //设置方向
            Boolean hasNext = hasNextPage();

            mPageAnim.setDirection(direction);
            if (!hasNext) {
                return;
            }
        } else {
            int x = 0;
            int y = mViewHeight;
            //初始化动画
            mPageAnim.setStartPoint(x, y);
            //设置点击点
            mPageAnim.setTouchPoint(x, y);
            mPageAnim.setDirection(direction);
            //设置方向方向
            Boolean hashPrev = hasPrevPage();
            if (!hashPrev) {
                return;
            }
        }
        mPageAnim.startAnim(isMove);
        this.postInvalidate();
    }

    public void setBgColor(int color) {
        mBgColor = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //绘制动画
        mPageAnim.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        if (!canTouch && event.getAction() != MotionEvent.ACTION_DOWN) return true;
        float mLoveBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.ic_loves).getHeight();
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = x;
                mStartY = y;
                isMove = false;
                canTouch = mTouchListener.onTouch();
                mPageAnim.onTouchEvent(event);
                break;
            case MotionEvent.ACTION_MOVE:
                // 判断是否大于最小滑动值。
                int slop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
                if (!isMove) {
                    isMove = Math.abs(mStartX - event.getX()) > slop || Math.abs(mStartY - event.getY()) > slop;
                }

                // 如果滑动了，则进行翻页。
                if (isMove) {
                    mPageAnim.onTouchEvent(event);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (!isMove) {
                    int a = 0;
                    if (((ScrollPageAnim) mPageAnim).getmActiveViews().size() > 1) {
                        a = y - (((ScrollPageAnim) mPageAnim).getmActiveViews().get(1).top);
                    }
                    int b = y - (((ScrollPageAnim) mPageAnim).getmActiveViews().get(0).top);
                    if (a != 0 && b != 0) {
                        LogUtils.warnInfo("////" + pageType + "====>" + a + "======>" + b + "----" + nowLove + "|||" + lastLove);
                        switch (pageType) {
                            case 0:
                                if (a >= nowLove && a <= (nowLove + mLoveBitmap)) {
                                    mTouchListener.reward();
                                    return true;
                                }
                                break;
                            case 1:
                                if (b >= lastLove && b <= (lastLove + mLoveBitmap)) {
                                    mTouchListener.reward();
                                    return true;
                                }
                                break;
                            case 2:
                                if (b >= lastLove && b <= (lastLove + mLoveBitmap)) {
                                    mTouchListener.reward();
                                    return true;
                                }
                                if (a >= nowLove && a <= (nowLove + mLoveBitmap)) {
                                    mTouchListener.reward();
                                    return true;
                                }
                                break;
                            case 3:
                                if (lastLove == 0) {
                                    if (b >= nowLove && b <= (nowLove + mLoveBitmap)) {
                                        mTouchListener.reward();
                                        return true;
                                    }
                                }
//                                if (lastPageType == 2) {
//                                    if (b >= lastLove && b <= (lastLove + mLoveBitmap)) {
//                                        mTouchListener.reward();
//                                        return true;
//                                    }
//                                } else {
                                    if (a >= nowLove && a <= (nowLove + mLoveBitmap)) {
                                        mTouchListener.reward();
                                        return true;
//                                    }
                                }
                                break;

                        }
                    }
                    if (a == 0 && b != 0) {
                        if (b >= nowLove && b <= (nowLove + mLoveBitmap)) {
                            mTouchListener.reward();
                            return true;
                        }
                    }
                    //设置中间区域范围
                    if (mCenterRect == null) {
                        mCenterRect = new RectF(mViewWidth / 5, mViewHeight / 3,
                                mViewWidth * 4 / 5, mViewHeight * 2 / 3);
                    }

                    //是否点击了中间
                    if (mCenterRect.contains(x, y)) {
                        if (mTouchListener != null) {
                            mTouchListener.center();
                        }
                        return true;
                    }
                }
                mPageAnim.onTouchEvent(event);
                break;
        }
        return true;
    }

    /**
     * 判断是否存在上一页
     *
     * @return
     */
    private boolean hasPrevPage() {

        boolean prev = mPageLoader.prev();
        mTouchListener.prePage(prev);
        return prev;
    }

    /**
     * 判断是否下一页存在
     *
     * @return
     */
    private boolean hasNextPage() {
        boolean next = mPageLoader.next();
        mTouchListener.nextPage(next);
        return next;
    }

    private void pageCancel() {
        mTouchListener.cancel();
        mPageLoader.pageCancel();
    }

    @Override
    public void computeScroll() {

        mPageAnim.scrollAnim();
        super.computeScroll();
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
        abortAnimation();

    }

    //如果滑动状态没有停止就取消状态，重新设置Anim的触碰点
    public void abortAnimation() {
        mPageAnim.abortAnim();
    }

    public boolean isRunning() {
        if (mPageAnim == null) {
            return false;
        }
        return mPageAnim.isRunning();
    }

    public boolean isPrepare() {
        return isPrepare;
    }

    public void setTouchListener(TouchListener mTouchListener) {
        this.mTouchListener = mTouchListener;
    }

    public void drawNextPage() {
        if (!isPrepare) return;

        if (mPageAnim instanceof HorizonPageAnim) {
            ((HorizonPageAnim) mPageAnim).changePage();
        }
        mPageLoader.drawPage(getNextBitmap(), false);
    }

    /**
     * 绘制当前页。
     *
     * @param isUpdate
     */
    public void drawCurPage(boolean isUpdate) {
        if (!isPrepare) return;

        if (!isUpdate) {
            if (mPageAnim instanceof ScrollPageAnim) {
                ((ScrollPageAnim) mPageAnim).resetBitmap();
            }
        }

        mPageLoader.drawPage(getNextBitmap(), isUpdate);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mPageAnim.abortAnim();
        mPageAnim.clear();

        mPageLoader = null;
        mPageAnim = null;
    }

    int pageType = 3;
    float lastLove = 0;
    float nowLove = 0;
    int lastPageType = 0;


    /**
     * 获取 PageLoader
     *
     * @return
     */
    public PageLoader getPageLoader(String bookId) {
        // 判是否已经存在
        if (mPageLoader != null) {
            return mPageLoader;
        }
        // 根据书籍类型，获取具体的加载器

        mPageLoader = new NetPageLoader(this, bookId);

        mPageLoader.setLastpage(new PageLoader.Lastpage() {
            @Override
            public void reward(int a, float love) {
                if (pageType != 0)
                    lastPageType = pageType;
                pageType = a;
//                LogUtils.warnInfo("////" + pageType + "====>" + a + "======>" + love + "----" + nowLove + "|||" + lastLove);
                switch (a) {
                    case 0:
                        nowLove = love;
                        break;
                    case 1:
                        if (nowLove != 0)
                            lastLove = nowLove;
                        nowLove = 0;
                        break;
                    case 2:
                        nowLove = love;
                        break;
                    case 3:
                        if (nowLove == 0)
                            nowLove = lastLove;
                        break;
                    case 4:
                        nowLove = 0;
                        break;
                }
            }
        });

        // 判断是否 PageView 已经初始化完成
        if (mViewWidth != 0 || mViewHeight != 0) {
            // 初始化 PageLoader 的屏幕大小
            mPageLoader.prepareDisplay(mViewWidth, mViewHeight);
        }

        return mPageLoader;
    }

    public void resetPage() {
    }

    public void reset() {
        ((ScrollPageAnim) mPageAnim).reset();
    }


    public interface TouchListener {
        boolean onTouch();

        void center();

        void prePage(boolean prev);

        void nextPage(boolean next);

        void cancel();

        void reward();
    }
}
