package com.novel.cn.view.wight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.novel.cn.R;
import com.novel.cn.model.entity.HomeReturnBean;
import com.novel.cn.util.ToastUtils;

/**
 * Created by jackieli on 2018/12/28.
 */

public class EditorRcItem extends RelativeLayout {

    private ImageView iv_book;
    private TextView tv_bookName;
    private Context contextall;

    public EditorRcItem(Context context) {
        super(context);

        init(context);
    }

    public EditorRcItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.item_editor, this);
        contextall=context;
        iv_book = (ImageView) findViewById(R.id.iv_book);
        tv_bookName = (TextView) findViewById(R.id.tv_bookName);
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;

//        int paddingSum = 60;//60dp!= 60px
//        itemSize = (int)(widthPixels - DeviceUtil.dpToPx(getContext(), paddingSum)) / 3;
//        //动态更改控件的大小
//        RelativeLayout.LayoutParams params = (LayoutParams) ivBig.getLayoutParams();
//        params.width = itemSize;
//        params.height = itemSize;
//        ivBig.setLayoutParams(params);
    }

    //主编推荐
    public void setEditorRcItem(final HomeReturnBean.DataBean.EditorRecommendBean bean){
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(contextall,);
//                intent.putExtra("id",bean.getNovelId());
//                contextall.startActivity(intent);
                ToastUtils.showShortToast("跳转书籍详情");
            }
        });

        //http://59.110.124.41:80/novel_a/novel/fd1dc45252b6493bb9be18133be42164/novel_img_1543892370367_0.jpg
        Glide.with(contextall)
                .load(bean.getNovelPhoto())
                .into(iv_book);

        tv_bookName.setText(bean.getNovelTitle());
    }

    //大神专区
    public void setEditorFourRcItem(final HomeReturnBean.DataBean.BestAuthorZoneBean bean){
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent(contextall,);
//                intent.putExtra("id",bean.getNovelId());
//                contextall.startActivity(intent);
                ToastUtils.showShortToast("跳转书籍详情");
            }
        });

        //http://59.110.124.41:80/novel_a/novel/fd1dc45252b6493bb9be18133be42164/novel_img_1543892370367_0.jpg
        Glide.with(contextall)
                .load(bean.getNovelPhoto())
                .into(iv_book);

        tv_bookName.setText(bean.getNovelTitle());
    }

}
