package com.novel.cn.view.wight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.novel.cn.R;
import com.novel.cn.model.entity.BookDetailBean;
import com.novel.cn.model.entity.HomeReturnBean;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.ToastUtils;

/**书评界面
 * Created by jackieli on 2019/1/18.
 */

public class BookReviewItem  extends RelativeLayout {

    private ImageView iv_tx;

    private TextView tv_name,tv_pl,tv_cf,tv_dz,tv_hf;

    private StateButton stateButton;

    public BookReviewItem(Context context) {
        super(context);
        init(context);
    }

    public BookReviewItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.item_bookreview, this);

        iv_tx=findViewById(R.id.iv_touxiang);
        tv_name=findViewById(R.id.tv_name);
        tv_pl=findViewById(R.id.tv_pl);
        tv_cf=findViewById(R.id.tv_cf);
        tv_dz=findViewById(R.id.tv_dz);
        tv_hf=findViewById(R.id.tv_hf);

    }


    public void setBookReviewItem(final Object bean){
        if(bean!=null){

            //http://59.110.124.41:80/novel_a/novel/fd1dc45252b6493bb9be18133be42164/novel_img_1543892370367_0.jpg
//            Glide.with(contextall)
//                    .load(bean.getNovelPhoto())
//                    .into(iv_book);
//            LogUtil.e("图片加载:"+bean.getNovelPhoto());
//            tv_bookName.setText(bean.getNovelTitle());

        }else{


        }

    }
}
