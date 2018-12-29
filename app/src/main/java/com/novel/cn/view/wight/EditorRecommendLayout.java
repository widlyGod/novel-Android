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

public class EditorRecommendLayout extends LinearLayout {

    private EditorRcItem item0;
    private EditorRcItem item1;
    private EditorRcItem item2;

    public EditorRecommendLayout(Context context) {
        super(context);
        init(context);
    }

    public EditorRecommendLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        //把指定的布局作为当前控件的视图效果
        inflate(context, R.layout.layout_etrct, this);
        item0 = (EditorRcItem) findViewById(R.id.editorrcitem0);
        item1 = (EditorRcItem) findViewById(R.id.editorrcitem1);
        item2 = (EditorRcItem) findViewById(R.id.editorrcitem2);
    }



    /**
     * 显示数据
     *
     * @param list
     */
    public void setEditorRecommendList(List<HomeReturnBean.DataBean.EditorRecommendBean> list) {
        item0.setEditorRcItem(list.size()>=1 ?list.get(0):null);
        item1.setEditorRcItem(list.size()>=2 ?list.get(1):null);
        item2.setEditorRcItem(list.size()>=3 ?list.get(2):null);
    }

}
