package com.novel.cn.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;

import com.alipay.sdk.app.PayTask;
import com.novel.cn.app.Constants;
import com.novel.cn.model.entity.PayReponse;
import com.novel.cn.model.entity.WxOrderBean;
import com.novel.cn.util.LogUtil;
import com.novel.cn.util.ToastUtils;
import com.novel.cn.wxapi.WxPayConfig;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

import java.util.Map;

/**
 * Created by jackieli on 2019/1/2.
 */

public class PaymentHelper {


    private static final int SDK_PAY_FLAG = 1;
    private Activity mthis;
    private Map<String, String> result;

    /**
     * 支付宝支付
     */
    public void  startAliPay(Activity activity, String orderInfo) {
        this.mthis = activity;
        if (activity == null || orderInfo == null) {
            return;
        }



        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         * 点击支付按钮出现的错误码，请查看：https://tech.open.alipay.com/support/knowledge/index.htm?categoryId=24120&scrollcheck=1#/?_k=d783mj
         * orderInfo的获取必须来自服务端；
         */
//        boolean rsa2 = (AlipayConfig.RSA2_PRIVATE.length() > 0);
//        boolean rsa2 =true;
//        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(AlipayConfig.APPID, rsa2, "1");
//        String orderParam = OrderInfoUtil2_0.buildOrderParam(params); //拼接订单信息

//        String privateKey = rsa2 ? AlipayConfig.RSA2_PRIVATE : AlipayConfig.RSA_PRIVATE;
//        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2); //然后并对订单信息使用私钥进行RSA加密
//        String orderInfox = orderParam + "&" + sign;

//        String orderInfox = "alipay_sdk=alipay-sdk-java-3.1.0&app_id=2016051700098925&biz_content=%7B%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%2207022254132511224123%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22App%E6%94%AF%E4%BB%98%E6%B5%8B%E8%AF%95Java%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&sign=dJk88EUZfava9yB7gPDwAoTrpRmMYl%2F%2FvJHYpzF5hXgRml4H4YLU79NQLsKYcLp2z5iyQIbgyT7J8HbX0r0DoNX0P2HBOiIfUdupXtc27HUKgWL8i%2FX%2FtqPsGqgBOYH8t2j6OQSKOynrl4%2BOQ5l7XWGQvtU7Ot7bO7q13%2Fd8qUfX0BDJfdY%2FdfAsZhlE3fRzhdRQ0hHGappVtwQotx%2BcgdUjIqCmoLXTaZ47jvx3ld9gE5IwqTqSxfYxqqlvbK3TDzhOD6c1W2Gu8j1ADHZCkRpzC7xPLbzM%2Bo0YpBkCt1V6QT94Q6%2Bo501DCzmiTlKMurw5KxeO4NZTo0uo6RfyTg%3D%3D&sign_type=RSA2&timestamp=2019-01-03+15%3A41%3A31&version=1.0";
        LogUtil.e("支付宝", "支付宝startAliPay=" + orderInfo);

        new AliPayThread(orderInfo).start();  //支付行为需要在独立的非ui线程中执行
    }



    //然后我去查了下APP后台，发现App支付功能那栏还没开通支付功能。
    // 这也就解释了为什么会报appid和mech_id不匹配的错误了。原来在微信中，每一个App都要独立申请支付功能
    /**
     * 微信支付
     */
    public void startWeChatPay(Context activity, WxOrderBean.DataBean.PayCodeBean payReponse) {
        if (activity == null || payReponse == null)
            return;
        if (!WxPayConfig.APP_ID.equals(payReponse.getAppid())){
            ToastUtils.showShortToast("签名不对应");
            return;
        }
        //true
        IWXAPI wxapi = WXAPIFactory.createWXAPI(activity, WxPayConfig.APP_ID, true);
//        final IWXAPI msgApi = WXAPIFactory.createWXAPI(activity, null);
        // 将该app注册到微信
        wxapi.registerApp(WxPayConfig.APP_ID);
        if (!wxapi.isWXAppInstalled()) {
            ToastUtils.showShortToast("你没有安装微信");
            return;
        }
        //我们把请求到的参数全部给微信
        PayReq req = new PayReq(); //调起微信APP的对象
        req.appId = WxPayConfig.APP_ID;
        //1.partnerId商户号	2.prepayId预支付交易会话ID	3.packageValue扩展字段	4.nonceStr随机字符串   5.timestamp时间戳  6.sign签名
        req.partnerId = payReponse.getMch_id();
        req.prepayId = payReponse.getPrepay_id();
        req.packageValue = "Sign=WXPay"; //Sign=WXPay
        req.nonceStr = payReponse.getNonce_str();
        req.sign = payReponse.getSign();
        req.timeStamp = payReponse.getTimeStamp();

        wxapi.sendReq(req); //发送调起微信的请求
    }






    /**
     * 支付宝支付异步任务
     */
    private class AliPayThread extends Thread {

        private String orderInfo;
        //orderStr示例如下，参数说明见"请求参数说明",orderStr的获取必须来源于服务端：
        //app_id=2015052600090779&biz_content=%7B%22timeout_express%22%3A%2230m%22%2C%22seller_id%22%3A%22%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22total_amount%22%3A%220.02%22%2C%22subject%22%3A%221%22%2C%22body%22%3A%22%E6%88%91%E6%98%AF%E6%B5%8B%E8%AF%95%E6%95%B0%E6%8D%AE%22%2C%22out_trade_no%22%3A%22314VYGIAGG7ZOYY%22%7D&charset=utf-8&method=alipay.trade.app.pay&sign_type=RSA2&timestamp=2016-08-15%2012%3A12%3A15&version=1.0&sign=MsbylYkCzlfYLy9PeRwUUIg9nZPeN9SfXPNavUCroGKR5Kqvx0nEnd3eRmKxJuthNUx4ERCXe552EV9PfwexqW%2B1wbKOdYtDIb4%2B7PL3Pc94RZL0zKaWcaY3tSL89%2FuAVUsQuFqEJdhIukuKygrXucvejOUgTCfoUdwTi7z%2BZzQ%3D
        private AliPayThread(String orderInfo) {
            this.orderInfo = orderInfo;
        }

        @Override
        public void run() {
//            PayTask对象主要为商户提供订单支付、查询功能，及获取当前开发包版本号。
//            获取PayTask支付对象调用支付（支付行为需要在独立的非ui线程中执行），代码示例：
            PayTask alipay = new PayTask(mthis);
            //参数1:app支付请求参数字符串，主要包含商户的订单信息，key=value形式，以&连接。
            //参数2:用户在商户app内部点击付款，是否需要一个loading做为在钱包唤起之前的过渡，这个值设置为true，将会在调用pay接口的时候直接唤起一个loading，
            // 参数2:直到唤起H5支付页面或者唤起外部的钱包付款页面loading才消失。（建议将该值设置为true，优化点击付款到支付唤起支付页面的过渡过程。）
            result = alipay.payV2(orderInfo, true);
            LogUtil.e("支付宝", "支付宝AliPayThreadResult=" + result.toString());
            Message msg = new Message();
            msg.what = SDK_PAY_FLAG;
            msg.obj = result;
            /**
             *  官方result返回结果参考：https://docs.open.alipay.com/204/105302
             *  我这里返回到result格式为：
             *      {
             resultStatus = 9000, result = {
             "alipay_trade_app_pay_response": {
             "code": "10000",
             "msg": "Success",
             "app_id": "2017112400138529",
             "auth_app_id": "2017112400138529",
             "charset": "utf-8",
             "timestamp": "2018-01-29 14:46:33",
             "total_amount": "0.01",
             "trade_no": "2018012921001004940219217398",
             "seller_id": "2088821472668202",
             "out_trade_no": "0129144616-2725"
             }
             */
//            Log.e("支付宝", "支付宝result=" + result);
            mHandler.sendMessage(msg);
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {//支付宝支付后回调，返回结果

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG:
                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    Map<String, String> mapPayResult = (Map<String, String>) msg.obj;
                    String resultStatus = mapPayResult.get("resultStatus");

                    LogUtil.e("支付宝", "支付宝mHandler=" + resultStatus);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表参考：https://docs.open.alipay.com/204/105301
                    if (TextUtils.equals(resultStatus, "9000")) {
                        EventBus.getDefault().post("支付成功");//支付成功后，发个通知
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            ToastUtils.showShortToast("支付结果确认中");
                        } else if (TextUtils.equals(resultStatus, "6001")) { //用户中途取消
                            ToastUtils.showShortToast("取消支付");
                        } else {
                            // 其他值就可以判断为支付失败
                            ToastUtils.showShortToast("支付失败");
                        }
                    }
                    break;
            }
        }

    };



}
