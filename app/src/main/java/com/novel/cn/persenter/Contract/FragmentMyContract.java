package com.novel.cn.persenter.Contract;


import com.novel.cn.base.BaseComtract;
import com.novel.cn.base.BasePresenter;
import com.novel.cn.base.BaseView;
import com.novel.cn.model.entity.BaseListObjectBean;
import com.novel.cn.model.entity.HomeReturnBean;
import com.novel.cn.model.entity.PersonDataBean;

/**
 *
 */

public interface FragmentMyContract extends BaseComtract {


    interface View extends BaseView {
        void getPersonDataSuccess(PersonDataBean data);
        void fail(String message);
        void noConnectInternet();
    }


    interface Presenter extends BasePresenter<View> {
        void getPersonData();
    }

}
