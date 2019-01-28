package com.novel.cn.persenter.Contract;


import com.novel.cn.base.BaseComtract;
import com.novel.cn.base.BasePresenter;
import com.novel.cn.base.BaseView;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.ReadResponBean;


/** 阅读界面
 * Created by jackieli on 2018/forgetpstwo/1.
 */

public interface ReadContract extends BaseComtract {

    interface View extends BaseView {
        void readNovelSuccess(ReadResponBean data);
        void saveCollectionSuccess(BaseBean data);
        void cancelCollectionSuccess(BaseBean data);
        void fail(String message);
        void noConnectInternet();
    }


    interface Presenter extends BasePresenter<View> {

        void readNovel(String id);

        void saveCollection(String novel_id);
        void cancelCollection(String novel_id);
    }

}
