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
import com.novel.cn.model.entity.BookShelfBean;
import com.novel.cn.model.entity.BookShowBean;
import com.novel.cn.util.NumberUtils;
import com.novel.cn.util.ToastUtils;
import com.novel.cn.view.wight.StateButton;

import java.util.List;

/**排行
 * Created by jackieli on 2019/1/16.
 */

public class BookShowAdapter extends BaseQuickAdapter<BookShowBean.DataBean.WEEKBean, BaseViewHolder> {

    private ItemClickListener listener;
    private Context context;

    public BookShowAdapter(@LayoutRes int layoutResId, @Nullable List<BookShowBean.DataBean.WEEKBean> data,
                            ItemClickListener partListener, Context context) {
        super(layoutResId, data);
        listener = partListener;
        this.context = context;
    }



    @Override
    protected void convert(final BaseViewHolder helper, final BookShowBean.DataBean.WEEKBean item) {

        ImageView iv_book = helper.getView(R.id.iv_book);
        Glide.with(context)
                .load(item.getNovelPhoto())
                .into(iv_book);

        TextView tv_num = helper.getView(R.id.tv_num);
        int postion=helper.getAdapterPosition();
        if(postion==0){
            tv_num.setVisibility(View.VISIBLE);
            tv_num.setText("NO1");
            tv_num.setBackgroundColor(Color.parseColor("#ef3737"));
        }else if(postion==1){
            tv_num.setVisibility(View.VISIBLE);
            tv_num.setText("NO2");
            tv_num.setBackgroundColor(Color.parseColor("#ff6c00"));
        }else if(postion==2){
            tv_num.setVisibility(View.VISIBLE);
            tv_num.setText("NO3");
            tv_num.setBackgroundColor(Color.parseColor("#fdb900"));
        }else{
            tv_num.setVisibility(View.GONE);
        }

        TextView tv_novelTitle = helper.getView(R.id.tv_novelTitle);
        tv_novelTitle.setText(item.getNovelTitle());

        TextView tv_novelAuthor = helper.getView(R.id.tv_novelAuthor);
        String writer=item.getWriter();
        SpannableString spannableString = new SpannableString(writer+" "  +"著");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#757575"));
        spannableString.setSpan(colorSpan, writer.length(), spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv_novelAuthor.setText(spannableString);

        TextView tv_type = helper.getView(R.id.tv_type);
        tv_type.setText(item.getNovelType());

        StateButton stateButton=helper.getView(R.id.tv_lianzai);
        String isover=item.getIsOver();
        if(isover.equals("0")){
            stateButton.setText("已完结");
        }else if(isover.equals("1")){
            stateButton.setText("连载中");
        }else if(isover.equals("3")){
            stateButton.setText("已停更");
        }

        Button btn_add=helper.getView(R.id.btn_add);
        if(item.isCollection()!=null && item.isCollection().equals("true")){
            btn_add.setClickable(false);
            btn_add.setText("已在书架");
            btn_add.setBackgroundColor(Color.parseColor("#03ad9b"));
        }else{
            btn_add.setClickable(true);
            btn_add.setText("加入书架");
            btn_add.setBackgroundColor(Color.parseColor("#01c78a"));
        }


        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.iteamClickCallback(0,item.getNovelId(),"","");
            }
        });

        TextView tv_jieshao = helper.getView(R.id.tv_jieshao);
        tv_jieshao.setText("简介:"+item.getNovelDescribe());

    }


}
