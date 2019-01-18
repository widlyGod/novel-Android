package com.novel.cn.view.wight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.novel.cn.R;
import com.novel.cn.model.entity.BookDetailBean;
import com.novel.cn.model.entity.HomeReturnBean;

import java.util.List;

/**粉丝动态item
 * Created by jackieli on 2019/1/18.
 */

public class FansDynamicLayout extends LinearLayout {

    private FansDynamicItem item1;
    private FansDynamicItem item2;
    private FansDynamicItem item3;
    private FansDynamicItem item4;
    private FansDynamicItem item5;

    public FansDynamicLayout(Context context) {
        super(context);
        init(context);
    }

    public FansDynamicLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        setOrientation(VERTICAL);
        inflate(context, R.layout.layout_fansdynamic, this);

        item1=findViewById(R.id.item_fansdy1);
        item2=findViewById(R.id.item_fansdy2);
        item3=findViewById(R.id.item_fansdy3);
        item4=findViewById(R.id.item_fansdy4);
        item5=findViewById(R.id.item_fansdy5);
    }



    public void setGiftsLayout(List<BookDetailBean.DataBean.FansRewardsBean> list) {
        item1.seGiftsItem(list.size()>=1 ?list.get(0):null);
        item2.seGiftsItem(list.size()>=2 ?list.get(1):null);
        item3.seGiftsItem(list.size()>=3 ?list.get(2):null);
        item4.seGiftsItem(list.size()>=4 ?list.get(3):null);
        item5.seGiftsItem(list.size()>=5 ?list.get(4):null);
    }



}
