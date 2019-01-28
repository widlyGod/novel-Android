package com.novel.cn.persenter.PresenterClass;

import com.google.gson.Gson;
import com.novel.cn.model.api.ApiClient;
import com.novel.cn.model.api.BaseSubscriber;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BaseObjectBean;
import com.novel.cn.model.entity.ReadRequestBean;
import com.novel.cn.model.entity.ReadResponBean;
import com.novel.cn.model.entity.UserBean;
import com.novel.cn.model.util.HttpUtils;
import com.novel.cn.persenter.Contract.LoginContract;
import com.novel.cn.persenter.Contract.ReadContract;
import com.novel.cn.util.JsonUtils;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**阅读presenter
 * Created by jackieli on 2018/12/25.
 */

public class ReadPresenter implements ReadContract.Presenter {

    private ReadContract.View view;

    @Override
    public Object loadCache() {
        return null;
    }

    @Override
    public void setMvpView(ReadContract.View view, String cacheKey) {
        this.view=view;
    }


    //sessionId     session-c559a4a278f44ee2b23c3f0434f437b6
    @Override
    public void readNovel(String id) {
//        JsonUtils jsonUtils = new JsonUtils();
//        jsonUtils.addField("id", id);
//        jsonUtils.addField("channelId", "-1");
//        String bodyString = jsonUtils.build().toString();

        ReadRequestBean bean=new ReadRequestBean();
        ReadRequestBean.ChapterInfoBean chapterBean=new ReadRequestBean.ChapterInfoBean();
        chapterBean.setChannelId(-1);
        chapterBean.setUniqueId("-1");
        chapterBean.setId(id);
        bean.setChapterInfo(chapterBean);
        String bodyString=new Gson().toJson(bean);

        //{chapterInfo: {id: "0f7cd473773e421d9c8d1489d7ed8f1b", uniqueId: "1545097950184", channelId: -1}}
        //{"chapterInfo":{"channelId":-1,"id":"784ea9dcd71148dd9e177715a0eac565"}}

        LogUtil.e("readNovel bodyString="+bodyString);
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),bodyString);
        ApiClient.service.readNovel(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())         //销毁时销毁Retrofit
                .subscribe(new BaseSubscriber<ReadResponBean>() {

                    @Override
                    protected void noConnectInternet() {
                        view.noConnectInternet();
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("tag","readNovel错误="+e.getMessage());
                        view.fail(e.getMessage());
                    }

                    @Override
                    public void onNext(ReadResponBean baseBean) {
                        LogUtil.e("tag","readNovel数据="+baseBean);
                        view.readNovelSuccess(baseBean);
                    }

                });
    }

    @Override
    public void saveCollection(String novel_id) {
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
                        view.saveCollectionSuccess(bean);
                    }
                });
    }

    @Override
    public void cancelCollection(String novel_id) {
        String url="novelOAService/novelCollection/cancelCollection";
        Map<String, String> map=new HashMap<>();
        map.put("novel_id",novel_id);

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
                        LogUtil.e("tag","cancelOper错误="+e.getMessage());
                        view.fail(e.getMessage());
                    }
                    @Override
                    public void onNext(BaseBean bean) {
//                      {"basePage":null,"code":"1","data":{"total":0,"book":[]},"message":"查询成功","success":true}
                        LogUtil.e("tag","cancelOper数据="+bean);
                        view.cancelCollectionSuccess(bean);
                    }
                });
    }
}
