package com.novel.cn.view.wight;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.novel.cn.R;
import com.novel.cn.model.api.ApiClient;
import com.novel.cn.model.api.BaseSubscriber;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BookShelfBean;
import com.novel.cn.model.entity.HomeReturnBean;
import com.novel.cn.ui.home.activity.BookDetailsActivity;
import com.novel.cn.util.JsonUtils;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.ToastUtils;

import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jackieli on 2018/12/28.
 */

public class FreeBookItem extends RelativeLayout {

    private RelativeLayout relativeLayout;
    private ImageView iv_book;
    private TextView tv_novelTitle;
    private Context contextall;
    private TextView tv_novelAuthor;
    private Button btn_add;
    private TextView tv_type;
    private TextView tv_jieshao;

    public FreeBookItem(Context context) {
        super(context);

        init(context);
    }

    public FreeBookItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.item_freerec, this);
        contextall=context;
        relativeLayout=findViewById(R.id.rl_free);
        iv_book = (ImageView) findViewById(R.id.iv_book);
        tv_novelTitle = (TextView) findViewById(R.id.tv_novelTitle);
        tv_novelAuthor = (TextView) findViewById(R.id.tv_novelTitle);
        btn_add = (Button) findViewById(R.id.btn_add);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_jieshao = (TextView) findViewById(R.id.tv_jieshao);

//        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
//        int paddingSum = 60;//60dp!= 60px
//        itemSize = (int)(widthPixels - DeviceUtil.dpToPx(getContext(), paddingSum)) / 3;
//        //动态更改控件的大小
//        RelativeLayout.LayoutParams params = (LayoutParams) ivBig.getLayoutParams();
//        params.width = itemSize;
//        params.height = itemSize;
//        ivBig.setLayoutParams(params);

    }

    //主编推荐
    public void setFreeBookItem(final HomeReturnBean.DataBean.FreeRecommendBean bean){
        if(bean!=null){
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(contextall, BookDetailsActivity.class);
                    intent.putExtra("id",bean.getNovelId());
                    contextall.startActivity(intent);
                }
            });

            Glide.with(contextall)
                    .load(bean.getNovelPhoto())
                    .into(iv_book);

            tv_novelTitle.setText(bean.getNovelTitle());
            tv_novelAuthor.setText(bean.getNovelAuthor());
            tv_type.setText(bean.getNovelAuthor());
            btn_add.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestNetWork(bean.getNovelId(),"","");
                }
            });
            tv_jieshao.setText(bean.getNovelIntro());

            relativeLayout.setVisibility(VISIBLE);
        }else{
            relativeLayout.setVisibility(INVISIBLE);
        }
    }


    //最佳推荐
    public void setBestNovelItem(final HomeReturnBean.DataBean.BestNovelRecommendBean bean){
        if(bean!=null){
            setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(contextall, BookDetailsActivity.class);
                    intent.putExtra("id",bean.getNovelId());
                    contextall.startActivity(intent);
                }
            });

            Glide.with(contextall)
                    .load(bean.getNovelPhoto())
                    .into(iv_book);

            tv_novelTitle.setText(bean.getNovelTitle());
            tv_novelAuthor.setText(bean.getNovelAuthor());
            tv_type.setText(bean.getNovelAuthor());
            btn_add.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestNetWork(bean.getNovelId(),"","");
                }
            });
            tv_jieshao.setText(bean.getNovelIntro());

            relativeLayout.setVisibility(VISIBLE);
        }else{
            relativeLayout.setVisibility(INVISIBLE);
        }
    }


    public void requestNetWork(String novel_id,String volumeId,String chapterId){
        JsonUtils jsonUtils = new JsonUtils();
        jsonUtils.addField("novel_id", novel_id);
        jsonUtils.addField("volumeId", volumeId);
        jsonUtils.addField("chapterId", chapterId);
        String bodyString = jsonUtils.build().toString();
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),bodyString);
        ApiClient.service.saveCollection(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<BaseBean>() {

                    @Override
                    protected void noConnectInternet() {
                        ToastUtils.showShortToast("网络错误，请检查网络");
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("tag","saveCollection错误="+e.getMessage());
                    }

                    @Override
                    public void onNext(BaseBean bean) {
                        ToastUtils.showShortToast(bean.getMessage());
                    }
                });

    }


}
