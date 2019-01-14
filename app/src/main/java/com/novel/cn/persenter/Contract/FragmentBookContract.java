package com.novel.cn.persenter.Contract;


import com.novel.cn.base.BaseComtract;
import com.novel.cn.base.BasePresenter;
import com.novel.cn.base.BaseView;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BookShelfBean;
import com.novel.cn.model.entity.PersonDataBean;

/**
 *
 */

public interface FragmentBookContract extends BaseComtract {


    interface View extends BaseView {
        void getBookDataSuccess(BookShelfBean bean,boolean isLoadMore);
        void cancelOperSuccess(BaseBean baseBean);
        void fail(String message);
        void noConnectInternet();
    }


    interface Presenter extends BasePresenter<View> {
        void getBookData(int type,boolean isLoadMore,String pageNum,String pageSize);
        void cancelOper(int type);

    }

}
