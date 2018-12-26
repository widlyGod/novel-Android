package com.novel.cn.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.novel.cn.R;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BaseObjectBean;
import com.novel.cn.model.entity.UserBean;
import com.novel.cn.persenter.Contract.LoginContract;
import com.novel.cn.persenter.PresenterClass.LoginPresenter;
import com.novel.cn.util.PartsUtil;
import com.novel.cn.util.ToastUtils;
import com.novel.cn.view.wight.Dialog_Loading;
import com.zhy.autolayout.AutoLayoutActivity;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func3;
import rx.functions.Func5;

/**
 * 忘记密码
 * Created by jackieli on 2018/12/25.
 */

public class ForgetPasswordActivity extends AutoLayoutActivity implements LoginContract.View {

    @Bind(R.id.iv_left)
    ImageView ivLeft;
    @Bind(R.id.toolbar)
    TextView toolbar;
    @Bind(R.id.appbar)
    RelativeLayout appbar;
    @Bind(R.id.et_username2)
    TextInputEditText etUsername2;
    @Bind(R.id.tl_username2)
    TextInputLayout tlUsername2;
    @Bind(R.id.et_yzm)
    TextInputEditText etYzm;
    @Bind(R.id.tv_sendyzm)
    TextView tvSendyzm;
    @Bind(R.id.tl_yzm)
    RelativeLayout tlYzm;
    @Bind(R.id.et_pw2)
    TextInputEditText etPw2;
    @Bind(R.id.tl_pw2)
    TextInputLayout tlPw2;
    @Bind(R.id.btn_commit)
    Button btnCommit;
    @Bind(R.id.tv_messagelogin)
    TextView tvMessagelogin;
    private LoginPresenter presenter;
    public Dialog_Loading dialog_loading;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpw);
        ButterKnife.bind(this);

        presenter = new LoginPresenter();
        presenter.setMvpView(this, "");
        dialog_loading = new Dialog_Loading(this, R.style.Dialog);
        dialog_loading.setCancelable(false);
        etListenerVoid();

    }


    //监听方法
    private void etListenerVoid() {
        Observable<CharSequence> obetUsername2 = RxTextView.textChanges(etUsername2);
        Observable<CharSequence> obetPw2 = RxTextView.textChanges(etPw2);
        Observable<CharSequence> obetYzmx = RxTextView.textChanges(etYzm);
        //使用combineLatest操作符,传入我们创建的那些监听   可以多个
        Observable.combineLatest(obetUsername2, obetPw2,obetYzmx,
                new Func3<CharSequence, CharSequence,CharSequence,  Boolean>() {
                    @Override
                    public Boolean call(CharSequence o, CharSequence o2, CharSequence o3) {
                        boolean isWrite = !TextUtils.isEmpty(etUsername2.getText().toString().trim())
                                && !TextUtils.isEmpty(etPw2.getText().toString().trim())
                                && !TextUtils.isEmpty(etYzm.getText().toString().trim());
                        return isWrite;
                    }
                }).subscribe(new Action1<Boolean>() {
            @Override
            public void call(Boolean aBoolean) {
                //在这个地方可以实现是否满足过滤逻辑
                if (aBoolean) {
                    //内容不为空  设置可以点击并且设置背景  tv_messagelogin
                    btnCommit.setClickable(true);
                    btnCommit.setBackground(ContextCompat.getDrawable(ForgetPasswordActivity.this, R.drawable.shape_login_conbtn));
                    String userName = etUsername2.getText().toString();
                    String pw = etPw2.getText().toString();
                    String etYzmx = etYzm.getText().toString();
                    if (PartsUtil.isEmail(userName) == false ) {
                        tvMessagelogin.setText("用户名格式不正确");
                    }else if (pw.length()<6) {
                        tvMessagelogin.setText("密码过短，请输入6-12位密码");
                    }else{//正确
                        tvMessagelogin.setText("");
                    }
                } else {
                    //内容为空
                    btnCommit.setClickable(false);
                    btnCommit.setBackground(ContextCompat.getDrawable(ForgetPasswordActivity.this, R.drawable.shape_login_btn));
                    tvMessagelogin.setText("");
                }
            }
        });
    }


    @OnClick({R.id.iv_left, R.id.btn_commit, R.id.tv_sendyzm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_left: {
                finish();
            }
            break;
            case R.id.tv_sendyzm: {
                String email = etUsername2.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    tvMessagelogin.setText("请输入用户名");
                } else if (PartsUtil.isEmail(email) == false) {
                    tvMessagelogin.setText("用户名格式不正确");
                } else {
                    dialog_loading.show();
                    presenter.sendCode("novelUserService/user/valiAccount",email);
                }
            }
            break;
            case R.id.btn_commit: {
                String userName = etUsername2.getText().toString();
                String pw = etPw2.getText().toString();
                String yzm = etYzm.getText().toString();
                if (PartsUtil.isEmail(userName) == false ) {
                    tvMessagelogin.setText("用户名格式不正确");
                }else if (pw.length()<6) {
                    tvMessagelogin.setText("密码过短，请输入6-12位密码");
                }else if(TextUtils.isEmpty(yzm)){
                    tvMessagelogin.setText("请输入验证码");
                } else{//请求接口
                    dialog_loading.show();
                    tvMessagelogin.setText("");
                    presenter.forgetPwd(pw,userName,yzm);
                }
            }
            break;
        }
    }


    @Override
    public void loginSuccess(BaseObjectBean<UserBean> data) {
    }
    @Override
    public void otherRegisterResponse(BaseBean data) {
    }

    @Override
    public void otherLoginResponse(BaseObjectBean<UserBean> data, String type, String opid, String sex, String face) {

    }

    @Override
    public void registerSuccess(BaseObjectBean<UserBean> data) {
    }

    int count = 180;

    @Override
    public void sendCodeSuccess(BaseBean data) {
        if(dialog_loading.isShowing()){
            dialog_loading.dismiss();
        }

        ToastUtils.showShortToast("发送成功");
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
        if(dialog_loading.isShowing()){
            dialog_loading.dismiss();
        }

        if(data.isSuccess()){
            finish();
            //主页修改缓存密码逻辑
        }else{
            tvMessagelogin.setText(data.getMessage());
        }

    }

    @Override
    public void fail(String message) {
        if(dialog_loading.isShowing()){
            dialog_loading.dismiss();
        }
        tvMessagelogin.setText(message);
    }

    @Override
    public void noConnectInternet() {
        if(dialog_loading.isShowing()){
            dialog_loading.dismiss();
        }
        ToastUtils.showShortToast("网络错误，请检查网络");
    }
}
