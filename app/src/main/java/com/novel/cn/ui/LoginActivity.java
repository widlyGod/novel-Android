package com.novel.cn.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.novel.cn.R;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BaseObjectBean;
import com.novel.cn.model.entity.UserBean;
import com.novel.cn.persenter.Contract.LoginContract;
import com.novel.cn.persenter.PresenterClass.LoginPresenter;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.PartsUtil;
import com.novel.cn.util.SharePrefUtil;
import com.novel.cn.util.ToastUtils;
import com.novel.cn.view.wight.Dialog_Loading;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Action5;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.functions.Func5;

/**
 * 登录界面
 * Created by jackieli on 2018/12/20.
 */

public class LoginActivity extends AutoLayoutActivity implements LoginContract.View{

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.toolbar)
    TextView toolbar;
    @Bind(R.id.appbar)
    RelativeLayout appbar;
    @Bind(R.id.tablayout)
    TabLayout tablayout;
    @Bind(R.id.et_username)
    TextInputEditText etUsername;
    @Bind(R.id.tl_username)
    TextInputLayout tlUsername;
    @Bind(R.id.et_pw)
    TextInputEditText etPw;
    //    ClearEditText etPs;
    @Bind(R.id.tl_pw)
    TextInputLayout tlPw;
    @Bind(R.id.ll_zhdl)
    LinearLayout llZhdl;
    @Bind(R.id.ll_yxzc)
    LinearLayout llYxzc;
    @Bind(R.id.ll_dsf)
    LinearLayout llDsf;
    @Bind(R.id.iv_qq)
    ImageButton ivQq;
    @Bind(R.id.iv_wx)
    ImageButton ivWx;
    @Bind(R.id.iv_wb)
    ImageButton ivWb;
    @Bind(R.id.login_ll)
    LinearLayout loginLl;
    @Bind(R.id.et_nicen)
    TextInputEditText etNicen;
    @Bind(R.id.tl_nicen)
    TextInputLayout tlNicen;
    @Bind(R.id.et_username2)
    TextInputEditText etUsername2;
    @Bind(R.id.tl_username2)
    TextInputLayout tlUsername2;
    @Bind(R.id.et_pw2)
    TextInputEditText etPw2;
    @Bind(R.id.tl_pw2)
    TextInputLayout tlPw2;
    @Bind(R.id.et_conps)
    TextInputEditText etConps;
    @Bind(R.id.tl_conpw)
    TextInputLayout tlConpw;
    @Bind(R.id.et_yzm)
    TextInputEditText etYzm;
    @Bind(R.id.tv_sendyzm)
    TextView tvSendyzm;
    @Bind(R.id.tv_yhzcxy)
    TextView tv_yhzcxy;
    @Bind(R.id.tv_forpw)
    TextView tv_forpw;
    @Bind(R.id.tl_yzm)
    RelativeLayout tlYzm;
    @Bind(R.id.ll_zhucexy)
    LinearLayout llZhucexy;
    @Bind(R.id.tv_messagelogin)
    TextView tv_messagelogin;
    @Bind(R.id.tv_messageregist)
    TextView tv_messageregist;

    @Bind(R.id.btn_LoginOrRes)
    Button btn_LoginOrRes;

    @Bind(R.id.cb_zhuce)
    CheckBox cb_zhuce;
    //0登录按钮，1注册按钮
    private int btnType=0;
    //是否点击了获取验证码
    private boolean isClickYzm=false;
    private LoginPresenter presenter;
    public Dialog_Loading dialog_loading;
    int count = 180;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Log.e("tag", "程序运行");

        presenter = new LoginPresenter();
        presenter.setMvpView(this, "");
        dialog_loading = new Dialog_Loading(this, R.style.Dialog);
        dialog_loading.setCancelable(false);
        String userBean= SharePrefUtil.getString(getApplicationContext(),"user","");
        if(!userBean.equals("")){
            UserBean userBean1=UserBean.objectFromData(userBean);
            if(userBean1.getUserEmail()!=null){
                etUsername.setText(userBean1.getUserEmail());
            }
        }


        tablayout.addTab(tablayout.newTab().setText("账号登录"));
        tablayout.addTab(tablayout.newTab().setText("邮箱注册"));
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setSwitchVis(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        cb_zhuce.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(errorText.equals("请勾选阅读注册协议")&& b==true){
                    errorText="";
                }
            }
        });



        etListenerVoid();
    }


    //设置切换顶部界面
    private void setSwitchVis(int position) {
        switch (position) {
            case 0: {
                btnType=0;
                llZhdl.setVisibility(View.VISIBLE);//登录输入框
                tv_forpw.setVisibility(View.VISIBLE);//忘记密码
                llZhucexy.setVisibility(View.GONE);//注册协议
                llYxzc.setVisibility(View.GONE);//注册输入框
                tv_messagelogin.setVisibility(View.VISIBLE);
                tv_messageregist.setVisibility(View.GONE);
            }
            break;
            case 1: {
                btnType=1;
                llYxzc.setVisibility(View.VISIBLE);//注册输入框
                llZhucexy.setVisibility(View.VISIBLE);//注册协议
                tv_forpw.setVisibility(View.GONE);//忘记密码
                llZhdl.setVisibility(View.GONE);//登录输入框
                tv_messagelogin.setVisibility(View.GONE);
                tv_messageregist.setVisibility(View.VISIBLE);
            }
            break;
        }
    }




    @OnClick({R.id.iv_left, R.id.iv_qq, R.id.iv_wx, R.id.iv_wb,R.id.tv_sendyzm,R.id.tv_yhzcxy,R.id.tv_forpw,R.id.btn_LoginOrRes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left: {//返回键
                finish();
                Intent intent=new Intent(this, MainActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.iv_qq: {//qq
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                loginwx(qq, 0);
            }
            break;
            case R.id.iv_wx: {//微信
                Platform wx = ShareSDK.getPlatform(Wechat.NAME);
                loginwx(wx, 1);
            }
            break;
            case R.id.iv_wb: {//微博
                Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                //  1、 新浪微博开放平台应用没有审核通过，不能用sso登陆，否则报错 加以下代码关闭
                //        weibo.SSOSetting(true);
                loginwx(weibo, 2);
            }
            break;
            case R.id.tv_sendyzm: {//发送注册验证码
                String email=etUsername2.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    tv_messageregist.setText("请输入用户名");
                }else if (PartsUtil.isEmail(email) == false ) {
                    tv_messageregist.setText("用户名格式不正确");
                }else{
                    dialog_loading.show();
                    presenter.sendCode("novelUserService/user/sendCode",email);
                }
            }
            break;
            case R.id.tv_yhzcxy: {//注册协议
                ToastUtils.showShortToast("开发中");
            }
            break;
            case R.id.tv_forpw: {//忘记密码
                Intent intent=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }
            break;
            case R.id.btn_LoginOrRes: {//登录或注册按钮
                if(btnType==0){//登录界面
                    String userName = etUsername.getText().toString();
                    String pw = etPw.getText().toString();
                    if (PartsUtil.isEmail(userName) == false ) {
                        tv_messagelogin.setText("用户名格式不正确");
                    }else if (pw.length()<6) {
                        tv_messagelogin.setText("密码过短，请输入6-12位密码");
                    }else{//请求接口
                        dialog_loading.show();
                        tv_messagelogin.setText("");
                        presenter.login(userName,pw);
                    }
                }else{//注册界面
                    String userName = etUsername2.getText().toString();
                    String pw = etPw2.getText().toString();
                    String conps = etConps.getText().toString();
                    if (PartsUtil.isEmail(userName) == false ) {
                        tv_messagelogin.setText("用户名格式不正确");
                    }else if (pw.length()<6) {
                        tv_messagelogin.setText("密码过短，请输入6-12位密码");
                    }else if (!conps.equals(pw)) {
                        tv_messagelogin.setText("两次密码输入不一致");
                    }else if (cb_zhuce.isChecked()==false) {
                        tv_messagelogin.setText("请勾选阅读注册协议");
                    }else{//正确
                        dialog_loading.show();
                        tv_messagelogin.setText("");
                        presenter.register(etNicen.getText().toString().trim(),userName,pw,etYzm.getText().toString());
                    }
                }
            }
            break;
        }
    }

    //先调登录，登录如果没有该用户，则调注册，有该用户直接登录
    //登陆微信
    private void loginwx(Platform other, final int type) {
        String stringType=type+"";
        switch (type){
            case 0:
                stringType="qq";
                break;
            case 1:
                stringType="微信";
                break;
            case 2:
                stringType="微博";
                break;
        }
        final String stringTypex=stringType;
//        other.SSOSetting(false);//优先选择调用手机客户端界面   true是网页界面
        other.setPlatformActionListener(new PlatformActionListener() {
            //当第三方授权登录成功，把SharedPreferences的值改变，并获取用户信息，跳转主页面
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                PlatformDb db = platform.getDb();
                String accessToken = db.getToken(); // 获取授权token
                final String openId = db.getUserId(); // 获取用户在此平台的ID
                final String username = db.getUserName();
                String sex=db.getUserGender();
                final String usericon = db.getUserIcon();
                //微博回调=2277089710 jackie_lixx http://tva1.sinaimg.cn/crop.0.0.852.852.1024/87b9a1aejw8f3w2ca85bwj20no0np76z.jpg  m
                LogUtil.e("tag", "第三方登陆回调=" + openId+username + usericon+sex);
                platform.getDb().exportData();
                // 1或0   男,女
                if(sex.equals("m")){
                    sex="1";
                }else{
                    sex="0";
                }
                final String finalSex = sex;
                //当前线程与主线程不一致，所以要在主线程运行
                LoginActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        dialog_loading.show();
                        presenter.otherLogin(openId,username,usericon,finalSex,stringTypex);
                    }
                });
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                String message = throwable.getMessage();
                LogUtil.e("第三方登陆回调错误信息:"+message);
                String localizedMessage = throwable.getLocalizedMessage();
                final int typex = type;
                final int ix = i;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (typex == 0 && ix == 1) {
                            Toast.makeText(LoginActivity.this, "qq客户端不存在", Toast.LENGTH_SHORT).show();
                        } else if (typex == 1 && ix == 8) {
                            Toast.makeText(LoginActivity.this, "微信客户端不存在", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void onCancel(Platform platform, int i) {
                LogUtil.e("第三方登陆回调onCancel:");
            }
        });
        if(other.isClientValid()){
            //{"msg":"the user modify password wrong","ret":-73}
            other.removeAccount(true);
        }
        other.showUser(null);//验证加获取用户信息
    }




    private String errorText="";

    //监听事件
    private void etListenerVoid() {
        //创建输入框的监听
        Observable<CharSequence> etAmoutMoney = RxTextView.textChanges(etUsername);
        //创建输入框的监听
        Observable<CharSequence> tvAddress = RxTextView.textChanges(etPw);
        //使用combineLatest操作符,传入我们创建的那些监听   可以多个
        Observable.combineLatest(etAmoutMoney, tvAddress, new Func2<CharSequence, CharSequence, Boolean>() {
            @Override
            public Boolean call(CharSequence charSequence, CharSequence charSequence2) {
                //在这个地方返回的逻辑就是那些输入框的逻辑
                return !TextUtils.isEmpty(etUsername.getText().toString().trim()) && !TextUtils.isEmpty(etPw.getText().toString().trim());
            }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                //在这个地方可以实现是否满足过滤逻辑
                if (aBoolean) {
                    //内容不为空  设置可以点击并且设置背景  tv_messagelogin
                    btn_LoginOrRes.setClickable(true);
                    btn_LoginOrRes.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.shape_login_conbtn));
                    String userName = etUsername.getText().toString();
                    String pw = etPw.getText().toString();
                    if (PartsUtil.isEmail(userName) == false ) {
                        tv_messagelogin.setText("用户名格式不正确");
//                        errorText="用户名格式不正确";
                    }else if (pw.length()<6) {
                        tv_messagelogin.setText("密码过短，请输入6-12位密码");
//                        errorText="密码过短，请输入6-12位密码";
                    }else{
                        tv_messagelogin.setText("");
//                        errorText="";
                    }
                } else {
                    //内容为空
                    btn_LoginOrRes.setClickable(false);
                    btn_LoginOrRes.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.shape_login_btn));
                    tv_messagelogin.setText("");
//                    errorText="";
                }
            }
        });

        //创建输入框的监听  et_yzm
        Observable<CharSequence> obetNicen = RxTextView.textChanges(etNicen);
        Observable<CharSequence> obetUsername2 = RxTextView.textChanges(etUsername2);
        Observable<CharSequence> obetPw2 = RxTextView.textChanges(etPw2);
        Observable<CharSequence> obetConps = RxTextView.textChanges(etConps);
        Observable<CharSequence> obetYzm = RxTextView.textChanges(etYzm);
        //使用combineLatest操作符,传入我们创建的那些监听   可以多个
        Observable.combineLatest(obetNicen, obetUsername2,obetPw2,obetConps,obetYzm,
                new Func5<CharSequence, CharSequence,CharSequence, CharSequence,CharSequence,  Boolean>() {
                    @Override
                    public Boolean call(CharSequence o, CharSequence o2, CharSequence o3, CharSequence o4, CharSequence o5) {
                        boolean isWrite = !TextUtils.isEmpty(etNicen.getText().toString().trim())
                                && !TextUtils.isEmpty(etUsername2.getText().toString().trim())
                                && !TextUtils.isEmpty(etPw2.getText().toString().trim())
                                && !TextUtils.isEmpty(etConps.getText().toString().trim())
                                && !TextUtils.isEmpty(etYzm.getText().toString().trim());
                        return isWrite;
                    }
        }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                //在这个地方可以实现是否满足过滤逻辑
                if (aBoolean) {
                    //内容不为空  设置可以点击并且设置背景  tv_messagelogin
                    btn_LoginOrRes.setClickable(true);
                    btn_LoginOrRes.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.shape_login_conbtn));
                    String userName = etUsername2.getText().toString();
                    String pw = etPw2.getText().toString();
                    String conps = etConps.getText().toString();
                    if (PartsUtil.isEmail(userName) == false ) {
                        tv_messageregist.setText("用户名格式不正确");
                        errorText="用户名格式不正确";
                    }else if (pw.length()<6) {
                        tv_messageregist.setText("密码过短，请输入6-12位密码");
                        errorText="密码过短，请输入6-12位密码";
                    }else if (!conps.equals(pw)) {
                        tv_messageregist.setText("两次密码输入不一致");
                        errorText="两次密码输入不一致";
                    }else if (cb_zhuce.isChecked()==false) {
                        tv_messageregist.setText("请勾选阅读注册协议");
                        errorText="请勾选阅读注册协议";
                    }else{//正确
                        tv_messageregist.setText("");
                        errorText="";
                    }
                } else {
                    //内容为空
                    btn_LoginOrRes.setClickable(false);
                    btn_LoginOrRes.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.shape_login_btn));
                    tv_messageregist.setText("");
                    errorText="";
                }
            }
        });

    }

    //登录成功
    @Override
    public void loginSuccess(BaseObjectBean<UserBean> data) {
        //登录成功，保存用户信息
        if(dialog_loading.isShowing()){
            dialog_loading.dismiss();
        }

        if(data.isSuccess()){
            SharePrefUtil.saveBoolean(LoginActivity.this, "isLogin", true);
            SharePrefUtil.saveString(this,"sessionId",data.getData().getSessionId());
            SharePrefUtil.saveString(getApplicationContext(),"user",new Gson().toJson(data.getData()));
            finish();
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            tv_messagelogin.setText(data.getMessage());
        }
    }
    //第三方注册成功
    @Override
    public void otherRegisterResponse(BaseBean data) {


    }

    //第三方登录成功
    @Override
    public void otherLoginResponse(BaseObjectBean<UserBean> data, String type, String opid, String sex, String face) {
        if(dialog_loading.isShowing()){
            dialog_loading.dismiss();
        }

        if(data.isSuccess()){
            SharePrefUtil.saveBoolean(LoginActivity.this, "isLogin", true);
            SharePrefUtil.saveString(this,"sessionId",data.getData().getSessionId());
            SharePrefUtil.saveString(getApplicationContext(),"user",new Gson().toJson(data.getData()));
            finish();
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            if(btnType==0){
                tv_messagelogin.setText(data.getMessage());
            }else{
                tv_messageregist.setText(data.getMessage());
            }
        }

    }
    //注册成功
    @Override
    public void registerSuccess(BaseObjectBean<UserBean> data) {
        if(dialog_loading.isShowing()){
            dialog_loading.dismiss();
        }
        //代码保存用户信息
//        String userName = etUsername2.getText().toString();
//        String pw = etPw2.getText().toString();
//        presenter.login(userName,pw);

        if(data.isSuccess()){
            SharePrefUtil.saveBoolean(LoginActivity.this, "isLogin", true);
            SharePrefUtil.saveString(this,"sessionId",data.getData().getSessionId());
            SharePrefUtil.saveString(getApplicationContext(),"user",new Gson().toJson(data.getData()));
            finish();
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        }else{
            tv_messagelogin.setText(data.getMessage());
        }
    }
    //发送验证码成功
    @Override
    public void sendCodeSuccess(BaseBean data) {
        if(dialog_loading.isShowing()){
            dialog_loading.dismiss();
        }

        if(data.isSuccess()==false){
            ToastUtils.showShortToast(data.getMessage());
        }else{
            ToastUtils.showShortToast("发送成功");
        }

        // 0秒延迟，1秒后计步器+1
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .take(count + 1) // 默认是从10开始的10 9 8 7 6 5 4 3 2 ## 1是不会执行的，为啥呢，因为到1的时候就已经走完了，应该去更新UI了，所以做一个+1的处理
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) { // 利用map转换下，把1 2 3 4 5 倒置过来变成 10 9 8 7 6 5
                        return count - aLong;
                    }
                }).doOnSubscribe(new Action0() { // 当计时器开始的时候
            @Override
            public void call() {
                tvSendyzm.setClickable(false);
            }
        }).observeOn(AndroidSchedulers.mainThread()) // 计时器默认是跑在子线程的，所以需要切换线程
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() { // 结束时
                        tvSendyzm.setClickable(true);
                        tvSendyzm.setText("重新发送");
                    }
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }
                    @Override
                    public void onNext(Long aLong) {
                        tvSendyzm.setText(aLong + "秒");
                    }
                });

    }

    @Override
    public void forgetPwdSuccess(BaseBean data) {
    }

    //失败信息
    @Override
    public void fail(String message) {
        if(dialog_loading.isShowing()){
            dialog_loading.dismiss();
        }
        if(btnType==0){
            tv_messagelogin.setText(message);
        }else{
            tv_messageregist.setText(message);
        }
    }

    //无网络
    @Override
    public void noConnectInternet() {
        if(dialog_loading.isShowing()){
            dialog_loading.dismiss();
        }
        ToastUtils.showShortToast("网络错误，请检查网络");
    }

}
