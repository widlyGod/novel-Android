package com.novel.cn.ui.home.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.novel.cn.R;
import com.novel.cn.model.api.ApiClient;
import com.novel.cn.model.api.ApiService;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BookDetailBean;
import com.novel.cn.persenter.Contract.BookDeatilContract;
import com.novel.cn.persenter.PresenterClass.BookDetailPresenter;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.ToastUtils;
import com.novel.cn.view.wight.DetailBookPanel;
import com.novel.cn.view.wight.StateButton;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 书籍详情
 * Created by jackieli on 2019/1/17.
 */

public class BookDetailsActivity extends AutoLayoutActivity implements BookDeatilContract.View {


    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.appbar)
    RelativeLayout appbar;
    @Bind(R.id.iv_book)
    ImageView ivBook;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.tv_novelTitle)
    TextView tvNovelTitle;
    @Bind(R.id.btn_share)
    ImageView btnShare;
    @Bind(R.id.tv_novelAuthor)
    TextView tvNovelAuthor;
    @Bind(R.id.rl_zuozhe)
    RelativeLayout rlZuozhe;
    @Bind(R.id.tv_type)
    TextView tvType;
    @Bind(R.id.tv_lianzai)
    StateButton tvLianzai;
    @Bind(R.id.tv_zishu)
    TextView tv_zishu;
    @Bind(R.id.rl_free)
    RelativeLayout rlFree;
    @Bind(R.id.sbtn_jxyd)
    StateButton sbtnJxyd;
    @Bind(R.id.sbtn_jrsj)
    StateButton sbtnJrsj;
    @Bind(R.id.cb_zd)
    CheckBox cbZd;
    @Bind(R.id.iv_zddy)
    ImageView ivZddy;
    @Bind(R.id.tv_detail)
    TextView tvDetail;
    @Bind(R.id.iv_zk)
    TextView ivZk;
    @Bind(R.id.tv_ml)
    TextView tvMl;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.rl_ml)
    RelativeLayout rlMl;
    @Bind(R.id.rb_tjp)
    RadioButton rbTjp;
    @Bind(R.id.tv_zs)
    RadioButton tvZs;
    @Bind(R.id.rg_tjzs)
    RadioGroup rgTjzs;
    @Bind(R.id.iv_queLeft)
    ImageView ivQueLeft;
    @Bind(R.id.tv_bzzs)
    TextView tvBzzs;
    @Bind(R.id.tv_mc)
    TextView tvMc;
    @Bind(R.id.tv_pmhc)
    TextView tvPmhc;
    @Bind(R.id.iv_leftx)
    ImageView ivLeftx;
    @Bind(R.id.btn_sendLeft)
    StateButton btnSendLeft;
    @Bind(R.id.rb_yp)
    RadioButton rbYp;
    @Bind(R.id.rb_ds)
    RadioButton rbDs;
    @Bind(R.id.rg_ypds)
    RadioGroup rgYpds;
    @Bind(R.id.iv_queRight)
    ImageView ivQueRight;
    @Bind(R.id.tv_bzps)
    TextView tvBzps;
    @Bind(R.id.tv_ps)
    TextView tvPs;
    @Bind(R.id.tv_pmright)
    TextView tvPmright;
    @Bind(R.id.iv_rightx)
    ImageView ivRightx;
    @Bind(R.id.btn_sendRight)
    StateButton btnSendRight;
    @Bind(R.id.ll_bookdetail)
    LinearLayout llBookdetail;

    private BookDetailPresenter presenter;
    private String novelId;
    PopupWindow popupWindow;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookdetail);
        ButterKnife.bind(this);


        novelId=getIntent().getStringExtra("id");
        presenter=new BookDetailPresenter();
        presenter.setMvpView(this,"");
        presenter.getOpenNovel(novelId);

        sbtnJrsj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sbtnJrsj.getText().equals("已在书架")){
                    presenter.saveCollection(novelId,"","");
                }
            }
        });

        cbZd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
               // LogUtil.e("请求接口自动订阅");
            }
        });
        ivZddy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        View viewx = LayoutInflater.from(BookDetailsActivity.this).inflate(R.layout.pop_bookdetail, null, false);
        popupWindow = new PopupWindow(viewx, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);  //原来true
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ivZddy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.showAsDropDown(ivZddy, 0, 0);
            }
        });

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.iv_left, R.id.btn_share, R.id.sbtn_jxyd, R.id.sbtn_jrsj, R.id.iv_zddy,
            R.id.iv_zk, R.id.rl_ml, R.id.iv_queLeft, R.id.btn_sendLeft, R.id.iv_queRight, R.id.btn_sendRight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left:{
                finish();
            }break;
            case R.id.btn_share:
                break;
            case R.id.sbtn_jxyd:
                break;
            case R.id.sbtn_jrsj:
                break;
            case R.id.iv_zddy:{
                LogUtil.e("点击问号");
//                popupWindow.showAsDropDown(ivZddy, 0, 0);
            }
                break;
            case R.id.iv_zk:{
                //展开
                if(ivZk.getText().equals("展开")){
                    ivZk.setText("收起");
                    ivZk.setCompoundDrawables(null,null,getDrawable(R.drawable.down),null);
                    tvDetail.setMaxLines(5);
                    tvDetail.setText(detailBean.getData().getNovelInfo().getNovelIntro());
                }else{//缩放
                    ivZk.setText("展开");
                    ivZk.setCompoundDrawables(null,null,getDrawable(R.drawable.up),null);
                    tvDetail.setMaxLines(2);
                    tvDetail.setText(detailBean.getData().getNovelInfo().getNovelIntro());
                }
            }break;
            case R.id.rl_ml:
                break;
            case R.id.iv_queLeft:
                break;
            case R.id.btn_sendLeft:
                break;
            case R.id.iv_queRight:
                break;
            case R.id.btn_sendRight:
                break;
        }
    }

    List<BookDetailBean.DataBean.GiftsBean>list=new ArrayList<>();
    private BookDetailBean detailBean;

    @Override
    public void getOpenSuccess(BookDetailBean bean) {
        detailBean=bean;
        if(bean.isSuccess()){
            BookDetailBean.DataBean.NovelInfoBean novelInfoBean=bean.getData().getNovelInfo();
            Glide.with(BookDetailsActivity.this)
                    .load(novelInfoBean.getNovelPhoto())
                    .into(ivBook);
            tvNovelTitle.setText(novelInfoBean.getNovelTitle());
            String writer=novelInfoBean.getNovelAuthor();
            SpannableString spannableString = new SpannableString(writer+" "  +"著");
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#757575"));
            spannableString.setSpan(colorSpan, writer.length(), spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            tvNovelAuthor.setText(spannableString);
            tvType.setText(novelInfoBean.getNovelType());
            if(novelInfoBean.getIsOver().equals("0")){
                tvLianzai.setText("连载中");
            }else{
                tvLianzai.setText("已完结");
            }
            tv_zishu.setText(novelInfoBean.getNovelWords()+"字  "+novelInfoBean.getClickNum()+"次点击");

            if(novelInfoBean.getIsCollection()!=null && novelInfoBean.getIsCollection().equals("true")) {
                sbtnJrsj.setClickable(true);
                sbtnJrsj.setText("加入书架");
                sbtnJrsj.setBackgroundColor(Color.parseColor("#01c78a"));
            }else{
                sbtnJrsj.setClickable(false);
                sbtnJrsj.setText("已在书架");
                sbtnJrsj.setBackgroundColor(Color.parseColor("#03ad9b"));
            }

            tvDetail.setText(novelInfoBean.getNovelIntro());

            //目录 最新章节：
            tvMl.setText("目录 最新章节：第"+bean.getData().getNewChapter().getChapter()+"章 "+bean.getData().getNewChapter().getTitle());
            tvTime.setText(bean.getData().getNewChapter().getPublishTime());

            list=bean.getData().getGifts();
            if(list.size()==4){
                tvMc.setText(list.get(0).getRewardCount());
                tvPmhc.setText("排行"+list.get(0).getRank()+" 还差"+list.get(0).getDifference()+"颗追上前一名");
                tvPs.setText(list.get(2).getRewardCount());
                tvPmright.setText("排行"+list.get(2).getRank()+" 还差"+list.get(2).getDifference()+"颗追上前一名");
            }

            DetailBookPanel pane0=new DetailBookPanel(BookDetailsActivity.this);
            pane0.setType(0);
            pane0.setBookDetail(bean);
            llBookdetail.addView(pane0);
            llBookdetail.addView(LayoutInflater.from(BookDetailsActivity.this).inflate(R.layout.whitebackground, null));
            DetailBookPanel pane1=new DetailBookPanel(BookDetailsActivity.this);
            pane1.setType(1);
            pane1.setBookDetail(bean);
            llBookdetail.addView(pane1);
            llBookdetail.addView(LayoutInflater.from(BookDetailsActivity.this).inflate(R.layout.whitebackground, null));
            DetailBookPanel pane2=new DetailBookPanel(BookDetailsActivity.this);
            pane2.setType(2);
            pane2.setBookDetail(bean);
            llBookdetail.addView(pane2);

        }else{
            ToastUtils.showShortToast(bean.getMessage());
        }

    }

    //加入书架
    @Override
    public void saveCollectionSuccess(BaseBean bean) {
//        if(item.isCollection()!=null && item.isCollection().equals("true")){
        if(bean.isSuccess()){
            sbtnJrsj.setClickable(false);
            sbtnJrsj.setText("已在书架");
            sbtnJrsj.setBackgroundColor(Color.parseColor("#03ad9b"));
        }else{
            ToastUtils.showShortToast(bean.getMessage());
        }
    }

    @Override
    public void fail(String message) {
        LogUtil.e("失败原因:"+message);
    }

    @Override
    public void noConnectInternet() {
        ToastUtils.showShortToast("网络错误，请检查网络");
    }

}
