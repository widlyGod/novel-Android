package com.novel.cn.view.wight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.novel.cn.R;
import com.novel.cn.model.entity.BookDetailBean;

/**单条粉丝动态
 * Created by jackieli on 2019/1/18.
 */

public class FansDynamicItem extends LinearLayout {

    private TextView tv_name,tv_dongzuo,tv_ps;

    private ImageView fsdt_yp;

    private Context contextall;

    public FansDynamicItem(Context context) {
        super(context);
        init(context);
    }

    public FansDynamicItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        inflate(context, R.layout.item_fansdynamic, this);
        contextall=context;

        tv_name=findViewById(R.id.tv_name);
        tv_dongzuo=findViewById(R.id.tv_dongzuo);
        tv_ps=findViewById(R.id.tv_ps);
        fsdt_yp=findViewById(R.id.iv_fsdtyp);
    }



    public void seGiftsItem(final BookDetailBean.DataBean.FansRewardsBean bean) {
        tv_name.setText(bean.getFansName());
        //根据rewardType显示动作，显示礼物类型，显示imageview
        tv_dongzuo.setText("投了");

        tv_ps.setText(bean.getRewardCount()+bean.getRewardType());

//        fsdt_yp.setImageResource();
    }

}
