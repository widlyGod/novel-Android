package com.novel.cn.persenter.PresenterClass;

import com.novel.cn.model.api.ApiClient;
import com.novel.cn.model.api.BaseSubscriber;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BaseObjectBean;
import com.novel.cn.model.entity.UserBean;
import com.novel.cn.model.util.HttpUtils;
import com.novel.cn.persenter.Contract.LoginContract;
import com.novel.cn.util.JsonUtils;
import com.novel.cn.util.LogUtil;

import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**登录注册presen
 * Created by jackieli on 2018/12/25.
 */

public class LoginPresenter implements LoginContract.Presenter {

    private LoginContract.View view;

    @Override
    public Object loadCache() {
        return null;
    }

    @Override
    public void setMvpView(LoginContract.View view, String cacheKey) {
        this.view=view;
    }

    @Override
    public void sendCode(String url, String keyword) {
        HttpUtils.httpInterceptor.isAddHead(false);

        JsonUtils jsonUtils = new JsonUtils();
        jsonUtils.addField("keyword", keyword);
        String bodyString = jsonUtils.build().toString();
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),bodyString);
        ApiClient.service.sendCode(url,body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())         //销毁时销毁Retrofit
                .subscribe(new BaseSubscriber<BaseBean>() {
                    @Override
                    protected void noConnectInternet() {
                        view.noConnectInternet();
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("tag","sendCode错误="+e.getMessage());
                        view.fail(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        LogUtil.e("tag","sendCode数据="+baseBean);
                        view.sendCodeSuccess(baseBean);
                    }
                });
    }

    @Override
    public void register(String nickName, String keyword, String userPassword,String code) {
        HttpUtils.httpInterceptor.isAddHead(false);

        JsonUtils jsonUtils = new JsonUtils();
        jsonUtils.addField("nickName", nickName);
        jsonUtils.addField("keyword", keyword);
        jsonUtils.addField("userPassword", userPassword);
        jsonUtils.addField("code", code);
        String bodyString = jsonUtils.build().toString();
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),bodyString);
        ApiClient.service.register(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<BaseObjectBean<UserBean>>() {

                    @Override
                    protected void noConnectInternet() {
                        view.noConnectInternet();
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("tag","register错误="+e.getMessage());
                        view.fail(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseObjectBean<UserBean> baseBean) {
                        LogUtil.e("tag","register数据="+baseBean);
                        view.registerSuccess(baseBean);
                    }
                });
    }

    @Override
    public void login(String keyword, String userPwd) {

        HttpUtils.httpInterceptor.isAddHead(false);

        JsonUtils jsonUtils = new JsonUtils();
        jsonUtils.addField("keyword", keyword);
        jsonUtils.addField("userPassword", userPwd);
        String bodyString = jsonUtils.build().toString();
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),bodyString);
        ApiClient.service.login(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<BaseObjectBean<UserBean>>() {

                    @Override
                    protected void noConnectInternet() {
                        view.noConnectInternet();
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("tag","login错误="+e.getMessage());
                        view.fail(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseObjectBean<UserBean> baseBean) {
                        LogUtil.e("tag","login数据="+baseBean);
                        view.loginSuccess(baseBean);
                    }
                });
    }

    @Override
    public void otherLogin(String opid, String type, String sex, String face) {

    }

    @Override
    public void otherRegister(String type, String opid, String sex, String face) {

    }
    //忘记密码
    @Override
    public void forgetPwd(String userPassword, String userEmail, String code) {
        HttpUtils.httpInterceptor.isAddHead(false);

        JsonUtils jsonUtils = new JsonUtils();
        jsonUtils.addField("userPassword", userPassword);
        jsonUtils.addField("userEmail", userEmail);
        jsonUtils.addField("code", code);
        String bodyString = jsonUtils.build().toString();
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),bodyString);
        ApiClient.service.forgetPwd(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<BaseBean>() {

                    @Override
                    protected void noConnectInternet() {
                        view.noConnectInternet();
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("tag","forgetPwd错误="+e.getMessage());
                        view.fail(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseBean baseBean) {
                        LogUtil.e("tag","forgetPwd数据="+baseBean);
                        view.forgetPwdSuccess(baseBean);
                    }
                });
    }
}
