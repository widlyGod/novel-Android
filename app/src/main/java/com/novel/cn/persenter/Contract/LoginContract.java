package com.novel.cn.persenter.Contract;


import com.novel.cn.base.BaseComtract;
import com.novel.cn.base.BasePresenter;
import com.novel.cn.base.BaseView;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BaseObjectBean;
import com.novel.cn.model.entity.UserBean;

/** 登录注册界面
 * Created by jackieli on 2018/forgetpstwo/1.
 */

public interface LoginContract extends BaseComtract {

    interface View extends BaseView {
        void loginSuccess(BaseObjectBean<UserBean> data);
        void otherRegisterResponse(BaseBean data);
        void otherLoginResponse(BaseBean data,String type,String opid,String sex, String face,String regId);

        void registerSuccess(BaseObjectBean<UserBean> data);
        void sendCodeSuccess(BaseBean data);
        void forgetPwdSuccess(BaseBean data);

        void fail(String message);
        void noConnectInternet();
    }


    interface Presenter extends BasePresenter<View> {
        void sendCode(String url, String keyword);
        void register(String nickName, String keyword, String userPassword,String code);
        void login(String keyword, String userPwd);
        void otherLogin(String opid, String type,String sex,String face);
        void otherRegister(String type,String opid,String sex, String face);
        void forgetPwd(String userPassword, String userEmail, String code);
    }

}
