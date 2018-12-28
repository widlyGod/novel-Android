package com.novel.cn.view.wight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.novel.cn.R;
import com.novel.cn.model.entity.HomeReturnBean;

import java.util.List;

/**
 * Created by jackieli on 2018/12/27.
 */

public class HomeBookPanel extends LinearLayout{


    private TextView tv_specialArea, tv_anotherBook;

    private int type=0;

    public HomeBookPanel(Context context) {
        super(context);
        init(context);
    }

    public HomeBookPanel(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setOrientation(VERTICAL);

        inflate(context, R.layout.homebookpanel, this);

        tv_specialArea = (TextView) findViewById(R.id.tv_specialArea);
        tv_anotherBook = (TextView) findViewById(R.id.tv_anotherBook);

    }


    public void setType(int type1){
        type=type1;
    }


    public void setRecommend(HomeReturnBean.DataBean recommend) {
        switch (type){
            case 0:{
                tv_specialArea.setText("主编推荐");
                List<HomeReturnBean.DataBean.EditorRecommendBean>etList= recommend.getEditorRecommend();
                EditorRecommendLayout layout1 = new EditorRecommendLayout(getContext());
                layout1.setEditorRecommendList(etList);
                addView(layout1);
                tv_anotherBook.setVisibility(VISIBLE);
            }break;
            case 1:{
                tv_specialArea.setText("大神专区");
                List<HomeReturnBean.DataBean.BestAuthorZoneBean>etList= recommend.getBestAuthorZone();
                EditorFourRecommendLayout layout1 = new EditorFourRecommendLayout(getContext());
                layout1.setEditorRecommendList(etList);
                addView(layout1);
                EditorFourRecommendLayout layout2 = new EditorFourRecommendLayout(getContext());
                layout2.setEditorRecommendList(etList);
                addView(layout2);
                tv_anotherBook.setVisibility(VISIBLE);
            }break;
            case 2:{
                tv_specialArea.setText("免费推荐");
                List<HomeReturnBean.DataBean.FreeRecommendBean>etList= recommend.getFreeRecommend();
                FreeBookLayout layout1 = new FreeBookLayout(getContext());
                layout1.setFreeBookLayout(etList);
                addView(layout1);
            }break;
            case 3:{
                tv_specialArea.setText("精品推荐");
                List<HomeReturnBean.DataBean.FreeRecommendBean>etList= recommend.getFreeRecommend();
                FreeBookLayout layout1 = new FreeBookLayout(getContext());
                layout1.setFreeBookLayout(etList);
                addView(layout1);
            }break;
        }


    }



}
