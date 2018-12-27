package com.novel.cn.view.wight;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.novel.cn.R;
import com.youth.banner.loader.ImageLoader;

/**少了一个图片错误显示
 * Created by Administrator on 2016/10/25 0025.
 */

public class GlideImageLoader implements ImageLoader {

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }

}
