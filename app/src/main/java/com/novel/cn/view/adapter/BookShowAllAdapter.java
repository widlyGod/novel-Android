package com.novel.cn.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.novel.cn.R;
import com.novel.cn.interfaceFolder.ItemClickListener;
import com.novel.cn.model.entity.BookShelfAllBean;
import com.novel.cn.model.entity.BookShowBean;
import com.novel.cn.view.wight.StateButton;

import java.util.List;

/**书库
 * Created by jackieli on 2019/1/16.
 */

public class BookShowAllAdapter extends BaseQuickAdapter<BookShelfAllBean.DataBean.ChildrenBean, BaseViewHolder> {

    private ItemClickListener listener;
    private Context context;

    public BookShowAllAdapter(@LayoutRes int layoutResId, @Nullable List<BookShelfAllBean.DataBean.ChildrenBean> data,
                           ItemClickListener partListener, Context context) {
        super(layoutResId, data);
        listener = partListener;
        this.context = context;
    }



    @Override
    protected void convert(final BaseViewHolder helper, final BookShelfAllBean.DataBean.ChildrenBean item) {

        ImageView iv_book = helper.getView(R.id.iv_book);

        Glide.with(context)
                .load(item.getImgUrl())
                .into(iv_book);

        TextView tv_novelTitle = helper.getView(R.id.tv_novelTitle);
        tv_novelTitle.setText(item.getTypeName());

        TextView tv_bookNum = helper.getView(R.id.tv_bookNum);
        tv_bookNum.setText(item.getNovelNum()+"本");
    }


}
