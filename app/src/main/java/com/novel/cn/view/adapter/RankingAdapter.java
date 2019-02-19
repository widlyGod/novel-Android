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

/**排行
 * Created by jackieli on 2019/1/15.
 */

public class RankingAdapter  extends BaseQuickAdapter<RankingBean.DataBean, BaseViewHolder> {

    private ItemClickListener listener;
    private Context context;
    private int type=0;

    //type
    public RankingAdapter(int layoutResId, List<RankingBean.DataBean> data,
                          ItemClickListener partListener, Context context,int typex) {
        super(layoutResId, data);
        listener = partListener;
        this.context = context;
        type=typex;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final RankingBean.DataBean item) {

        final TextView textView = helper.getView(R.id.tv_text);
        final TextView orgerView = helper.getView(R.id.view_kuai);
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

                if(item.isClck()==false){
                    item.setClck(true);
                    List<RankingBean.DataBean> data=RankingAdapter.this.getData();
                    for(int i=0;i<data.size();i++){
                        if(i!=helper.getAdapterPosition()){
                            data.get(i).setClck(false);
                        }
                    }
                    listener.iteamClickCallback(0,item.getCode(),"","");
                }

            }
        });

    }


}
