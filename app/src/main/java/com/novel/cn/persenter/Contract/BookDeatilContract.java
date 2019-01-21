package com.novel.cn.persenter.Contract;


import com.novel.cn.base.BaseComtract;
import com.novel.cn.base.BasePresenter;
import com.novel.cn.base.BaseView;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BookDetailBean;
import com.novel.cn.model.entity.BookShelfAllBean;
import com.novel.cn.model.entity.BookShowBean;
import com.novel.cn.model.entity.RankingBean;

/**
 *
 */

public interface BookDeatilContract extends BaseComtract {


    interface View extends BaseView {
        void getOpenSuccess(BookDetailBean bean);
        void saveCollectionSuccess(BaseBean bean);
        void queryAccountSuccess(BaseBean bean);
        void giveOperationSuccess(String message);

        void fail(String message);
        void noConnectInternet();
    }


    interface Presenter extends BasePresenter<View> {
        void getOpenNovel(String novelId);
        void saveCollection(String novel_id,String volumeId,String chapterId);


        void queryPersonAccount();
        void giveOperation(String novelId,String number,int type);
        //{boardsEnumCode: "ALL", listType: 1, pageNum: 1, pageSize: 10, fromMobile: 1}

    }

}
