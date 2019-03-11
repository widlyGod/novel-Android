package com.novel.cn.newui.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
import rx.functions.Action1;
import rx.functions.Func2;

/**登录界面
 * Created by jackieli on 2019/3/4.
 */

public class LoginActivity extends AutoLayoutActivity implements LoginContract.View{


    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.toolbar)
    TextView toolbar;
    @Bind(R.id.appbar)
    RelativeLayout appbar;
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
    @Bind(R.id.tv_ljzc)
    TextView tv_ljzc;
    @Bind(R.id.tv_forpw)
    TextView tv_forpw;
    @Bind(R.id.tv_messagelogin)
    TextView tv_messagelogin;
    @Bind(R.id.btn_LoginOrRes)
    Button btn_LoginOrRes;
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
        setContentView(R.layout.activity_newlogin);
        ButterKnife.bind(this);


//        View decor = this.getWindow().getDecorView();
//        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

//      decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

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

        etListenerVoid();

    }




    @OnClick({R.id.iv_left, R.id.iv_qq, R.id.iv_wx, R.id.iv_wb,R.id.tv_ljzc,R.id.tv_forpw,R.id.btn_LoginOrRes})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left: {//返回键
                finish();
            }break;
            case R.id.iv_qq: {//qq
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                loginwx(qq, 0);
            }break;
            case R.id.iv_wx: {//wx
                Platform wx = ShareSDK.getPlatform(Wechat.NAME);
                loginwx(wx, 1);
            }break;
            case R.id.iv_wb: {//微博
                Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
                //  1、 新浪微博开放平台应用没有审核通过，不能用sso登陆，否则报错 加以下代码关闭
                weibo.SSOSetting(true);
                loginwx(weibo, 2);
            }break;
            case R.id.tv_forpw: {//忘记密码
                Intent intent=new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                startActivity(intent);
            }break;
            case R.id.btn_LoginOrRes: {//登录或注册按钮
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
            }break;
            case R.id.tv_ljzc: {//立即注册
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,0);
            }break;
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
                stringType="wx";
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

    }







    @Override
    public void loginSuccess(BaseObjectBean<UserBean> data) {
        //登录成功，保存用户信息
        if(dialog_loading.isShowing()){
            dialog_loading.dismiss();
        }

        if(data.isSuccess()){
            SharePrefUtil.saveBoolean(LoginActivity.this, "isLogin", true);

            LogUtil.e("登录保存sessionId="+data.getData().getSessionId());
            SharePrefUtil.saveString(this,"sessionId",data.getData().getSessionId());
            SharePrefUtil.saveString(getApplicationContext(),"user",new Gson().toJson(data.getData()));
            setResult(1);
            finish();
        }else{
            tv_messagelogin.setText(data.getMessage());
        }
    }

    @Override
    public void otherRegisterResponse(BaseBean data) {

    }

    @Override
    public void otherLoginResponse(BaseObjectBean<UserBean> data, String type, String opid, String sex, String face) {
        if(dialog_loading.isShowing()){
            dialog_loading.dismiss();
        }

        if(data.isSuccess()){
            SharePrefUtil.saveBoolean(LoginActivity.this, "isLogin", true);
            SharePrefUtil.saveString(this,"sessionId",data.getData().getSessionId());
            SharePrefUtil.saveString(getApplicationContext(),"user",new Gson().toJson(data.getData()));
            setResult(1);
            finish();
        }else {
            tv_messagelogin.setText(data.getMessage());
        }

    }

    @Override
    public void registerSuccess(BaseObjectBean<UserBean> data) {
    }
    @Override
    public void sendCodeSuccess(BaseBean data) {
    }
    @Override
    public void forgetPwdSuccess(BaseBean data) {
    }
    @Override
    public void fail(String message) {
        if(dialog_loading.isShowing()){
            dialog_loading.dismiss();
        }
        tv_messagelogin.setText(message);
    }

    @Override
    public void noConnectInternet() {
        if(dialog_loading.isShowing()){
            dialog_loading.dismiss();
        }
        ToastUtils.showShortToast("网络错误，请检查网络");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0 && resultCode==1){//从注册界面回来的
            LogUtil.e("注册成功 销毁界面");
            finish();
        }
    }
}
