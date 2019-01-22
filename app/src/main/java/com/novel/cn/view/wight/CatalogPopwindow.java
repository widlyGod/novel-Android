package com.novel.cn.view.wight;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.novel.cn.R;
import com.novel.cn.model.entity.VolumesBean;
import com.novel.cn.persenter.PresenterClass.CatalogPresenter;
import com.novel.cn.ui.home.activity.CatalogActivity;
import com.novel.cn.view.adapter.PopCatalogAdapter;
import com.novel.cn.view.adapter.PopWindowAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieli on 2019/1/22.
 */

public class CatalogPopwindow extends PopupWindow {

    Context context;
    List<String>list=new ArrayList<>();



    public CatalogPopwindow(Context context) {
        super(context);
        this.context=context;
        inintView();
    }

    public CatalogPopwindow(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        inintView();
    }

    public PopCatalogAdapter popAdapter;

    //初始化布局
    private void inintView() {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_catalogall, null);
        setContentView(view);

        setTouchable(true);
        setFocusable(true);  //原来true
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.pop_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        popAdapter = new PopCatalogAdapter(context, list);
        recyclerView.setAdapter(popAdapter);

    }


    public  void setList(List<String>listx){
        list.clear();
        list.addAll(listx);
        popAdapter.notifyDataSetChanged();
    }


}
