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

import java.util.List;

/**
 * Created by jackieli on 2019/1/22.
 */

public class PopCatalogAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private MyItemClickListener itemClickListener;
    private List<String> list;

    public interface MyItemClickListener{
        void onClick(View view);
    }

    public PopCatalogAdapter(Context context, List<String>list) {
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.list = list;
    }


    public void setItemClickListener(MyItemClickListener itemClickListenerx){
        itemClickListener=itemClickListenerx;

    }

    public void setList(List<String>listx){
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
            ((ItemViewHolder) holder).textView.setText(list.get(position));
        }
    }

    @Override
    public int getItemCount() {
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

