package com.novel.cn.persenter.PresenterClass;

import com.google.gson.Gson;
import com.novel.cn.model.api.ApiClient;
import com.novel.cn.model.api.BaseSubscriber;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BaseObjectBean;
import com.novel.cn.model.entity.ChapterBean;
import com.novel.cn.model.entity.ChargeChapterBean;
import com.novel.cn.model.entity.ReadChapterBean;
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

    //读取章节内容
    //http://59.110.124.41:8000/novelapi/novelOAService/mobile/getChapter?chapterId=09790d7c3b1a497cb8f2712008e6b919
    //sessionId     session-c559a4a278f44ee2b23c3f0434f437b6
    @Override
    public void readNovel(String id,boolean isCharge) {
        if(isCharge){
            readChargeNovel(id);
        }else{
            ReadRequestBean bean=new ReadRequestBean();
            ReadRequestBean.ChapterInfoBean chapterBean=new ReadRequestBean.ChapterInfoBean();
            chapterBean.setChannelId(-1);
            chapterBean.setUniqueId("-1");
            chapterBean.setId(id);
            bean.setChapterInfo(chapterBean);
            String bodyString=new Gson().toJson(bean);

            //{chapterInfo: {id: "0f7cd473773e421d9c8d1489d7ed8f1b", uniqueId: "1545097950184", channelId: -1}}
            LogUtil.e("readNovel 免费bodyString="+bodyString);
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
//                        LogUtil.e("tag","readNovel数据="+baseBean);
                            view.readNovelSuccess(baseBean,true);
                        }
                    });
        }
    }


    //订阅本章节http://59.110.124.41:8000/novelapi/novelOAService/novelDetail/addConsumeRecord       {"basePage":null,"code":"1","data":null,"message":"该章节收费成功且榜单已更新！","success":true}
    //请求参数:{novelId: "e4e0be43640747f6afb9561a63b85b7e", novelVolumeId: "fe5ac7a3ff494e518ca2fc29bf5d74b1",...}
    //自动订阅下一章+订阅本章节 http://59.110.124.41:8000/novelapi/novelOAService/novelSubscribe/saveSubscribe    {"basePage":null,"code":"1","data":null,"message":"订阅成功","success":true}
    //请求参数:{novel_id: "e4e0be43640747f6afb9561a63b85b7e", chapterId: "e86a4c6a478e4f37987a5c88352a875a"}
    //自动订阅后切换每一章，调接口变成 isChargeChapter   ->  addConsumeRecord    ->  readNovel

    @Override
    public void readChargeNovel(String chapterId) {
        LogUtil.e("readNovel 收费bodyString="+chapterId);
        ApiClient.service.readChargeNovel(chapterId)
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
//                        LogUtil.e("tag","readNovel数据="+baseBean);
                        view.readNovelSuccess(baseBean,false);
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

    @Override
    public void getChapters(String novelId,  String selectType, int isReader) {
        JsonUtils jsonUtils = new JsonUtils();
        jsonUtils.addField("novelId", novelId);
        jsonUtils.addField("selectType", selectType);
        jsonUtils.addField("isReader", isReader);
        String bodyString = jsonUtils.build().toString();
        LogUtil.e("tag","getChapters传参数="+bodyString);
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),bodyString);
        ApiClient.service.getReadChapters(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<ReadChapterBean>() {
                    @Override
                    protected void noConnectInternet() {
                        ToastUtils.showShortToast("网络错误，请检查网络");
                    }
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("tag","getChapters错误="+e.getMessage());
                    }
                    @Override
                    public void onNext(ReadChapterBean bean) {
                        view.getChaptersSuccess(bean);
                    }
                });
    }

    //是否收费
    @Override
    public void isChargeChapter(String novelId, String novelVolumeId, String novelChapterId) {
        JsonUtils jsonUtils = new JsonUtils();
        jsonUtils.addField("novelId", novelId);
        jsonUtils.addField("novelVolumeId", novelVolumeId);
        jsonUtils.addField("novelChapterId", novelChapterId);
        String bodyString = jsonUtils.build().toString();
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),bodyString);
        ApiClient.service.isChargeChapter(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<ChargeChapterBean>() {
                    @Override
                    protected void noConnectInternet() {
                        ToastUtils.showShortToast("网络错误，请检查网络");
                    }
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("tag","isChargeChapter错误="+e.getMessage());
                    }
                    @Override
                    public void onNext(ChargeChapterBean bean) {
                        view.isChargeChapterSuccess(bean);
                    }
                });
    }


}
