package com.novel.cn.persenter.PresenterClass;


import com.novel.cn.model.api.ApiClient;
import com.novel.cn.model.api.BaseSubscriber;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BookDetailBean;
import com.novel.cn.model.entity.BookShelfBean;
import com.novel.cn.model.entity.ChargeChapterBean;
import com.novel.cn.persenter.Contract.BookDeatilContract;
import com.novel.cn.persenter.Contract.FragmentBookContract;
import com.novel.cn.util.JsonUtils;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**书籍详情
 * Created by jackieli on 2018/12/25.
 */

public class BookDetailPresenter implements BookDeatilContract.Presenter {

    private BookDeatilContract.View view;

    @Override
    public Object loadCache() {
        return null;
    }

    @Override
    public void setMvpView(BookDeatilContract.View view, String cacheKey) {
        this.view=view;
    }


    @Override
    public void getOpenNovel(String novelId) {

        ApiClient.service.getOpenNovel(novelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<BookDetailBean>() {
                    @Override
                    protected void noConnectInternet() {
                        view.noConnectInternet();
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("tag","getOpenNovel错误="+e.getMessage());
                        view.fail(e.getMessage());
                    }

                    @Override
                    public void onNext(BookDetailBean bean) {
//                        LogUtil.e("tag","getOpenNovel数据="+bean);
                        view.getOpenSuccess(bean);
                    }
                });
    }

    @Override
    public void saveCollection(String novel_id, String volumeId, String chapterId) {
        JsonUtils jsonUtils = new JsonUtils();
        jsonUtils.addField("novel_id", novel_id);
        jsonUtils.addField("volumeId", volumeId);
        jsonUtils.addField("chapterId", chapterId);
        String bodyString = jsonUtils.build().toString();
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),bodyString);
        ApiClient.service.saveCollection(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<BaseBean>() {

                    @Override
                    protected void noConnectInternet() {
                        ToastUtils.showShortToast("网络错误，请检查网络");
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("tag","saveCollection错误="+e.getMessage());
                    }

                    @Override
                    public void onNext(BaseBean bean) {
                        view.saveCollectionSuccess(bean);
                    }
                });
    }

    //查询账户余额
    @Override
    public void queryPersonAccount() {

    }

    //打赏
    @Override
    public void giveOperation(String novelId, String number, int type) {

    }




}
