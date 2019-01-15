package com.novel.cn.persenter.PresenterClass;


import com.novel.cn.model.api.ApiClient;
import com.novel.cn.model.api.BaseSubscriber;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.PersonDataBean;
import com.novel.cn.model.entity.RankingBean;
import com.novel.cn.persenter.Contract.FragmentMyContract;
import com.novel.cn.persenter.Contract.RankingorOtherContract;
import com.novel.cn.util.LogUtil;

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
    public void getNovelList(String boardsEnumCode, String listType, String pageNum,boolean isLoadMore) {


    }


}
