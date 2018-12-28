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

public class EditorFourRecommendLayout extends LinearLayout {

    private EditorRcItem item0;
    private EditorRcItem item1;
    private EditorRcItem item2;
    private EditorRcItem item3;

    public EditorFourRecommendLayout(Context context) {
        super(context);
        init(context);
    }

    public EditorFourRecommendLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context)
    {
        //把指定的布局作为当前控件的视图效果
        inflate(context, R.layout.layout_etrc_four, this);

        item0 = (EditorRcItem) findViewById(R.id.editorrcitem0);
        item1 = (EditorRcItem) findViewById(R.id.editorrcitem1);
        item2 = (EditorRcItem) findViewById(R.id.editorrcitem2);
        item3 = (EditorRcItem) findViewById(R.id.editorrcitem3);
    }



    /**
     * 显示数据
     *
     * @param list
     */
    public void setEditorRecommendList(List<HomeReturnBean.DataBean.BestAuthorZoneBean> list) {

        item0.setEditorFourRcItem(list.get(0));
        item1.setEditorFourRcItem(list.get(1));
        item2.setEditorFourRcItem(list.get(2));
        item3.setEditorFourRcItem(list.get(3));
    }

}
