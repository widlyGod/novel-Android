package com.novel.cn.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.novel.cn.R;
import com.novel.cn.interfaceFolder.ItemClickListener;
import com.novel.cn.model.entity.RankingBean;

import java.util.List;

/**
 * Created by jackieli on 2019/1/15.
 */

public class RankingAdapter  extends BaseQuickAdapter<RankingBean.DataBean, BaseViewHolder> {

    private ItemClickListener listener;
    private Context context;

    public RankingAdapter(int layoutResId, List<RankingBean.DataBean> data,
                          ItemClickListener partListener, Context context) {
        super(layoutResId, data);
        listener = partListener;
        this.context = context;
    }


    @Override
    protected void convert(BaseViewHolder helper, final RankingBean.DataBean item) {
        final TextView textView = helper.getView(R.id.tv_text);
        final View orgerView = helper.getView(R.id.view_kuai);
        textView.setText(item.getName());


        if(item.isClck()){
            orgerView.setVisibility(View.VISIBLE);
            textView.setTextColor(Color.parseColor("#ff7d00"));
            textView.setBackgroundColor(Color.parseColor("#ffffff"));
        }else{
            orgerView.setVisibility(View.GONE);
            textView.setTextColor(Color.parseColor("#333333"));
            textView.setBackgroundColor(Color.parseColor("#e3e7ea"));
        }



        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setClck(true);
                listener.iteamClickCallback(0,item.getCode(),"");
                orgerView.setVisibility(View.VISIBLE);
                textView.setTextColor(Color.parseColor("#ff7d00"));
                textView.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        });

    }


}
