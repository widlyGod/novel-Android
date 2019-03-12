package com.novel.cn.persenter.Contract;

import com.novel.cn.base.BaseComtract;
import com.novel.cn.base.BasePresenter;
import com.novel.cn.base.BaseView;
import com.novel.cn.model.entity.BookShelfBean;

/**
 * Created by jackieli on 2019/3/12.
 */

public interface FragmentBookShelfContract extends BaseComtract {



    interface View extends BaseView {

        void getAllCollectionSuccess(BookShelfBean bean,boolean isLoadMore);
        void fail(String message);
        void noConnectInternet();
    }

    interface Presenter extends BasePresenter<FragmentBookShelfContract.View> {

        void getAllCollection(String pageNum,String pageSize,boolean isLoadMore);

    }


}
