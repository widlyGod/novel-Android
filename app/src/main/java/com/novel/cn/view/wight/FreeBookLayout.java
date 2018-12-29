package com.novel.cn.view.wight;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.novel.cn.R;
import com.novel.cn.model.entity.HomeReturnBean;
import java.util.List;

/**主编推荐
 * Created by jackieli on 2018/12/28.
 */

public class FreeBookLayout extends LinearLayout {

    private FreeBookItem item0;
    private FreeBookItem item1;


    public FreeBookLayout(Context context) {
        super(context);
        init(context);
    }

    public FreeBookLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {

        //把指定的布局作为当前控件的视图效果
        inflate(context, R.layout.layout_freerec, this);
        item0 = (FreeBookItem) findViewById(R.id.freebookitem1);
        item1 = (FreeBookItem) findViewById(R.id.freebookitem2);

    }



    /**
     * 显示数据
     *
     * @param list
     */
    public void setFreeBookLayout(List<HomeReturnBean.DataBean.FreeRecommendBean> list) {
        item0.setFreeBookItem(list.size()>=1 ?list.get(0):null);
        item1.setFreeBookItem(list.size()>=2 ?list.get(1):null);
    }

    public void setBestNovelLayout(List<HomeReturnBean.DataBean.BestNovelRecommendBean> list) {
        item0.setBestNovelItem(list.size()>=1 ?list.get(0):null);
        item1.setBestNovelItem(list.size()>=2 ?list.get(1):null);
    }

}
