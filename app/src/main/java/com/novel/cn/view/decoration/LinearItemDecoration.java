package com.novel.cn.view.decoration;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class LinearItemDecoration extends RecyclerView.ItemDecoration {
    private ColorDrawable mColorDrawable = new ColorDrawable(Color.parseColor("#e5e5e5"));

    private int mHeaderCount = 0;

    private int mFooterCount = 0;

    private int mHeight = 1;


    private int leftMargin = 0;

    private int rightMargin = 0;

    public LinearItemDecoration(int height) {
        this.mHeight = height;
    }

    public LinearItemDecoration() {
    }

    public LinearItemDecoration(int color, int mHeight) {
        this.mColorDrawable = new ColorDrawable(color);
        this.mHeight = mHeight;
    }

    public int getLeftMargin() {
        return leftMargin;
    }

    public void setLeftMargin(int leftMargin) {
        this.leftMargin = leftMargin;
    }

    public int getRightMargin() {
        return rightMargin;
    }

    public void setRightMargin(int rightMargin) {
        this.rightMargin = rightMargin;
    }

    public LinearItemDecoration(ColorDrawable colorDrawable, int mHeight) {
        this.mColorDrawable = colorDrawable;
        this.mHeight = mHeight;
    }

    public void setHeaderCount(int mHeaderCount) {
        this.mHeaderCount = mHeaderCount;
    }

    public void setFooterCount(int mFooterCount) {
        this.mFooterCount = mFooterCount;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int itemCount = parent.getAdapter().getItemCount();
        int orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();


        if (position >= mHeaderCount && position < itemCount - mFooterCount - 1) {
            if (orientation == RecyclerView.VERTICAL) {
                outRect.bottom = mHeight;
            } else {
                outRect.right = mHeight;
            }
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int orientation = ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();

        int start;
        int end;
        if (orientation == RecyclerView.VERTICAL) {
            start = parent.getPaddingLeft() + leftMargin;
            end = parent.getWidth() - parent.getPaddingRight() - rightMargin;
        } else {
            start = parent.getPaddingTop();
            end = parent.getHeight() - parent.getPaddingBottom();
        }
        int itemCount = parent.getAdapter().getItemCount();
        int dataStartPosition = mHeaderCount;
        int dataEndPosition = itemCount;


        for (int i = 0; i < itemCount; i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);

            if (position >= dataStartPosition && position < dataEndPosition - mFooterCount - 1) {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                if (orientation == RecyclerView.VERTICAL) {
                    int top = child.getBottom() + params.bottomMargin;
                    int bottom = top + mHeight;
                    mColorDrawable.setBounds(start, top, end, bottom);
                    mColorDrawable.draw(c);
                } else {
                    int left = child.getRight() + params.rightMargin;
                    int right = left + mHeight;
                    mColorDrawable.setBounds(left, start, right, end);
                    mColorDrawable.draw(c);
                }
            }
        }
    }
}
