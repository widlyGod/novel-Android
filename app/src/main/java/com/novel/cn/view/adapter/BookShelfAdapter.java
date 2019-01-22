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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.novel.cn.R;
import com.novel.cn.interfaceFolder.ItemClickListener;
import com.novel.cn.model.entity.BookShelfBean;
import com.novel.cn.model.entity.HomeReturnBean;
import com.novel.cn.util.NumberUtils;
import com.novel.cn.util.ToastUtils;
import com.novel.cn.view.wight.StateButton;

import java.util.List;

/**
 * 我的书架
 */

public class BookShelfAdapter extends BaseQuickAdapter<BookShelfBean.DataBean.BookBean, BaseViewHolder> {

    private ItemClickListener listener;
    private Context context;
    private static int type=0;

    public BookShelfAdapter(@LayoutRes int layoutResId, @Nullable List<BookShelfBean.DataBean.BookBean> data,
                            ItemClickListener partListener, Context context) {
        super(layoutResId, data);
        listener = partListener;
        this.context = context;
    }

    public static void setType(int type1){
        type=type1;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final BookShelfBean.DataBean.BookBean item) {
        ImageView iv_book = helper.getView(R.id.iv_book);
        Glide.with(context)
                .load(item.getNovelPoto())
                .into(iv_book);

        TextView tv_bookTitle = helper.getView(R.id.tv_bookTitle);
        tv_bookTitle.setText(item.getNovelTitle());
        TextView tv_reUpda = helper.getView(R.id.tv_reUpda);


        String num= "最近更新:第"+NumberUtils.numberKArab2CN(item.getNewChapter())+"章  ";
        String chapterTitle=item.getNewChapterTitle();

        SpannableString spannableString = new SpannableString(num  +chapterTitle);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#757575"));
        spannableString.setSpan(colorSpan, num.length(), spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_reUpda.setText(spannableString);



        StateButton btn_jx=helper.getView(R.id.btn_jx);
        StateButton btn_qx=helper.getView(R.id.btn_qx);
        switch (type){
            case 0:
                btn_qx.setText("取消收藏");
                break;
            case 1:
                btn_qx.setText("取消订阅");
                break;
            case 2:
                btn_qx.setText("取消");
                break;
        }

        //继续阅读
        btn_jx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.iteamClickCallback(0,item.getNovelId(),"");
            }
        });

        //取消操作
        btn_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.iteamClickCallback(1,item.getNovelId(),"");
            }
        });


        iv_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.iteamClickCallback(2,item.getNovelId(),"");
            }
        });

        tv_bookTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.iteamClickCallback(2,item.getNovelId(),"");
            }
        });

        tv_reUpda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.iteamClickCallback(2,item.getNovelId(),"");
            }
        });
    }


}
