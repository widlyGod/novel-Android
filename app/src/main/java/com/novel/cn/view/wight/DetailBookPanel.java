package com.novel.cn.view.wight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.novel.cn.R;
import com.novel.cn.model.entity.BookDetailBean;
import com.novel.cn.model.entity.HomeReturnBean;

import java.util.List;

/**
 * Created by jackieli on 2019/1/18.
 */

public class DetailBookPanel extends LinearLayout implements View.OnClickListener {

    private TextView tv_specialArea, tv_anotherBook;


    public DetailBookPanel(Context context) {
        super(context);
        init(context);
    }

    public DetailBookPanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        setOrientation(VERTICAL);
        inflate(context, R.layout.homebookpanel, this);

        tv_specialArea = (TextView) findViewById(R.id.tv_specialArea);
        tv_anotherBook = (TextView) findViewById(R.id.tv_anotherBook);

        tv_anotherBook.setOnClickListener(this);
    }

    private int type = 0;

    public void setType(int type1) {
        type = type1;
    }


    private EditorRecommendLayout layout1;
    private boolean isAnotherBook = false;
    private BookDetailBean beanx;

    //设置书籍数据
    public void setBookDetail(BookDetailBean bean) {
        beanx=bean;
        switch (type){
            case 0: {//粉丝动态
                tv_specialArea.setText("粉丝动态");

                FansDynamicLayout layout=new FansDynamicLayout(getContext());
                layout.setGiftsLayout(bean.getData().getFansRewards());
                addView(layout);
            }break;
            case 1: {//书评
                tv_specialArea.setText("书评");

                BookReviewLayout layout=new BookReviewLayout(getContext());
                layout.setCommentLayout(bean.getData().getComment());
                addView(layout);
            }break;
            case 2:{//主编推荐
                List<HomeReturnBean.DataBean.EditorRecommendBean> etList = bean.getData().getEditorRecommend();
                if (etList.size() >= 3) {
                    layout1 = new EditorRecommendLayout(getContext());
                    layout1.setEditorRecommendList(etList);
                    isAnotherBook = true;
                } else {
                    layout1 = new EditorRecommendLayout(getContext());
                    layout1.setEditorRecommendList(etList.subList(0, etList.size()));
                }
                addView(layout1);
                tv_anotherBook.setVisibility(VISIBLE);

            }break;
        }
    }


    boolean isChange = false;

    @Override
    public void onClick(View view) {
        //主编推荐
        if (isAnotherBook) {
            List<HomeReturnBean.DataBean.EditorRecommendBean> etList =  beanx.getData().getEditorRecommend();
            //如果点击过换一批了，换回原来的
            layout1.setEditorRecommendList(isChange == true ? etList : etList.subList(3, etList.size()));
            isChange = !isChange;
        }
    }

}
