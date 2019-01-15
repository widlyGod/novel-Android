package com.novel.cn.persenter.PresenterClass;


import com.novel.cn.model.api.ApiClient;
import com.novel.cn.model.api.BaseSubscriber;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BookShelfBean;
import com.novel.cn.model.entity.PersonDataBean;
import com.novel.cn.persenter.Contract.FragmentBookContract;
import com.novel.cn.persenter.Contract.FragmentMyContract;
import com.novel.cn.util.JsonUtils;
import com.novel.cn.util.LogUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**我的书架
 * Created by jackieli on 2018/12/25.
 */

public class FragmentBookPresenter implements FragmentBookContract.Presenter {

    private FragmentBookContract.View view;

    @Override
    public Object loadCache() {
        return null;
    }

    @Override
    public void setMvpView(FragmentBookContract.View view, String cacheKey) {
        this.view=view;
    }


    @Override
    public void getBookData(int type, final boolean isLoadMore,String pageNum,String pageSize) {

        String url="novelOAService/mobile/getAllCollection";

        switch (type){
            case 1:
                url="novelOAService/mobile/getAllCollection";
                break;
            case 2:
                url="novelOAService/mobile/getSubcribe";
                break;
            case 3:
                url="novelOAService/mobile/getNovleHistory";
                break;
        }

        JsonUtils jsonUtils = new JsonUtils();
        jsonUtils.addField("pageNum", pageNum);
        jsonUtils.addField("pageSize", pageSize);
        String bodyString = jsonUtils.build().toString();
        LogUtil.e("tag","getBookshelfData body="+bodyString);
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),bodyString);

        ApiClient.service.getBookshelfData(url,body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
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
                        LogUtil.e("tag","getBookshelfData错误="+e.getMessage());
                        view.fail(e.getMessage());
                    }

                    @Override
                    public void onNext(BookShelfBean bean) {
//                      {"basePage":null,"code":"1","data":{"total":0,"book":[]},"message":"查询成功","success":true}
                        LogUtil.e("tag","getBookshelfData数据="+bean);
                       view.getBookDataSuccess(bean,isLoadMore);

                    }

                });
    }

    @Override
    public void cancelOper(final int type, String novel_id) {

        String url="novelOAService/novelCollection/cancelCollection";

        switch (type){
            case 1: {
                url="novelOAService/novelCollection/cancelCollection";
            }break;
            case 2: {
                url="novelOAService/novelSubscribe/cancelSubscribe";
            }break;
            case 3: {
                url="novelOAService/novelCollection/emptyNovleHistory";
            }break;
        }

        Map<String, String> map=new HashMap<>();
        if(type==1){
            map.put("novel_id",novel_id);
        }else{
            map.put("novelId",novel_id);
        }

        LogUtil.e("url="+url+",type="+type);
        ApiClient.service.cancelOper1(url,map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<BaseBean>() {
                    @Override
                    protected void noConnectInternet() {
                        view.noConnectInternet();
                    }
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("tag","cancelOper错误="+e.getMessage()+",type="+type);
                        view.fail(e.getMessage());
                    }
                    @Override
                    public void onNext(BaseBean bean) {
//                      {"basePage":null,"code":"1","data":{"total":0,"book":[]},"message":"查询成功","success":true}
                        LogUtil.e("tag","cancelOper数据="+bean+",type="+type);
                        view.cancelOperSuccess(bean);

                    }
                });
    }


}
