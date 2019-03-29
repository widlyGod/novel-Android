package com.novel.cn.view.decoration;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

public class GridItemDecoration extends RecyclerView.ItemDecoration {

    private ColorDrawable mColorDrawable = new ColorDrawable(Color.parseColor("#ebebeb"));

    private int space = 1;

    private boolean edge = true;

    private boolean isFistFull = false;

    public GridItemDecoration() {
    }

    public GridItemDecoration(int space) {
        this.space = space;
    }

    public GridItemDecoration(int space, int color) {
        this.mColorDrawable = new ColorDrawable(color);
        ;
        this.space = space;
    }

    public void setEdge(boolean edge) {
        this.edge = edge;
    }

    public void setColorDrawable(ColorDrawable mColorDrawable) {
        this.mColorDrawable = mColorDrawable;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {


        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        int position = parent.getChildAdapterPosition(view);
        //列数
        int spanCount = layoutManager.getSpanCount();
        //当前所在列数下标
        int spanIndex = ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
        //当前跨列
        int spanSize = ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanSize();


        int itemCount = parent.getAdapter().getItemCount();


        isFistFull = position == 0 && spanSize == spanCount;

        if (position < itemCount) {
            int gravity;
            if (spanCount == spanSize) {
                gravity = Gravity.FILL_HORIZONTAL;
            } else if (spanIndex == 0 && spanCount > 1) {
                gravity = Gravity.LEFT;
            } else if (spanIndex == spanCount - 1 && spanCount > 1) {
                gravity = Gravity.RIGHT;
            } else {
                gravity = Gravity.CENTER;
            }

            int edgeWidth;


            if (edge) {
                edgeWidth = space * (spanCount + 1) / spanCount - space;
            } else {
                edgeWidth = space * (spanCount - 1) / spanCount;
            }

            int halfSpace = space / 2;

            if (halfSpace == 0) {
                halfSpace = 1;
            }
            switch (gravity) {
                case Gravity.LEFT:
                    if (edge)
                        outRect.left = space;
                    outRect.right = edgeWidth;
                    break;
                case Gravity.RIGHT:
                    if (edge)
                        outRect.right = space;
                    outRect.left = edgeWidth;
                    break;
                case Gravity.CENTER:
                    if (spanIndex == 1) {
                        outRect.right = halfSpace;
                        outRect.left = space - edgeWidth;
                    } else if (spanIndex == spanCount - 2) {
                        outRect.right = space - edgeWidth;
                        outRect.left = halfSpace;
                    } else {
                        outRect.right = halfSpace;
                        outRect.left = halfSpace;
                    }
                    break;
                case Gravity.FILL_HORIZONTAL:
                    if (edge) {
                        /*outRect.left = space;
                        outRect.right = space;*/
                    }
                    break;
            }
            outRect.bottom = space;
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int itemCount = parent.getChildCount();
        GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
        //列数
        int spanCount = layoutManager.getSpanCount();
        for (int i = 0; i < itemCount; i++) {
            //获取每个itemview
            View child = parent.getChildAt(i);

            //垂直线 itemView 的右边
            int left = child.getRight();
            int top = child.getTop() - space;
            int right = left + space;
            int bottom = child.getBottom() + space;

            mColorDrawable.setBounds(left, top, right, bottom);
            mColorDrawable.draw(c);

            //水平线  itemView的下面

            left = child.getLeft();
            top = child.getBottom();
            right = child.getRight();
            bottom = top + space;
            mColorDrawable.setBounds(left, top, right, bottom);
            mColorDrawable.draw(c);

            int spanIndex = ((GridLayoutManager.LayoutParams) child.getLayoutParams()).getSpanIndex();
            //上边缘
            if (edge) {
                if (i < spanCount) {
                    left = child.getLeft();
                    top = child.getTop();
                    right = child.getRight();
                    bottom = top - space;
                    mColorDrawable.setBounds(left, top, right, bottom);
                    mColorDrawable.draw(c);
                }
                //垂直线 itemView 的左
                if (spanIndex == 0) {
                    left = child.getLeft() - space;
                    top = child.getTop() - space;
                    right = child.getLeft();
                    bottom = child.getBottom() + space;
                    mColorDrawable.setBounds(left, top, right, bottom);
                    mColorDrawable.draw(c);
                }
            }
        }
    }
}
