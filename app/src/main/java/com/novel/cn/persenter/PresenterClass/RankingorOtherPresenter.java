package com.novel.cn.persenter.PresenterClass;


import com.novel.cn.model.api.ApiClient;
import com.novel.cn.model.api.BaseSubscriber;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BookShelfAllBean;
import com.novel.cn.model.entity.BookShowBean;
import com.novel.cn.model.entity.PersonDataBean;
import com.novel.cn.model.entity.RankingBean;
import com.novel.cn.model.util.HttpUtils;
import com.novel.cn.persenter.Contract.FragmentMyContract;
import com.novel.cn.persenter.Contract.RankingorOtherContract;
import com.novel.cn.util.JsonUtils;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.ToastUtils;

import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**排行书库
 * Created by jackieli on 2018/12/25.
 */

public class RankingorOtherPresenter implements RankingorOtherContract.Presenter {

    private RankingorOtherContract.View view;

    @Override
    public Object loadCache() {
        return null;
    }

    @Override
    public void setMvpView(RankingorOtherContract.View view, String cacheKey) {
        this.view=view;
    }


    @Override
    public void getList() {
        //        HttpUtils.httpInterceptor.isAddHead(false);
//        JsonUtils jsonUtils = new JsonUtils();
//        jsonUtils.addField("nickName", nickName);
//        String bodyString = jsonUtils.build().toString();
//        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),bodyString);
        ApiClient.service.getRankingList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<RankingBean>() {

                    @Override
                    protected void noConnectInternet() {
                        view.noConnectInternet();
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("tag","getRankingList错误="+e.getMessage());
                        view.fail(e.getMessage());
                    }
                    @Override
                    public void onNext(RankingBean bean) {
                        LogUtil.e("tag","getRankingList数据="+bean);
                        view.getListSuccess(bean);
                    }
                });
    }

    @Override
    public void getAllTypesMo() {
        ApiClient.service.getAllTypesMo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<BookShelfAllBean>() {

                    @Override
                    protected void noConnectInternet() {
                        view.noConnectInternet();
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("tag","getAllTypesMo错误="+e.getMessage());
                        view.fail(e.getMessage());
                    }
                    @Override
                    public void onNext(BookShelfAllBean bean) {
                        LogUtil.e("tag","getAllTypesMo数据="+bean);
                        view.getAllTypesMoSuccess(bean);
                    }
                });
    }

    @Override
    public void getNovelList(String boardsEnumCode, String listType, String pageNum, final boolean isLoadMore) {

        JsonUtils jsonUtils = new JsonUtils();
        jsonUtils.addField("boardsEnumCode", boardsEnumCode);
        jsonUtils.addField("listType", listType);
        jsonUtils.addField("pageNum", pageNum);
        jsonUtils.addField("pageSize", "10");

        String bodyString = jsonUtils.build().toString();
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),bodyString);
        ApiClient.service.getNovelList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<BookShowBean>() {

                    @Override
                    protected void noConnectInternet() {
                        view.noConnectInternet();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("tag","getNovelList错误="+e.getMessage());
                        view.fail(e.getMessage());
                    }

                    @Override
                    public void onNext(BookShowBean bean) {
                        LogUtil.e("tag","getNovelList数据="+bean);
                        view.getNovelListSuccess(bean,isLoadMore);
                    }
                });

    }

    @Override
    public void addBookShelf(String novel_id) {
        JsonUtils jsonUtils = new JsonUtils();
        jsonUtils.addField("novel_id", novel_id);
        jsonUtils.addField("volumeId", "");
        jsonUtils.addField("chapterId", "");
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
                        view.addBookShelfSuccess(bean);
                    }
                });

    }


}
