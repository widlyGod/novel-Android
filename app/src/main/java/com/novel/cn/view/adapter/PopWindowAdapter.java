package com.novel.cn.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.novel.cn.R;
import com.novel.cn.model.entity.VolumesBean;
import com.novel.cn.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieli on 2019/1/22.
 */

public class PopWindowAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private MyItemClickListener itemClickListener;
    private List<VolumesBean.DataBean> list;
    private String id, name, patientId, cardNo;

    public interface MyItemClickListener{
        void onClick(View view);
    }

    public PopWindowAdapter(Context context, MyItemClickListener itemClickListener, List<VolumesBean.DataBean>list) {
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.list = list;
    }


    public void setList(List<VolumesBean.DataBean>listx){
        list.addAll(listx);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pop_catalog, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onClick(view);
            }
        });
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).itemView.setTag(position);
            ((ItemViewHolder) holder).textView.setText("第"+list.get(position).getVolume()+"卷 "+list.get(position).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        LogUtil.e("size="+(list == null ? 0 : list.size()));
        return list == null ? 0 : list.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_js);
        }
    }
}

