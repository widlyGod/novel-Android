package com.novel.cn.ui.home.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import com.novel.cn.model.entity.ChargeChapterBean;
import com.novel.cn.model.entity.ReadChapterBean;
import com.novel.cn.model.entity.ReadResponBean;
import com.novel.cn.model.entity.UserBean;
import com.novel.cn.persenter.Contract.ReadContract;
import com.novel.cn.persenter.PresenterClass.ReadPresenter;
import com.novel.cn.ui.LoginActivity;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.SharePrefUtil;
import com.novel.cn.util.ToastUtils;
import com.novel.cn.view.wight.ReadTextView;
import com.novel.cn.view.wight.StateButton;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 阅读界面
 * Created by jackieli on 2019/1/24.
 */

public class ReadActivity extends AutoLayoutActivity implements ReadContract.View {

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
    String novelId = "";
    ReadPresenter presenter;
    @Bind(R.id.sb_cz)
    StateButton sbCz;
    @Bind(R.id.rb_zddy)
    RadioButton rbZddy;
    @Bind(R.id.ll_czjm)
    LinearLayout llCzjm;
    @Bind(R.id.tv_czYdb)
    TextView tv_czYdb;
    @Bind(R.id.tv_czYe)
    TextView tv_czYe;
    private boolean isDay = true;
    private int novelTextSize = 17;
    private int chapter = 1;
    private ReadResponBean bean;
    @Bind(R.id.state_layout)
    StateLayout state_layout;
    //我的书架上面接口加个哪一章节
    //付费，点击事件弹窗，两个接口阅读的有什么不一样
    //评论，分享功能,打赏
    private boolean isIntentMulu = false;
    private boolean isLogin = false;
    public UserBean userBean;
    private int isCharge=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);
        ButterKnife.bind(this);
        chapterId = getIntent().getStringExtra("id");
        novelId = getIntent().getStringExtra("novelId");
        chapter = getIntent().getIntExtra("chapterIndex", 1);
        isIntentMulu = getIntent().getBooleanExtra("isIntentMulu", false);
        isLogin = SharePrefUtil.getBoolean(ReadActivity.this, "isLogin", false);

        isCharge=getIntent().getIntExtra("isCharge",0);

        String userBeanx = SharePrefUtil.getString(ReadActivity.this, "user", "");
        if (!userBeanx.equals("")) {
            userBean = UserBean.objectFromData(userBeanx);
        }


        LogUtil.e("readNovel chapterId=" + chapterId + ",novelId=" + novelId);
        presenter = new ReadPresenter();
        presenter.setMvpView(this, "");

        presenter.readNovel(chapterId,isCharge==1?true:false);
        presenter.getChapters(novelId, "1", 0);
        state_layout.showLoadingView();

        rgPf.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
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
                switch (i) {
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

        seekbarZj.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b == true) {
                    chapter = i + 1;
                    LogUtil.e("进度章节chapter=" + chapter);

                    isCharge=chapterBeans.get(i).getIsFree();
                    presenter.readNovel(chapterBeans.get(i).getId(),
                            chapterBeans.get(i).getIsFree()==1?true:false);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


    }


    public void setTyperface(String path) {
        Typeface typeface = Typeface.createFromAsset(getAssets(), path);
        tvZw.setTypeface(typeface);
        tvBookName.setTypeface(typeface);
        tvTitleName.setTypeface(typeface);
        tvAutor.setTypeface(typeface);
        tvWord.setTypeface(typeface);
        tvTime.setTypeface(typeface);
    }

    public void setDayNightTextColor(String bgColor, String textColor) {
        scrollView.setBackgroundColor(Color.parseColor(bgColor));
        tvZw.setTextColor(Color.parseColor(textColor));
        tvBookName.setTextColor(Color.parseColor(textColor));
        tvTitleName.setTextColor(Color.parseColor(textColor));
        tvTitleName.setTextColor(Color.parseColor(textColor));
        tvAutor.setTextColor(Color.parseColor(textColor));
        tvWord.setTextColor(Color.parseColor(textColor));
        tvTime.setTextColor(Color.parseColor(textColor));
    }

    @SuppressLint("NewApi")
    @OnClick({R.id.sbtn_syz, R.id.sbtn_ml, R.id.sbtn_yzj, R.id.iv_dayNight, R.id.iv_dasan, R.id.ivBack, R.id.tv_syz, R.id.tv_xyz,
            R.id.tv_mulu, R.id.tv_set, R.id.tv_pl, R.id.tv_sc, R.id.tv_share, R.id.rl_title, R.id.tv_zw, R.id.btn_ztj, R.id.btn_zen,R.id.sb_cz})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sbtn_syz: {//上一章
                setUpDownChapter(false);
            }break;
            case R.id.sbtn_ml: {//目录
                Intent intent = new Intent(ReadActivity.this, CatalogActivity.class);
                intent.putExtra("id", novelId);
                intent.putExtra("type", 0);
                startActivityForResult(intent, 0);
            }break;
            case R.id.sbtn_yzj: {//下一章
                setUpDownChapter(true);
            }break;
            case R.id.iv_dayNight: {//日夜间转换
                if (isDay) {//转为黑夜
                    isDay = false;
                    ivDayNight.setImageResource(R.drawable.read_month);
                    setDayNightTextColor("#323838", "#a1adad");
                } else {//转为白天
                    isDay = true;
                    ivDayNight.setImageResource(R.drawable.read_day);
                    setDayNightTextColor("#FEDFC3", "#333333");
                }
            }break;
            case R.id.iv_dasan://打赏
                break;
            case R.id.ivBack://返回键
                finish();
                break;
            case R.id.tv_syz: {//弹出框的上一章
                setUpDownChapter(false);
            }
            break;
            case R.id.tv_xyz: {//弹出框的下一章
                setUpDownChapter(true);
            }
            break;
            case R.id.tv_mulu: {//弹出框的目录
                Intent intent = new Intent(ReadActivity.this, CatalogActivity.class);
                intent.putExtra("id", novelId);
                intent.putExtra("type", 0);
                startActivityForResult(intent, 0);
            }
            break;
            case R.id.tv_set://点击设置字体大小与背景
                llReadset.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_pl://评论
                break;
            case R.id.tv_sc: {//收藏该书本
                if (bean != null) {
                    if (bean.getData().getNovelInfo().getIsCollection().equals("true")) {
                        presenter.cancelCollection(novelId);
                    } else {
                        presenter.saveCollection(novelId);
                    }
                }
            }
            break;
            case R.id.tv_share://分享该章节
                break;
            case R.id.rl_title: {//小说正文头部
                setTopOrBottomVis();
            }
            break;
            case R.id.tv_zw: {//小说正文
                setTopOrBottomVis();
            }
            break;
            case R.id.btn_ztj:
                novelTextSize = novelTextSize - 1;
                tvZw.setTextSize(TypedValue.COMPLEX_UNIT_SP, novelTextSize); //22SP
                btnZh.setText(novelTextSize + "");
                break;
            case R.id.btn_zen:
                novelTextSize = novelTextSize + 1;
                tvZw.setTextSize(TypedValue.COMPLEX_UNIT_SP, novelTextSize); //22SP
                btnZh.setText(novelTextSize + "");
                break;
            case R.id.sb_cz:{//充值按钮


            }break;
        }
    }


    public void setUpDownChapter(boolean isNext) {
        if (isNext) {//下一章
            if (chapter < chapterBeans.size()) {
                chapter = chapter + 1;
                LogUtil.e("进度章节chapter=" + chapter);
                seekbarZj.setProgress(chapter - 1);
                isCharge=chapterBeans.get(chapter - 1).getIsFree();
                presenter.readNovel(chapterBeans.get(chapter - 1).getId(),
                        chapterBeans.get(chapter - 1).getIsFree()==1?true:false);
            } else {
                ToastUtils.showShortToast("当前为最后一章");
            }
        } else {//上一章
            if (chapter != 1) {
                chapter = chapter - 1;
                seekbarZj.setProgress(chapter - 1);
                isCharge=chapterBeans.get(chapter - 1).getIsFree();
                presenter.readNovel(chapterBeans.get(chapter - 1).getId(),
                        chapterBeans.get(chapter - 1).getIsFree()==1?true:false);
            } else {
                ToastUtils.showShortToast("当前为第一章");
            }
        }
        LogUtil.e("进度章节chapter=" + chapter);
    }

    public void setTopOrBottomVis() {
        if (llTop.getVisibility() == View.GONE) {
            llTop.setVisibility(View.VISIBLE);
            rlBottom.setVisibility(View.VISIBLE);
            ivDayNight.setVisibility(View.VISIBLE);
            llReadset.setVisibility(View.GONE);
        } else {
            llTop.setVisibility(View.GONE);
            rlBottom.setVisibility(View.GONE);
            ivDayNight.setVisibility(View.GONE);
            llReadset.setVisibility(View.GONE);
        }

    }


    //获取阅读数据成功
    @Override
    public void readNovelSuccess(ReadResponBean data,boolean isGetCharge) {
        tvZw.setVisibility(View.VISIBLE);
        if (data.isSuccess()) {
            llCzjm.setVisibility(View.GONE);
            bean = data;
            ReadResponBean.DataBean.NovelInfoBean novelBean = data.getData().getNovelInfo();
            ReadResponBean.DataBean.ChapterInfoBean chapterBean = data.getData().getChapterInfo();

            //免费小说,展示数据
            //正文
            tvZw.setText(chapterBean.getContent());
            //弹窗顶部
            tvBt.setText("第" + chapterBean.getChapter() + "章 " + chapterBean.getTitle());
            //正文顶部
            tvBookName.setText(novelBean.getTitle());
            tvTitleName.setText("第" + chapterBean.getChapter() + "章 " + chapterBean.getTitle());
            tvAutor.setText(novelBean.getAuthorInfo().getPenName());
            tvWord.setText(chapterBean.getWords() + "");
            tvTime.setText(chapterBean.getCreateTime());

            if(isGetCharge==false){//当前请求的是收费接口，不用再请求
                //书本是否付费
                if (chapterBean.getIsFree() == 1 && isLogin == false) {//付费未登录进登录界面。
                    //进登录界面
                    Intent intent=new Intent(ReadActivity.this, LoginActivity.class);
//                    intent.putExtra("isIntentRead",true);
                    startActivityForResult(intent,0);
                } else if (chapterBean.getIsFree() == 1 && userBean != null && userBean.getRecodeCode() == 100) {//付费如果当前用户是普通用户判断该章节是否已付过费
                    //userBean角色100普通用户，0管理，101编辑）
                    //付费小说，调是否付费过的接口判断（免费章节,限时限免,本书的作者以及管理员->过滤掉），未付费展示付费界面，付费了展示数据
                    presenter.isChargeChapter(novelBean.getId(), chapterBean.getVolumeId(), chapterBean.getId());
                }
            }
            setCollection(novelBean.getIsCollection().endsWith("true") ? true : false);
        } else {
            ToastUtils.showShortToast(data.getMessage());
        }
        state_layout.showContentView();
    }

    @Override
    public void saveCollectionSuccess(BaseBean data) {
        if (data.isSuccess()) {
            setCollection(true);
        }
        ToastUtils.showShortToast(data.getMessage());
    }

    @Override
    public void cancelCollectionSuccess(BaseBean data) {
        if (data.isSuccess()) {
            setCollection(false);
        }
        ToastUtils.showShortToast(data.getMessage());
    }


    List<ReadChapterBean.DataBean.NovelInfoBean.VolumeInfosBean.ChapterInfosBean> chapterBeans = new ArrayList<>();

    //获取章节成功
    @Override
    public void getChaptersSuccess(ReadChapterBean bean) {
        if (bean.isSuccess()) {
            chapterBeans.clear();
            List<ReadChapterBean.DataBean.NovelInfoBean.VolumeInfosBean> list = bean.getData().getNovelInfo().getVolumeInfos();
            for (int i = 0; i < list.size(); i++) {
                List<ReadChapterBean.DataBean.NovelInfoBean.VolumeInfosBean.ChapterInfosBean> chapterList = list.get(i).getChapterInfos();
                for (int j = 0; j < chapterList.size(); j++) {
                    int chapter = chapterList.get(j).getChapter();
                    LogUtil.e("循环获得章节第:" + chapter + "章");
                    chapterBeans.add(chapterList.get(j));
                }
            }
            seekbarZj.setMax(chapterBeans.size() > 1 ? chapterBeans.size() - 1 : 0);
            //会请求接口
            if (isIntentMulu) {//从目录页进来的阅读界面
                seekbarZj.setProgress(chapter - 1);
            }
        }
    }

    //判断收费章节是否收费
    @Override
    public void isChargeChapterSuccess(ChargeChapterBean bean) {
        if (bean.getMessage()!=null && bean.getMessage().equals("该章节还未收费！")) {//判断展示付款界面还是阅读界面
            tvZw.setVisibility(View.GONE);
            int jiage= chapterBeans.get(chapter - 1).getMoney();
            double yue= bean.getData().getGoldNumber();
            tv_czYdb.setText("价格:" + jiage + "阅读币");
            tv_czYe.setText("余额:" + bean.getData().getGoldNumber() + "阅读币 | " + bean.getData().getDiamondNumber() + "砖石");
            llCzjm.setVisibility(View.VISIBLE);
            if(yue>jiage){
                sbCz.setText("订阅本章节");
            }else{
                sbCz.setText("余额不足，请充值");
            }
        } else {
            LogUtil.e("isChargeChapter="+bean.getMessage());
        }
    }

    public void setCollection(boolean isCollect) {
        if (isCollect) {
            bean.getData().getNovelInfo().setIsCollection("true");
            tvSc.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.read_scs), null, null);
        } else {
            bean.getData().getNovelInfo().setIsCollection("false");
            tvSc.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.drawable.read_sc), null, null);
        }
    }


    //失败
    @Override
    public void fail(String message) {
        state_layout.showContentView();
        LogUtil.e("fail=" + message);
    }


    //无网络
    @Override
    public void noConnectInternet() {
        ToastUtils.showShortToast("网络错误，请检查网络");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 ) {
            switch (resultCode){
                case 0:{//从目录界面回来
                    if (data != null) {
                        chapterId = data.getStringExtra("id");
                        chapter = data.getIntExtra("chapterIndex", 1);
                        isCharge=data.getIntExtra("chapterIndex", 1);
                        presenter.readNovel(chapterId, isCharge==1?true:false);
                        seekbarZj.setProgress(chapter - 1);
                    }
                }break;
                case 1:{//从登录界面回来，更新用户信息
                    isLogin = SharePrefUtil.getBoolean(ReadActivity.this, "isLogin", false);
                    String userBeanx = SharePrefUtil.getString(ReadActivity.this, "user", "");
                    if (!userBeanx.equals("")) {
                        userBean = UserBean.objectFromData(userBeanx);
                    }
                    presenter.readNovel(chapterId,isCharge==1?true:false);
                }break;
                case 2:
                    break;
            }
        }
    }


}
