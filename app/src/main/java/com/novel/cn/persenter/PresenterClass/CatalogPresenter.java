package com.novel.cn.persenter.PresenterClass;


import com.novel.cn.model.api.ApiClient;
import com.novel.cn.model.api.BaseSubscriber;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.BookDetailBean;
import com.novel.cn.model.entity.ChapterBean;
import com.novel.cn.model.entity.VolumesBean;
import com.novel.cn.persenter.Contract.BookDeatilContract;
import com.novel.cn.persenter.Contract.CataloglContract;
import com.novel.cn.util.JsonUtils;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.ToastUtils;

import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**目录界面
 * Created by jackieli on 2018/12/25.
 */

public class CatalogPresenter implements CataloglContract.Presenter {

    private CataloglContract.View view;

    @Override
    public Object loadCache() {
        return null;
    }

    @Override
    public void setMvpView(CataloglContract.View view, String cacheKey) {
        this.view=view;
    }

    @Override
    public void getVolumes(String novelId) {
        ApiClient.service.getVolumes(novelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<VolumesBean>() {

                    @Override
                    protected void noConnectInternet() {
                        ToastUtils.showShortToast("网络错误，请检查网络");
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("tag","getVolumes错误="+e.getMessage());
                    }

                    @Override
                    public void onNext(VolumesBean bean) {
                        view.getVolumesSuccess(bean);
                    }
                });
    }

    @Override
    public void getChapters(String novelId, final int pageNum, String pageSize, String sort, String volume, final boolean isLoadMore) {
        JsonUtils jsonUtils = new JsonUtils();
        jsonUtils.addField("novelId", novelId);
        jsonUtils.addField("pageNum", pageNum+"");
        jsonUtils.addField("pageSize", pageSize);
        jsonUtils.addField("sort", sort);
        jsonUtils.addField("volume", volume);
        String bodyString = jsonUtils.build().toString();

        LogUtil.e("tag","getChapters传参数="+bodyString);
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),bodyString);
        ApiClient.service.getChapters(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new BaseSubscriber<ChapterBean>() {

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
                    public void onNext(ChapterBean bean) {
                        view.getChaptersSuccess(bean,pageNum,isLoadMore);
                    }
                });
    }



}
