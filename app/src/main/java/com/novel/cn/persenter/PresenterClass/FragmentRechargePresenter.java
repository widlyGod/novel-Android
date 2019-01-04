package com.novel.cn.persenter.PresenterClass;

import com.novel.cn.model.api.ApiClient;
import com.novel.cn.model.api.BaseSubscriber;
import com.novel.cn.model.entity.AplipayOrderBean;
import com.novel.cn.model.entity.BaseBean;
import com.novel.cn.model.entity.HomeReturnBean;
import com.novel.cn.model.entity.WxOrderBean;
import com.novel.cn.model.util.HttpUtils;
import com.novel.cn.persenter.Contract.FragmentHomeContract;
import com.novel.cn.persenter.Contract.FragmentRechargeContract;
import com.novel.cn.util.JsonUtils;
import com.novel.cn.util.LogUtil;

import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by jackieli on 2019/1/2.
 */

public class FragmentRechargePresenter implements FragmentRechargeContract.Presenter{

    private FragmentRechargeContract.View view;

    @Override
    public Object loadCache() {
        return null;
    }

    @Override
    public void setMvpView(FragmentRechargeContract.View view, String cacheKey) {
        this.view=view;
    }

    @Override
    public void getPayInfo(final String rechargeCode, String requestCode, double orderAmount) {
        JsonUtils jsonUtils = new JsonUtils();
        jsonUtils.addField("rechargeCode", rechargeCode);
        jsonUtils.addField("requestCode", requestCode);
        jsonUtils.addField("orderAmount", orderAmount);
        String bodyString = jsonUtils.build().toString();
//        JsonUtils jsonUtils = new JsonUtils();
//        jsonUtils.addField("out_trade_no", "2019010305");
//        jsonUtils.addField("total_amount", 1);
//        jsonUtils.addField("body", "测试app支付");

        LogUtil.e("getPayInfoBodyString="+bodyString);
        RequestBody body= RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),bodyString);
        ApiClient.service.upayCenterRecharge(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())         //销毁时销毁Retrofit
                .subscribe(new BaseSubscriber<String>() {

                    @Override
                    protected void noConnectInternet() {
                        view.noConnectInternet();
                    }

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("tag","getPayInfo错误="+e.getMessage());
                        view.fail(e.getMessage());
                    }

                    @Override
                    public void onNext(String baseBean) {
                        LogUtil.e("tag","getPayInfo数据="+baseBean);
                        if(rechargeCode.equals("0")){//该请求为微信支付
                            WxOrderBean wxOrderBean=WxOrderBean.objectFromData(baseBean);
                            view.getWxDataSuccess(wxOrderBean);
                        }else{//该请求为支付宝支付
                            AplipayOrderBean aplipayOrderBean=AplipayOrderBean.objectFromData(baseBean);
//                            BaseBean aplipayOrderBean=BaseBean.objectFromData(baseBean);
                            view.getAplipayDataSuccess(aplipayOrderBean);
                        }
                    }
                });

    }


}
