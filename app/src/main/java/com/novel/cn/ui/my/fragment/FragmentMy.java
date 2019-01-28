package com.novel.cn.ui.my.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.novel.cn.R;
import com.novel.cn.base.BaseFragment;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.PersonDataBean;
import com.novel.cn.model.entity.UserBean;
import com.novel.cn.persenter.Contract.FragmentMyContract;
import com.novel.cn.persenter.PresenterClass.FragmentMyPresenter;
import com.novel.cn.ui.LoginActivity;
import com.novel.cn.ui.book.activity.TestInfoActivity;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.SharePrefUtil;
import com.novel.cn.util.ToastUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jackieli on 2018/12/26.
 */

public class FragmentMy extends BaseFragment implements FragmentMyContract.View {


    @Bind(R.id.tv_ydb)
    TextView tv_ydb;
    @Bind(R.id.tv_cz)
    TextView tv_cz;
    @Bind(R.id.iv_personiv)
    SimpleDraweeView ivPersoniv;
    @Bind(R.id.rl_tx)
    RelativeLayout rlTx;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_level)
    TextView tvLevel;
    @Bind(R.id.tv_gy)
    TextView tvGy;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.rl_loginbtn)
    RelativeLayout rlLoginbtn;
    @Bind(R.id.tv_masonry)
    TextView tvMasonry;
    @Bind(R.id.tv_reward)
    TextView tvReward;
    @Bind(R.id.tv_recticket)
    TextView tvRecticket;
    @Bind(R.id.tv_montick)
    TextView tvMontick;
    @Bind(R.id.ll_dia)
    LinearLayout llDia;
    @Bind(R.id.rl_mycount)
    RelativeLayout rlMycount;
    @Bind(R.id.rl_news)
    RelativeLayout rlNews;
    @Bind(R.id.rl_share)
    RelativeLayout rlShare;
    @Bind(R.id.rl_conhis)
    RelativeLayout rlConhis;
    @Bind(R.id.rl_set)
    RelativeLayout rlSet;
    @Bind(R.id.rl_verinf)
    RelativeLayout rlVerinf;
    @Bind(R.id.btn_out)
    Button btnOut;
    @Bind(R.id.news_num)
    TextView newsNum;
    @Bind(R.id.share_num)
    TextView shareNum;
    private FragmentMyPresenter presenter;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData() {
        presenter = new FragmentMyPresenter();
        presenter.setMvpView(this, "");
        boolean isLogin = SharePrefUtil.getBoolean(getActivity(), "isLogin", false);
        if (isLogin) {
            presenter.getPersonData();
            tvLevel.setVisibility(View.VISIBLE);
            tvGy.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.GONE);
        } else {
            tvLevel.setVisibility(View.GONE);
            tvGy.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
//            changeViewData(false);
        }


    }


    //获取个人数据成功
    @Override
    public void getPersonDataSuccess(PersonDataBean data) {
        if (data.isSuccess()) {
            PersonDataBean.DataBean dataBean = data.getData();
            if(dataBean.getUserPhoto() != null && !dataBean.getUserPhoto().equals("")) {
                ivPersoniv.setImageURI(dataBean.getUserPhoto());
            }
            tvName.setText(dataBean.getUserName());
            tvLevel.setText("LV" + dataBean.getUserLevel());
            if (dataBean.getUserIntro() != null && !dataBean.getUserIntro().equals("")) {
                tvGy.setText(dataBean.getUserIntro());
            } else {
                tvGy.setText("这家伙很懒，什么都没有留下");
            }
            tvMasonry.setText(dataBean.getDiamonds());
            tvReward.setText(dataBean.getMoneys());
            tvRecticket.setText(dataBean.getRecommendTickets());
            tvMontick.setText(dataBean.getMonthTickets());
            tv_ydb.setText(dataBean.getMoneys() + "阅读币");
            newsNum.setText(dataBean.getMsgCount());
            shareNum.setText(dataBean.getShareCount());
            tvLevel.setVisibility(View.VISIBLE);
            tvGy.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.GONE);
        } else {
            LogUtil.e("错误信息" + data.getMessage());
        }
    }


    //登出成功
    @Override
    public void logoutSuccess(BaseBean baseBean) {

        if (baseBean.isSuccess()) {
            ToastUtils.showShortToast(baseBean.getMessage());
            //改变界面信息代码
            changeViewData(true);
        } else {
            ToastUtils.showShortToast(baseBean.getMessage());
        }

    }


    public void changeViewData(boolean isSuccess) {
        if (isSuccess) {
            SharePrefUtil.saveBoolean(getActivity(), "isLogin", false);
            SharePrefUtil.saveString(getActivity(), "sessionId", "");
            SharePrefUtil.saveString(getActivity(), "user", "");
            tvLevel.setVisibility(View.GONE);
            tvGy.setVisibility(View.GONE);
            btnLogin.setVisibility(View.VISIBLE);
        }
        ivPersoniv.setImageURI((new Uri.Builder()).scheme("res").path(String.valueOf(R.drawable.personimage)).build());
        tvName.setText("游客");
        tvLevel.setText("LV0");
        tvGy.setText("这家伙很懒，什么都没有留下");
        tvMasonry.setText("0");
        tvReward.setText("0");
        tvRecticket.setText("0");
        tvMontick.setText("0");
        tv_ydb.setText("0阅读币");
        newsNum.setText("0");
        shareNum.setText("0");
    }


    @OnClick({R.id.rl_tx, R.id.tv_cz, R.id.btn_login, R.id.rl_news, R.id.rl_share, R.id.rl_conhis, R.id.rl_set, R.id.rl_verinf, R.id.btn_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_tx: {
                Intent intent=new Intent(getActivity(), TestInfoActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.tv_cz: {
                mListener.onClickFragment(3);
            }
            break;
            case R.id.btn_login: {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivityForResult(intent, 0);
            }
            break;
            case R.id.rl_news: {


            }
            break;
            case R.id.rl_share: {


            }
            break;
            case R.id.rl_conhis: {


            }
            break;
            case R.id.rl_set: {


            }
            break;
            case R.id.rl_verinf: {


            }
            break;
            case R.id.btn_out: {
                boolean isLogin = SharePrefUtil.getBoolean(getActivity(), "isLogin", false);
                if (isLogin) {
                    presenter.logout();
                } else {
                    ToastUtils.showShortToast("该用户暂未登录");
                }
            }
            break;
        }
    }

    private boolean isHidden=false;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtil.e("fragmentMy 更新个人信息="+hidden);
        isHidden=hidden;
        if(hidden==false){
            boolean isLogin = SharePrefUtil.getBoolean(getActivity(), "isLogin", false);
            if(isLogin){
                tvLevel.setVisibility(View.VISIBLE);
                tvGy.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.GONE);
                presenter.getPersonData();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean isLogin = SharePrefUtil.getBoolean(getActivity(), "isLogin", false);
        if(isLogin==true && isHidden==false){
            LogUtil.e("fragmentMy onResume");
            presenter.getPersonData();
        }
    }

    //失败
    @Override
    public void fail(String message) {
        LogUtil.e("获取个人信息message:" + message);
    }

    //无网络连接
    @Override
    public void noConnectInternet() {
        ToastUtils.showShortToast("网络错误，请检查网络");
    }
}
