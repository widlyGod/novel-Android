package com.novel.cn.ui.recharge.fragment;

import android.widget.Button;
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
import com.novel.cn.model.entity.WxOrderBean;
import com.novel.cn.persenter.Contract.FragmentRechargeContract;
import com.novel.cn.persenter.PresenterClass.FragmentRechargePresenter;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.ToastUtils;
import com.novel.cn.view.wight.CustomRadioButton;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jackieli on 2018/12/26.
 */

public class FragmentRecharge extends BaseFragment implements FragmentRechargeContract.View{

    @Bind(R.id.iv_returnHome)
    ImageView ivReturnHome;
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
    @Bind(R.id.ll_czfs)
    LinearLayout llCzfs;
    @Bind(R.id.ll_twenty)
    LinearLayout llTwenty;
    @Bind(R.id.ll_thirty)
    LinearLayout llThirty;
    @Bind(R.id.ll_fifty)
    LinearLayout llFifty;
    @Bind(R.id.ll_hundred)
    LinearLayout llHundred;
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
    }

    @Override
    public void initData() {
        presenter=new FragmentRechargePresenter();
        presenter.setMvpView(this,"");

    }

    @OnClick(R.id.btn_cz)
    public void onViewClicked() {

        presenter.getPayInfo(type+"","2", Integer.parseInt(etJe.getText().toString()));

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
//            PaymentHelper helper=new PaymentHelper();
//            helper.startWeChatPay(getActivity(),data.getData().getPayCode());
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


}
