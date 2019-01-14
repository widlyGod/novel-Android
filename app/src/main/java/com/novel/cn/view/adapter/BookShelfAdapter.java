package com.novel.cn.view.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
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
        tv_reUpda.setText("最近更新:"+item.getNewChapter()+"  "+item.getNewChapterTitle());


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
                ToastUtils.showShortToast("继续阅读");
                listener.iteamClickCallback(0,item.getNovelId(),"");
            }
        });

        //取消操作
        btn_qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShortToast("取消操作");
                listener.iteamClickCallback(1,item.getNovelId(),"");
            }
        });

    }


}
