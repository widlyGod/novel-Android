package com.novel.cn.persenter.PresenterClass;

import com.novel.cn.model.api.ApiClient;
import com.novel.cn.model.api.BaseSubscriber;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BaseListObjectBean;
import com.novel.cn.model.entity.BaseObjectBean;
import com.novel.cn.model.entity.HomeReturnBean;
import com.novel.cn.model.entity.UserBean;
import com.novel.cn.model.util.HttpUtils;
import com.novel.cn.persenter.Contract.FragmentHomeContract;
import com.novel.cn.persenter.Contract.LoginContract;
import com.novel.cn.util.JsonUtils;
import com.novel.cn.util.LogUtil;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**登录注册presen
 * Created by jackieli on 2018/12/25.
 */

public class FragmentHomePresenter implements FragmentHomeContract.Presenter {

    private FragmentHomeContract.View view;

    @Override
    public Object loadCache() {
        return null;
    }

    @Override
    public void setMvpView(FragmentHomeContract.View view, String cacheKey) {
        this.view=view;
    }


    @Override
    public void getHomePage() {
        HttpUtils.httpInterceptor.isAddHead(false);


        ApiClient.service.getHomepage()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())         //销毁时销毁Retrofit
                .subscribe(new BaseSubscriber<HomeReturnBean>() {
                    @Override
                    protected void noConnectInternet() {
                        view.noConnectInternet();
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("tag","sendCode错误="+e.getMessage());
                        view.fail(e.getMessage());
                    }

                    @Override
                    public void onNext(HomeReturnBean baseBean) {
                        LogUtil.e("tag","sendCode数据="+baseBean);
                        view.getHomePageSuccess(baseBean);
                    }
                });


    }

    @Override
    public void getRecentUpdatedNovel(String pageNum, String pageSize) {
        HttpUtils.httpInterceptor.isAddHead(false);

        JsonUtils jsonUtils = new JsonUtils();
        jsonUtils.addField("pageNum", pageNum);
        jsonUtils.addField("pageSize", pageSize);
        String bodyString = jsonUtils.build().toString();
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),bodyString);
        ApiClient.service.getRecentUpdatedNovel(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<BaseListObjectBean<HomeReturnBean.DataBean.RecentUpdateBean>>() {

                    @Override
                    protected void noConnectInternet() {
                        view.noConnectInternet();
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("tag","getRecentUpdatedNovel错误="+e.getMessage());
                        view.fail(e.getMessage());
                    }

                    @Override
                    public void onNext(BaseListObjectBean<HomeReturnBean.DataBean.RecentUpdateBean> baseBean) {
                        LogUtil.e("tag","getRecentUpdatedNovel数据="+baseBean);
                        view.getRuSuccess(baseBean);
                    }
                });
    }
}
