package com.novel.cn.newui.mine.fragment;


import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.sdk.app.EnvUtils;
import com.novel.cn.R;
import com.novel.cn.alipay.PaymentHelper;
import com.novel.cn.base.BaseFragment;
import com.novel.cn.model.entity.AplipayOrderBean;
import com.novel.cn.model.entity.QueryUpayBean;
import com.novel.cn.model.entity.UserBean;
import com.novel.cn.model.entity.WxOrderBean;
import com.novel.cn.persenter.Contract.FragmentRechargeContract;
import com.novel.cn.persenter.PresenterClass.FragmentRechargePresenter;
import com.novel.cn.util.CalculationUtil;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.SharePrefUtil;
import com.novel.cn.util.ToastUtils;
import com.novel.cn.util.Utils;
import com.novel.cn.view.wight.CustomRadioButton;
import com.novel.cn.view.wight.MRadioButton;
import com.novel.cn.view.wight.MRadioGroup;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import butterknife.Bind;
import butterknife.OnClick;

/**支付的代码，可用
 * Created by jackieli on 2018/12/26.
 */

public class FragmentRecharge extends BaseFragment implements
        FragmentRechargeContract.View, View.OnTouchListener, View.OnFocusChangeListener {


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
    @Bind(R.id.tv_je)
    TextView tvJe;
    //充值金额最多输入两位小数，微信支付测试
    @Override
    public int getLayoutId() {
        return R.layout.fragment_recharge;
    }

    private int rctype = 0;     //充值类型
    private double czje = 0;    //充值金额

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
                        rctype = 0;
                        break;
                    case R.id.rb_zfb:
                        rctype = 1;
                        break;
                }
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new MRadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View group, int checkedId) {
                switch (checkedId) {
                    case R.id.ll_twenty:
                        clearFoucus(20);
                        break;
                    case R.id.ll_thirty:
                        clearFoucus(30);
                        break;
                    case R.id.ll_fifty:
                        clearFoucus(50);
                        break;
                    case R.id.ll_hundred:
                        clearFoucus(100);
                        break;
                }
            }
        });

        //输入框限制两位小数点输入，文字改变监听，输入框与选择按钮得切换
        etJe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                etJe.setFocusable(true);
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                String text=etJe.getText().toString();
                if(text.length()>0){
                    double jine=Double.parseDouble(text);
                    czje=jine;
                    tvJe.setText(jine*100+"阅读币"+CalculationUtil.givePoint(text));
                    setBtnIsCanClick(czje);
                }else {
                    setBtnIsCanClick(0);
                    tvJe.setText("0阅读币");
                }
            }
        });

        etJe.setOnFocusChangeListener(this);
        etJe.setOnTouchListener(this);

    }


    public void setBtnIsCanClick(double jine){
        if(jine>=1){
            btnCz.setClickable(true);
            btnCz.setText("确定（共"+czje+"元)");
            btnCz.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_login_conbtn));
        }else{
            btnCz.setClickable(false);
            btnCz.setText("确定");
            btnCz.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.shape_login_btn));
        }
    }


    //输入框有焦点，按钮选中，取消输入框焦点
    public void clearFoucus(double num){
        tvJe.setText("");
        etJe.setText("");
        etJe.clearFocus();//失去焦点
        etJe.setFocusable(false);
        czje = num;

        setBtnIsCanClick(czje);
    }

    //输入框获得焦点，取消按钮选中
    @Override
    public void onFocusChange(View view, boolean b) {
        if(b){
            setBtnIsCanClick(0);
//                    int radioButtonId = mRadioGroup.getCheckedRadioButtonId();
            for (int i=0;i<mRadioGroup.getChildCount();i++){
                MRadioButton mRadioButton= (MRadioButton) mRadioGroup.getChildAt(i);
                mRadioButton.setChecked(false);
            }
        }
    }

    //触摸输入框，重新获得焦点
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        etJe.setFocusable(true);
        etJe.setFocusableInTouchMode(true);
        etJe.requestFocus();
        etJe.findFocus();

        return false;
    }




    @Override
    public void initData() {
        presenter = new FragmentRechargePresenter();
        presenter.setMvpView(this, "");

        presenter.quePayInfo();

    }

    @OnClick(R.id.btn_cz)
    public void onViewClicked() {
        boolean isLogin = SharePrefUtil.getBoolean(getActivity(), "isLogin", false);
        if(isLogin){
            presenter.getPayInfo(rctype + "", "2", czje);
        }else{
            ToastUtils.showShortToast("请先登录账户");
        }
    }

    //查询阅读币成功
    @Override
    public void querySuccess(QueryUpayBean queryUpayBean) {

        boolean isLogin = SharePrefUtil.getBoolean(getActivity(), "isLogin", false);
        String userBean = SharePrefUtil.getString(getActivity(), "user", "");
        String userName = "";
        if (!userBean.equals("")) {
            UserBean userBean1 = UserBean.objectFromData(userBean);
            userName = userBean1.getUserName();
        }

        if (isLogin) {
            if (queryUpayBean.isSuccess()) {
                tvAccount.setText("充值账号 " + userName);
                tvBalance.setText("账号余额 " + queryUpayBean.getData().getGoldNumber() + "阅读币");
            } else {
                tvAccount.setText("充值账号 " + userName);
                tvBalance.setText("账号余额 0阅读币");
            }
        } else {
            tvAccount.setText("充值账号 游客");
            tvBalance.setText("账号余额 0阅读币");
        }

    }

    //获取支付宝订单成功
    @Override
    public void getAplipayDataSuccess(AplipayOrderBean data) {

        if (data.isSuccess()) {
            PaymentHelper helper = new PaymentHelper();
//            helper.startAliPay(getActivity(),data.getData().toString());
            helper.startAliPay(getActivity(), data.getData().getPayCode().toString());
        } else {
            ToastUtils.showShortToast(data.getMessage());
        }

    }

    //获取微信订单成功
    @Override
    public void getWxDataSuccess(WxOrderBean data) {
        if (data.isSuccess()) {
            PaymentHelper helper = new PaymentHelper();
            helper.startWeChatPay(getActivity(), data.getData().getPayCode());
        } else {
            ToastUtils.showShortToast(data.getMessage());
        }
    }

    //失败
    @Override
    public void fail(String message) {
        LogUtil.e("支付message:" + message);
    }

    //无网络
    @Override
    public void noConnectInternet() {
        ToastUtils.showShortToast("网络错误，请检查网络");
    }


    @Subscribe          //订阅事件FirstEvent,EventBus接收消息
    public void onEventMainThread(String event) {
        if (event.equals("支付成功")) {
            etJe.setText("");
            presenter.quePayInfo();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean isLogin = SharePrefUtil.getBoolean(getActivity(), "isLogin", false);
        if (isLogin){
            presenter.quePayInfo();
        }
    }

}
