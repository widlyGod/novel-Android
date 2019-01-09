package com.novel.cn.ui.recharge.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.novel.cn.R;
import com.novel.cn.alipay.PaymentHelper;
import com.novel.cn.base.BaseFragment;
import com.novel.cn.model.entity.AplipayOrderBean;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.QueryUpayBean;
import com.novel.cn.model.entity.UserBean;
import com.novel.cn.model.entity.WxOrderBean;
import com.novel.cn.persenter.Contract.FragmentRechargeContract;
import com.novel.cn.persenter.PresenterClass.FragmentRechargePresenter;
import com.novel.cn.ui.LoginActivity;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.SharePrefUtil;
import com.novel.cn.util.ToastUtils;
import com.novel.cn.view.wight.CustomRadioButton;
import com.novel.cn.view.wight.MRadioButton;
import com.novel.cn.view.wight.MRadioGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jackieli on 2018/12/26.
 */

public class FragmentRecharge extends BaseFragment implements FragmentRechargeContract.View {


    @Bind(R.id.appbar)
    RelativeLayout appbar;
    @Bind(R.id.tv_account)
    TextView tvAccount;
    @Bind(R.id.tv_balance)
    TextView tvBalance;
    @Bind(R.id.zhAndye)
    LinearLayout zhAndye;
    @Bind(R.id.rb_wx)
    CustomRadioButton rbWx;
    @Bind(R.id.rb_zfb)
    CustomRadioButton rbZfb;
    @Bind(R.id.rg_rechargeType)
    RadioGroup rgRechargeType;

    @Bind(R.id.myradiogroup)
    MRadioGroup mRadioGroup;
    @Bind(R.id.ll_czfs)
    LinearLayout llCzfs;
    @Bind(R.id.ll_twenty)
    MRadioButton llTwenty;
    @Bind(R.id.ll_thirty)
    MRadioButton llThirty;
    @Bind(R.id.ll_fifty)
    MRadioButton llFifty;
    @Bind(R.id.ll_hundred)
    MRadioButton llHundred;
    @Bind(R.id.et_je)
    EditText etJe;
    @Bind(R.id.btn_cz)
    Button btnCz;
    FragmentRechargePresenter presenter;
    //充值金额有小数还是都是整数，微信支付与支付宝测试，界面与逻辑的完善


    @Override
    public int getLayoutId() {
        return R.layout.fragment_recharge;
    }

    private int type=0;

    @Override
    public void initViews() {
        //支付宝沙盒环境
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        EventBus.getDefault().register(this);//在当前界面注册一个订阅者

        rgRechargeType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_wx:
                        type = 0;
                        break;
                    case R.id.rb_zfb:
                        type = 1;
                        break;
                }
            }
        });


        mRadioGroup.setOnCheckedChangeListener(new MRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View group, int checkedId) {
                switch (checkedId) {
                    case R.id.ll_twenty:
                        LogUtil.e("切换20");
                        break;
                    case R.id.ll_thirty:
                        LogUtil.e("切换30");
                        break;
                    case R.id.ll_fifty:
                        LogUtil.e("切换50");
                        break;
                    case R.id.ll_hundred:
                        LogUtil.e("切换100");
                        break;
                }
            }
        });

    }

    @Override
    public void initData() {
        presenter=new FragmentRechargePresenter();
        presenter.setMvpView(this,"");


        presenter.quePayInfo();
    }

    @OnClick(R.id.btn_cz)
    public void onViewClicked() {

//        ToastUtils.showShortToast("金额="+Double.parseDouble(etJe.getText().toString()));
        presenter.getPayInfo(type+"","2", Double.parseDouble(etJe.getText().toString()));

    }

    //查询阅读币成功
    @Override
    public void querySuccess(QueryUpayBean queryUpayBean) {

        boolean isLogin=SharePrefUtil.getBoolean(getActivity(), "isLogin", false);
        String userBean= SharePrefUtil.getString(getActivity(),"user","");
        String userName="";
        if(!userBean.equals("")){
            UserBean userBean1=UserBean.objectFromData(userBean);
            userName=userBean1.getUserName();
        }

        if(isLogin){
            if(queryUpayBean.isSuccess()){
                tvAccount.setText("充值账号 "+userName);
                tvBalance.setText("账号余额 "+queryUpayBean.getData().getGoldNumber()+"阅读币");
            }else{
                tvAccount.setText("充值账号 "+userName);
                tvBalance.setText("账号余额 0阅读币");
            }
        }else{
            tvAccount.setText("充值账号 游客");
            tvBalance.setText("账号余额 0阅读币");
        }

    }

    //获取支付宝订单成功
    @Override
    public void getAplipayDataSuccess(AplipayOrderBean data) {

        if(data.isSuccess()){
            PaymentHelper helper=new PaymentHelper();
//            helper.startAliPay(getActivity(),data.getData().toString());
            helper.startAliPay(getActivity(),data.getData().getPayCode().toString());
        }else{
            ToastUtils.showShortToast(data.getMessage());
        }

    }

    //获取微信订单成功
    @Override
    public void getWxDataSuccess(WxOrderBean data) {
        if(data.isSuccess()){
            PaymentHelper helper=new PaymentHelper();
            helper.startWeChatPay(getActivity(),data.getData().getPayCode());
        }else{
            ToastUtils.showShortToast(data.getMessage());
        }
    }

    //失败
    @Override
    public void fail(String message) {
        LogUtil.e("支付message:"+message);
    }

    //无网络
    @Override
    public void noConnectInternet() {
        ToastUtils.showShortToast("网络错误，请检查网络");
    }



    @Subscribe          //订阅事件FirstEvent,EventBus接收消息
    public void onEventMainThread(String event) {
        if(event.equals("支付成功")){
            LogUtil.e("执行了onEventMainThread方法支付成功");
            presenter.quePayInfo();
        }
    }




}
