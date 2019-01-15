package com.novel.cn.persenter.Contract;


import com.novel.cn.base.BaseComtract;
import com.novel.cn.base.BasePresenter;
import com.novel.cn.base.BaseView;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BookShelfBean;
import com.novel.cn.model.entity.BookShowBean;
import com.novel.cn.model.entity.RankingBean;

/**
 *
 */

public interface RankingorOtherContract extends BaseComtract {


    interface View extends BaseView {
        void getListSuccess(RankingBean bean);
        void getNovelListSuccess(BookShowBean baseBean, boolean isLoadMore);
        void fail(String message);
        void noConnectInternet();
    }


    interface Presenter extends BasePresenter<View> {
        void getList();
        //{boardsEnumCode: "ALL", listType: 1, pageNum: 1, pageSize: 10, fromMobile: 1}
        void getNovelList(String boardsEnumCode, String listType,String pageNum,boolean isLoadMore);

    }

}
