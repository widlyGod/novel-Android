package com.novel.cn.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.novel.cn.R;
import com.novel.cn.interfaceFolder.ItemClickListener;
import com.novel.cn.model.entity.BookShelfAllBean;
import com.novel.cn.model.entity.RankingBean;

import java.util.List;

/**书库
 * Created by jackieli on 2019/1/15.
 */

public class RankingAllAdapter extends BaseQuickAdapter<BookShelfAllBean.DataBean, BaseViewHolder> {

    private ItemClickListener listener;
    private Context context;
    private int type=0;

    //type
    public RankingAllAdapter(int layoutResId, List<BookShelfAllBean.DataBean> data,
                             ItemClickListener partListener, Context context, int typex) {
        super(layoutResId, data);
        listener = partListener;
        this.context = context;
        type=typex;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final BookShelfAllBean.DataBean item) {

        final TextView textView = helper.getView(R.id.tv_text);
        final TextView orgerView = helper.getView(R.id.view_kuai);
        textView.setText(item.getTypeName());

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
                    List<BookShelfAllBean.DataBean> data=RankingAllAdapter.this.getData();
                    for(int i=0;i<data.size();i++){
                        if(i!=helper.getAdapterPosition()){
                            data.get(i).setClck(false);
                        }
                    }
                    listener.iteamClickCallback(type,helper.getAdapterPosition(),"");
                }

            }
        });

    }


}
