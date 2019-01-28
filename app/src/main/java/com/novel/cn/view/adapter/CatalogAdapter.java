package com.novel.cn.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.novel.cn.R;
import com.novel.cn.interfaceFolder.ItemClickListener;
import com.novel.cn.model.entity.BookShelfBean;
import com.novel.cn.model.entity.ChapterBean;
import com.novel.cn.util.NumberUtils;
import com.novel.cn.view.wight.StateButton;

import java.util.List;

/**
 * read_mulu
 */

public class CatalogAdapter extends BaseQuickAdapter<ChapterBean.DataBean.ListBean, BaseViewHolder> {

    private ItemClickListener listener;
    private Context context;


    public CatalogAdapter(@LayoutRes int layoutResId, @Nullable List<ChapterBean.DataBean.ListBean> data,
                          ItemClickListener partListener, Context context) {
        super(layoutResId, data);
        listener = partListener;
        this.context = context;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final ChapterBean.DataBean.ListBean item) {

        TextView tv_zjbt = helper.getView(R.id.tv_zjbt);

        tv_zjbt.setText("第" + item.getChapter() + "章 " + item.getTitle());

        ImageView iv_isFree = helper.getView(R.id.iv_isFree);
        ImageView iv_isReading = helper.getView(R.id.iv_isReading);
        ImageView iv_isLocked = helper.getView(R.id.iv_isLocked);
        if (item.getIsFree().equals("1")) {
            iv_isFree.setVisibility(View.VISIBLE);
        } else {
            iv_isFree.setVisibility(View.GONE);
        }
        if (item.getIsReading().equals("1")) {
            iv_isReading.setVisibility(View.VISIBLE);
        } else {
            iv_isReading.setVisibility(View.GONE);
        }
        if (item.getIsLocked().equals("1")) {
            iv_isLocked.setVisibility(View.VISIBLE);
        } else {
            iv_isLocked.setVisibility(View.GONE);
        }

        RelativeLayout rl_all = helper.getView(R.id.rl_all);

        //点击进入下一级界面
        rl_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.iteamClickCallback(0, item.getId(), "");
            }
        });

    }


}
