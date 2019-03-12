package com.novel.cn.persenter.PresenterClass;

import com.novel.cn.model.api.ApiClient;
import com.novel.cn.model.api.BaseSubscriber;
import com.novel.cn.model.entity.AplipayOrderBean;
import com.novel.cn.model.entity.BookShelfBean;
import com.novel.cn.model.entity.WxOrderBean;
import com.novel.cn.persenter.Contract.FragmentBookShelfContract;
import com.novel.cn.persenter.Contract.FragmentRechargeContract;
import com.novel.cn.util.JsonUtils;
import com.novel.cn.util.LogUtil;

import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**书架
 * Created by jackieli on 2019/3/12.
 */

public class FragmentBookShelfPresenter implements FragmentBookShelfContract.Presenter {

    private FragmentBookShelfContract.View view;

    @Override
    public Object loadCache() {
        return null;
    }

    @Override
    public void setMvpView(FragmentBookShelfContract.View view, String cacheKey) {
        this.view=view;
    }

    @Override
    public void getAllCollection(String pageNum, String pageSize, final boolean isLoadMore) {

        JsonUtils jsonUtils = new JsonUtils();
        jsonUtils.addField("pageNum", pageNum);
        jsonUtils.addField("pageSize", pageSize);
        String bodyString = jsonUtils.build().toString();
        LogUtil.e("getAllCollection="+bodyString);
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),bodyString);

        ApiClient.service.getAllCollection(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())         //销毁时销毁Retrofit
                .subscribe(new BaseSubscriber<BookShelfBean>() {

                    @Override
                    protected void noConnectInternet() {
                        view.noConnectInternet();
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("tag","getAllCollection错误="+e.getMessage());
                        view.fail(e.getMessage());
                    }

                    @Override
                    public void onNext(BookShelfBean baseBean) {
                        LogUtil.e("tag","getAllCollection数据="+baseBean.toString());
                        view.getAllCollectionSuccess(baseBean,isLoadMore);
                    }
                });
    }


}
