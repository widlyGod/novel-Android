package com.novel.cn.persenter.Contract;


import com.novel.cn.base.BaseComtract;
import com.novel.cn.base.BasePresenter;
import com.novel.cn.base.BaseView;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BookDetailBean;
import com.novel.cn.model.entity.ChapterBean;
import com.novel.cn.model.entity.VolumesBean;

/**
 *
 */

public interface CataloglContract extends BaseComtract {


    interface View extends BaseView {
        void getVolumesSuccess(VolumesBean bean);
        void getChaptersSuccess(ChapterBean bean, int pageNum,boolean isLoadMore);
        void fail(String message);
        void noConnectInternet();
    }


    interface Presenter extends BasePresenter<View> {
        void getVolumes(String novelId);
        void getChapters(String novelId, int pageNum, String pageSize, String sort, String volume,boolean isLoadMore);
    }

}
