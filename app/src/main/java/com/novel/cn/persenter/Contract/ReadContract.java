package com.novel.cn.persenter.Contract;


import com.novel.cn.base.BaseComtract;
import com.novel.cn.base.BasePresenter;
import com.novel.cn.base.BaseView;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.ChapterBean;
import com.novel.cn.model.entity.ChargeChapterBean;
import com.novel.cn.model.entity.ReadChapterBean;
import com.novel.cn.model.entity.ReadResponBean;


/** 阅读界面
 * Created by jackieli on 2018/forgetpstwo/1.
 */

public interface ReadContract extends BaseComtract {

    interface View extends BaseView {
        void readNovelSuccess(ReadResponBean data,boolean isGetCharge);
        void saveCollectionSuccess(BaseBean data);
        void cancelCollectionSuccess(BaseBean data);

        void getChaptersSuccess(ReadChapterBean bean);
        void isChargeChapterSuccess(ChargeChapterBean bean);
        void fail(String message);
        void noConnectInternet();
    }


    interface Presenter extends BasePresenter<View> {
        //阅读小说不收费的
        void readNovel(String id,boolean isCharge);
        //阅读小说收费的
        void readChargeNovel(String chapterId);


        void saveCollection(String novel_id);
        void cancelCollection(String novel_id);
        void getChapters(String novelId,  String selectType, int isReader);
        void isChargeChapter(String novelId,String novelVolumeId,String novelChapterId);
    }

}
