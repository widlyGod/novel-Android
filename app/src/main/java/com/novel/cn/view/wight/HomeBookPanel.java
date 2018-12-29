package com.novel.cn.view.wight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.novel.cn.R;
import com.novel.cn.model.entity.HomeReturnBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieli on 2018/12/27.
 */

public class HomeBookPanel extends LinearLayout implements View.OnClickListener {


    private TextView tv_specialArea, tv_anotherBook;

    private int type = 0;

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

        tv_anotherBook.setOnClickListener(this);
    }


    public void setType(int type1) {
        type = type1;
    }

    private boolean isAnotherBook = false;
    private HomeReturnBean.DataBean bean;
    private EditorRecommendLayout layout1;
    private EditorFourRecommendLayout layoutFour1;
    private EditorFourRecommendLayout layoutFour2;
    private FreeBookLayout freeBookLayout1;
    private FreeBookLayout freeBookLayout2;

    public void setRecommend(HomeReturnBean.DataBean recommend) {
        bean = recommend;
        switch (type) {
            case 0: {
                tv_specialArea.setText("主编推荐");
                List<HomeReturnBean.DataBean.EditorRecommendBean> etList = recommend.getEditorRecommend();
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
            }
            break;
            case 1: {
                tv_specialArea.setText("大神专区");
                List<HomeReturnBean.DataBean.BestAuthorZoneBean> etList = recommend.getBestAuthorZone();
                layoutFour1 = new EditorFourRecommendLayout(getContext());
                layoutFour2 = new EditorFourRecommendLayout(getContext());
                if (etList.size() <= 4) {
                    layoutFour1.setEditorRecommendList(etList.subList(0, etList.size()));
                    layoutFour2.setEditorRecommendList(new ArrayList<HomeReturnBean.DataBean.BestAuthorZoneBean>());
                } else if (etList.size() <= 8) {
                    layoutFour1.setEditorRecommendList(etList.subList(0, 4));
                    layoutFour2.setEditorRecommendList(etList.subList(4, etList.size()));
                } else {
                    isAnotherBook = true;
                    layoutFour1.setEditorRecommendList(etList.subList(0, 4));
                    layoutFour2.setEditorRecommendList(etList.subList(4, etList.size()));
                }
                addView(layoutFour1);
                addView(layoutFour2);
                tv_anotherBook.setVisibility(VISIBLE);
            }
            break;
            case 2: {
                tv_specialArea.setText("免费推荐");
                List<HomeReturnBean.DataBean.FreeRecommendBean> etList = recommend.getFreeRecommend();
                freeBookLayout1 = new FreeBookLayout(getContext());
                if (etList.size() >= 2) {
                    freeBookLayout1.setFreeBookLayout(etList);
                    isAnotherBook = true;
                } else {
                    freeBookLayout1.setFreeBookLayout(etList.subList(0,2));
                }
                addView(freeBookLayout1);
            }break;
            case 3: {
                tv_specialArea.setText("精品推荐");
                List<HomeReturnBean.DataBean.BestNovelRecommendBean> etList = recommend.getBestNovelRecommend();
                freeBookLayout2 = new FreeBookLayout(getContext());
                if (etList.size() >= 2) {
                    freeBookLayout2.setBestNovelLayout(etList);
                    isAnotherBook = true;
                } else {
                    freeBookLayout2.setBestNovelLayout(etList.subList(0,2));
                }
                addView(freeBookLayout2);
            }break;
        }

    }


    boolean isChange = false;

    @Override
    public void onClick(View view) {
        switch (type) {
            case 0: {
                if (isAnotherBook) {
                    List<HomeReturnBean.DataBean.EditorRecommendBean> etList = bean.getEditorRecommend();
                    //如果点击过换一批了，换回原来的
                    layout1.setEditorRecommendList(isChange == true ? etList : etList.subList(3, etList.size()));
                    isChange = !isChange;
                }
            }
            break;
            case 1: {
                if (isAnotherBook) {//如果当前可换
                    List<HomeReturnBean.DataBean.BestAuthorZoneBean> etList = bean.getBestAuthorZone();
                    if (etList.size() <= 12) {//本数小于12
                        layoutFour1.setEditorRecommendList(isChange == true ?
                                etList.subList(0, 4)
                                :etList.subList(8, etList.size()) );
                        layoutFour2.setEditorRecommendList(isChange == true ?
                                etList.subList(4, etList.size())
                                :new ArrayList<HomeReturnBean.DataBean.BestAuthorZoneBean>());
                        isChange = !isChange;
                    } else {//本书大于12
                        isAnotherBook = true;

                        layoutFour1.setEditorRecommendList(isChange == true ?
                                etList.subList(0, 4)
                                :etList.subList(8, 12) );
                        layoutFour2.setEditorRecommendList(isChange == true ?
                                etList.subList(4, etList.size())
                                :etList.subList(12, etList.size()));
                        isChange = !isChange;
                    }
                }
            }
            break;
        }
    }

    //每隔两秒更换的定时任务
    public void changeFreeBook(){
        if(type==2){//免费推荐
            List<HomeReturnBean.DataBean.FreeRecommendBean> etList = bean.getFreeRecommend();
            //如果点击过换一批了，换回原来的
            freeBookLayout1.setFreeBookLayout(isChange == true ? etList : etList.subList(2, etList.size()));
        }else{//精品推荐
            List<HomeReturnBean.DataBean.BestNovelRecommendBean> etList = bean.getBestNovelRecommend();
            //如果点击过换一批了，换回原来的
            freeBookLayout2.setBestNovelLayout(isChange == true ? etList : etList.subList(2, etList.size()));
        }
        isChange = !isChange;
    }



}
