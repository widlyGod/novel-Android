package com.novel.cn.view.wight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.novel.cn.R;
import com.novel.cn.model.entity.BookDetailBean;

import java.util.List;

/**书评
 * Created by jackieli on 2019/1/18.
 */

public class BookReviewLayout extends LinearLayout {


    private BookReviewItem item1;
    private BookReviewItem item2;
    private BookReviewItem item3;
    private TextView tv_allpl;

    public BookReviewLayout(Context context) {
        super(context);
        init(context);
    }

    public BookReviewLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);
        inflate(context, R.layout.layout_bookreview, this);

        item1=findViewById(R.id.item_BookReview1);
        item2=findViewById(R.id.item_BookReview2);
        item3=findViewById(R.id.item_BookReview3);
        tv_allpl=findViewById(R.id.tv_allpl);

    }


    public void setCommentLayout(BookDetailBean.DataBean.CommentBean bean) {
        List<Object> list=bean.getComments();

        tv_allpl.setText("查看全部评论 ("+bean.getTotalCount()+"条)");


        item1.setBookReviewItem(list.size()>=1 ?list.get(0):null);
        item2.setBookReviewItem(list.size()>=2 ?list.get(1):null);
        item3.setBookReviewItem(list.size()>=3 ?list.get(2):null);
    }

}
