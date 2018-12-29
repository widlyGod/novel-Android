package com.novel.cn.view.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.novel.cn.R;
import com.novel.cn.interfaceFolder.ItemClickListener;
import com.novel.cn.model.entity.HomeReturnBean;

import java.util.List;

/**
 * 首页最近更新
 */

public class RecentUpdatesAdapter extends BaseQuickAdapter<HomeReturnBean.DataBean.RecentUpdateBean, BaseViewHolder> {

    private ItemClickListener listener;
    private Context context;

    public RecentUpdatesAdapter(@LayoutRes int layoutResId, @Nullable List<HomeReturnBean.DataBean.RecentUpdateBean> data,
                                ItemClickListener partListener, Context context) {
        super(layoutResId, data);
        listener = partListener;
        this.context = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final HomeReturnBean.DataBean.RecentUpdateBean item) {

        TextView textView = helper.getView(R.id.tv_recentupdates);
        textView.setText("【" + item.getNovelType() + "】" + item.getNovelTitle()+":" + item.getChapterTitle());
        //设置位置，无网络时候在修改笔记时才会根据index动态修改列表
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.iteamClickCallback(0,item.getNovelId(),"");
            }
        });
    }


}
