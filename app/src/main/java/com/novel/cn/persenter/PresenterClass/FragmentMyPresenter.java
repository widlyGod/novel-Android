package com.novel.cn.persenter.PresenterClass;


import com.novel.cn.model.api.ApiClient;
import com.novel.cn.model.api.BaseSubscriber;
import com.novel.cn.model.entity.PersonDataBean;
import com.novel.cn.persenter.Contract.FragmentMyContract;
import com.novel.cn.util.JsonUtils;
import com.novel.cn.util.LogUtil;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**登录注册presen
 * Created by jackieli on 2018/12/25.
 */

public class FragmentMyPresenter implements FragmentMyContract.Presenter {

    private FragmentMyContract.View view;

    @Override
    public Object loadCache() {
        return null;
    }

    @Override
    public void setMvpView(FragmentMyContract.View view, String cacheKey) {
        this.view=view;
    }


    @Override
    public void getPersonData() {
//        HttpUtils.httpInterceptor.isAddHead(false);
//        JsonUtils jsonUtils = new JsonUtils();
//        jsonUtils.addField("nickName", nickName);
//        String bodyString = jsonUtils.build().toString();
//        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),bodyString);
        ApiClient.service.personCenter()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<PersonDataBean>() {

                    @Override
                    protected void noConnectInternet() {
                        view.noConnectInternet();
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("tag","register错误="+e.getMessage());
                        view.fail(e.getMessage());
                    }

                    @Override
                    public void onNext(PersonDataBean bean) {
                        LogUtil.e("tag","register数据="+bean);
                        view.getPersonDataSuccess(bean);
                    }
                });
    }



}
