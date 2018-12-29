package com.novel.cn.persenter.Contract;


import com.novel.cn.base.BaseComtract;
import com.novel.cn.base.BasePresenter;
import com.novel.cn.base.BaseView;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BaseListObjectBean;
import com.novel.cn.model.entity.BaseObjectBean;
import com.novel.cn.model.entity.HomeReturnBean;
import com.novel.cn.model.entity.UserBean;

/**
 *
 */

public interface FragmentHomeContract extends BaseComtract {

    interface View extends BaseView {
        void getHomePageSuccess(HomeReturnBean data);
        void getRuSuccess(BaseListObjectBean<HomeReturnBean.DataBean.RecentUpdateBean> data);

        void fail(String message);
        void noConnectInternet();
    }


    interface Presenter extends BasePresenter<View> {
        void getHomePage();
        void getRecentUpdatedNovel(String pageNum, String pageSize);

    }

}
