package com.novel.cn.ui.home.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fingdo.statelayout.StateLayout;
import com.novel.cn.R;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.ReadResponBean;
import com.novel.cn.persenter.Contract.ReadContract;
import com.novel.cn.persenter.PresenterClass.ReadPresenter;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.ToastUtils;
import com.novel.cn.view.wight.ReadTextView;
import com.novel.cn.view.wight.StateButton;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 阅读界面
 * Created by jackieli on 2019/1/24.
 */

public class ReadActivity extends AutoLayoutActivity implements ReadContract.View{

    @Bind(R.id.tv_bookName)
    TextView tvBookName;
    @Bind(R.id.tv_titleName)
    TextView tvTitleName;
    @Bind(R.id.tv_autor)
    TextView tvAutor;
    @Bind(R.id.tv_word)
    TextView tvWord;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.tv_zw)
    ReadTextView tvZw;
    @Bind(R.id.sbtn_syz)
    StateButton sbtnSyz;
    @Bind(R.id.sbtn_ml)
    StateButton sbtnMl;
    @Bind(R.id.sbtn_yzj)
    StateButton sbtnYzj;
    @Bind(R.id.ll_syzmlxyz)
    LinearLayout llSyzmlxyz;
    @Bind(R.id.iv_dasan)
    ImageView ivDasan;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.ivBack)
    ImageView ivBack;
    @Bind(R.id.tv_bt)
    TextView tvBt;
    @Bind(R.id.ll_Top)
    LinearLayout llTop;
    @Bind(R.id.iv_dayNight)
    ImageView ivDayNight;
    @Bind(R.id.tv_syz)
    TextView tvSyz;
    @Bind(R.id.seekbar_zj)
    SeekBar seekbarZj;
    @Bind(R.id.tv_xyz)
    TextView tvXyz;
    @Bind(R.id.tv_mulu)
    TextView tvMulu;
    @Bind(R.id.tv_set)
    TextView tvSet;
    @Bind(R.id.tv_pl)
    TextView tvPl;
    @Bind(R.id.tv_sc)
    TextView tvSc;
    @Bind(R.id.tv_share)
    TextView tvShare;
    @Bind(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @Bind(R.id.rl_title)
    RelativeLayout rl_title;

    @Bind(R.id.rb_rbone)
    RadioButton rbRbone;
    @Bind(R.id.rb_rbtwo)
    RadioButton rbRbtwo;
    @Bind(R.id.rb_rbthree)
    RadioButton rbRbthree;
    @Bind(R.id.rb_rbfour)
    RadioButton rbRbfour;
    @Bind(R.id.rb_rbfive)
    RadioButton rbRbfive;
    @Bind(R.id.rg_pf)
    RadioGroup rgPf;
    @Bind(R.id.btn_ztj)
    TextView btnZtj;
    @Bind(R.id.btn_zh)
    TextView btnZh;
    @Bind(R.id.btn_zen)
    TextView btnZen;
    @Bind(R.id.rb_yh)
    RadioButton rbYh;
    @Bind(R.id.rb_st)
    RadioButton rbSt;
    @Bind(R.id.rb_ks)
    RadioButton rbKs;
    @Bind(R.id.rg_ztgs)
    RadioGroup rgZtgs;
    @Bind(R.id.ll_readset)
    LinearLayout llReadset;
    String chapterId = "";
    String novelId="";
    ReadPresenter presenter;
    private boolean isDay=true;
    //登录与阅读接口403错误，字体设置与背景设置，上下一章与目录
    private int novelTextSize=17;
    private ReadResponBean bean;
    @Bind(R.id.state_layout)
    StateLayout state_layout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        ButterKnife.bind(this);
        chapterId = getIntent().getStringExtra("id");
        novelId = getIntent().getStringExtra("novelId");

        LogUtil.e("readNovel chapterId="+chapterId+",novelId="+novelId);
        presenter=new ReadPresenter();
        presenter.setMvpView(this,"");
        presenter.readNovel(chapterId);
        state_layout.showLoadingView();


        rgPf.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_rbone:
                        scrollView.setBackgroundColor(Color.parseColor("#f7f7f7"));
                        tvZw.setTextColor(Color.parseColor("#333333"));
                        break;
                    case R.id.rb_rbtwo:
                        scrollView.setBackgroundColor(Color.parseColor("#ffffe5"));
                        tvZw.setTextColor(Color.parseColor("#333333"));
                        break;
                    case R.id.rb_rbthree:
                        scrollView.setBackgroundColor(Color.parseColor("#fedfc3"));
                        tvZw.setTextColor(Color.parseColor("#333333"));
                        break;
                    case R.id.rb_rbfour:
                        scrollView.setBackgroundColor(Color.parseColor("#bacbbb"));
                        tvZw.setTextColor(Color.parseColor("#333333"));
                        break;
                    case R.id.rb_rbfive:
                        scrollView.setBackgroundColor(Color.parseColor("#507b74"));
                        tvZw.setTextColor(Color.parseColor("#333333"));
                        break;
                }
            }
        });

        rgZtgs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_yh:
                        setTyperface("fonts/Yahei.ttf");
                        break;
                    case R.id.rb_st:
                        setTyperface("fonts/Sonti.ttf");
                        break;
                    case R.id.rb_ks:
                        setTyperface("fonts/Kaiti.ttf");
                        break;
                }
            }
        });

    }


    public void setTyperface(String path){
        Typeface typeface = Typeface.createFromAsset(getAssets(), path);
        tvZw.setTypeface(typeface);
        tvBookName.setTypeface(typeface);
        tvTitleName.setTypeface(typeface);
        tvAutor.setTypeface(typeface);
        tvWord.setTypeface(typeface);
        tvTime.setTypeface(typeface);
    }

    public void setDayNightTextColor(String bgColor,String textColor){
        scrollView.setBackgroundColor(Color.parseColor(bgColor));
        tvZw.setTextColor(Color.parseColor(textColor));
        tvBookName.setTextColor(Color.parseColor(textColor));
        tvTitleName.setTextColor(Color.parseColor(textColor));
        tvTitleName.setTextColor(Color.parseColor(textColor));
        tvAutor.setTextColor(Color.parseColor(textColor));
        tvWord.setTextColor(Color.parseColor(textColor));
        tvTime.setTextColor(Color.parseColor(textColor));
    }

    @OnClick({R.id.sbtn_syz, R.id.sbtn_ml, R.id.sbtn_yzj, R.id.iv_dayNight,R.id.iv_dasan, R.id.ivBack, R.id.tv_syz,R.id.tv_xyz,
            R.id.tv_mulu, R.id.tv_set, R.id.tv_pl, R.id.tv_sc, R.id.tv_share,R.id.rl_title,R.id.tv_zw,R.id.btn_ztj,R.id.btn_zen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sbtn_syz://上一章
                break;
            case R.id.sbtn_ml://目录
                break;
            case R.id.sbtn_yzj://下一章
                break;
            case R.id.iv_dayNight:{//日夜间转换
                if(isDay){//转为黑夜
                    isDay=false;
                    ivDayNight.setImageResource(R.drawable.read_month);
                    setDayNightTextColor("#323838","#a1adad");
                }else {//转为白天
                    isDay=true;
                    ivDayNight.setImageResource(R.drawable.read_day);
                    setDayNightTextColor("#FEDFC3","#333333");
                }
            }break;
            case R.id.iv_dasan://打赏
                break;
            case R.id.ivBack://返回键
                finish();
                break;
            case R.id.tv_syz://弹出框的上一章
                break;
            case R.id.tv_xyz://弹出框的下一章
                break;
            case R.id.tv_mulu:{//弹出框的目录

            }break;
            case R.id.tv_set://点击设置字体大小与背景
                llReadset.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_pl://评论
                break;
            case R.id.tv_sc: {//收藏该书本
                if(bean!=null){
                    if(bean.getData().getNovelInfo().getIsCollection().equals("true")){
                        presenter.cancelCollection(novelId);
                    }else{
                        presenter.saveCollection(novelId);
                    }
                }
            }break;
            case R.id.tv_share://分享该章节
                break;
            case R.id.rl_title:{//小说正文头部
                setTopOrBottomVis();
            }break;
            case R.id.tv_zw:{//小说正文
                setTopOrBottomVis();
            }break;
            case R.id.btn_ztj:
                novelTextSize=novelTextSize-1;
                tvZw.setTextSize(TypedValue.COMPLEX_UNIT_SP,novelTextSize); //22SP
                btnZh.setText(novelTextSize+"");
                break;
            case R.id.btn_zen:
                novelTextSize=novelTextSize+1;
                tvZw.setTextSize(TypedValue.COMPLEX_UNIT_SP,novelTextSize); //22SP
                btnZh.setText(novelTextSize+"");
                break;
        }
    }


    public void setTopOrBottomVis(){
        if(llTop.getVisibility()==View.GONE){
            llTop.setVisibility(View.VISIBLE);
            rlBottom.setVisibility(View.VISIBLE);
            ivDayNight.setVisibility(View.VISIBLE);
            llReadset.setVisibility(View.GONE);
        }else{
            llTop.setVisibility(View.GONE);
            rlBottom.setVisibility(View.GONE);
            ivDayNight.setVisibility(View.GONE);
            llReadset.setVisibility(View.GONE);
        }

    }


    //获取阅读数据成功
    @Override
    public void readNovelSuccess(ReadResponBean data) {
        if(data.isSuccess()){
            bean=data;
            ReadResponBean.DataBean.NovelInfoBean novelBean=data.getData().getNovelInfo();
            ReadResponBean.DataBean.ChapterInfoBean chapterBean=data.getData().getChapterInfo();
            //正文
            tvZw.setText(chapterBean.getContent());
            //弹窗顶部
            tvBt.setText("第"+chapterBean.getChapter()+"章 "+chapterBean.getTitle());
            //正文顶部
            tvBookName.setText(novelBean.getTitle());
            tvTitleName.setText("第"+chapterBean.getChapter()+"章 "+chapterBean.getTitle());
            tvAutor.setText(novelBean.getAuthorInfo().getPenName());
            tvWord.setText(chapterBean.getWords()+"");
            tvTime.setText(chapterBean.getCreateTime());

            setCollection(novelBean.getIsCollection().endsWith("true")?true:false);
        }else{
            ToastUtils.showShortToast(data.getMessage());
        }
        state_layout.showContentView();
    }

    @Override
    public void saveCollectionSuccess(BaseBean data) {
        if(data.isSuccess()){
            setCollection(true);
        }
        ToastUtils.showShortToast(data.getMessage());
    }

    @Override
    public void cancelCollectionSuccess(BaseBean data) {
        if(data.isSuccess()){
            setCollection(false);
        }
        ToastUtils.showShortToast(data.getMessage());
    }

    public void setCollection(boolean isCollect){
        if(isCollect){
            bean.getData().getNovelInfo().setIsCollection("true");
            tvSc.setCompoundDrawablesWithIntrinsicBounds(null,  getResources().getDrawable(R.drawable.read_scs),null, null);
        }else{
            bean.getData().getNovelInfo().setIsCollection("false");
            tvSc.setCompoundDrawablesWithIntrinsicBounds(null,  getResources().getDrawable(R.drawable.read_sc),null, null);
        }
    }

    //失败
    @Override
    public void fail(String message) {
        state_layout.showContentView();
        LogUtil.e("fail="+message);
    }

    //无网络
    @Override
    public void noConnectInternet() {
        ToastUtils.showShortToast("网络错误，请检查网络");
    }

}
