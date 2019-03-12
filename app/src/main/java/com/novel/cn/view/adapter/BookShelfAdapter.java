package com.novel.cn.view.adapter;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.novel.cn.R;
import com.novel.cn.interfaceFolder.ItemClickListener;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BookShelfBean;
import com.novel.cn.util.NumberUtils;

import java.util.List;

/**
 * Created by jackieli on 2019/3/12.
 */

public class BookShelfAdapter extends BaseQuickAdapter<BookShelfBean.DataBean.BookBean,BaseViewHolder> {

    private ItemClickListener listener;
    private Context context;

    public BookShelfAdapter(int layoutResId, List<BookShelfBean.DataBean.BookBean> data,ItemClickListener listener1,Context context1) {
        super(layoutResId, data);
        listener=listener1;
        context=context1;
    }

    @Override
    protected void convert(BaseViewHolder helper, BookShelfBean.DataBean.BookBean item) {
        ImageView iv_bookshelf=helper.getView(R.id.iv_bookshelf);
        TextView tv_bookName=helper.getView(R.id.tv_bookName);
        TextView tv_updateCa=helper.getView(R.id.tv_updateCa);

        RequestOptions options = new RequestOptions();
        options.placeholder(R.drawable.book_placeholder)
                .error(R.drawable.book_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(context)
                .load(item.getNovelPoto())
                .apply(options)
                .into(iv_bookshelf);

        tv_bookName.setText(item.getNovelTitle());

        String num= "最近更新:第"+ NumberUtils.numberKArab2CN(item.getNewChapter())+"章  ";
        tv_updateCa.setText(num);

        TextView tv_noreadNum=helper.getView(R.id.tv_noreadNum);
        int noreadNum=item.getNoReadNum();
        if(noreadNum>=99){
            tv_noreadNum.setText(item.getNoReadNum()+"+");
        }else{
            tv_noreadNum.setText(item.getNoReadNum()+"");
        }

    }


}
