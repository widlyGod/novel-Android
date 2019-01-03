package com.novel.cn.model.entity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jackieli on 2019/1/2.
 */

public class WxOrderBean {


    /**
     * basePage : null
     * code : 1
     * data : {"tradeNumber":"20190102173849000000292","payCode":{"nonce_str":"f10f571ad9e346ab80795aec00f528b8","out_trade_no":"20190102173849000000292","appid":"wx681bb7c8ec4c6754","total_fee":"100","sign":"380EB8AD8F8F01741F5E856D21D48D7B","trade_type":"APP","body":"用户[枫叶余香844257]充值小说网阅读币","mch_id":"1498301862","notify_url":"http://www.levis.kim:8083/weixinPayCenter/notifyPay","sign_type":"MD5","spbill_create_ip":"172.16.200.141"}}
     * message : 微信支付app下单成功
     * success : true
     */

    private Object basePage;
    private String code;
    private DataBean data;
    private String message;
    private boolean success;

    public static WxOrderBean objectFromData(String str) {

        return new Gson().fromJson(str, WxOrderBean.class);
    }

    public static List<WxOrderBean> arrayWxOrderBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<WxOrderBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public Object getBasePage() {
        return basePage;
    }

    public void setBasePage(Object basePage) {
        this.basePage = basePage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class DataBean {
        /**
         * tradeNumber : 20190102173849000000292
         * payCode : {"nonce_str":"f10f571ad9e346ab80795aec00f528b8","out_trade_no":"20190102173849000000292","appid":"wx681bb7c8ec4c6754","total_fee":"100","sign":"380EB8AD8F8F01741F5E856D21D48D7B","trade_type":"APP","body":"用户[枫叶余香844257]充值小说网阅读币","mch_id":"1498301862","notify_url":"http://www.levis.kim:8083/weixinPayCenter/notifyPay","sign_type":"MD5","spbill_create_ip":"172.16.200.141"}
         */

        private String tradeNumber;
        private PayCodeBean payCode;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public static List<DataBean> arrayDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public String getTradeNumber() {
            return tradeNumber;
        }

        public void setTradeNumber(String tradeNumber) {
            this.tradeNumber = tradeNumber;
        }

        public PayCodeBean getPayCode() {
            return payCode;
        }

        public void setPayCode(PayCodeBean payCode) {
            this.payCode = payCode;
        }

        public static class PayCodeBean {
            /**
             * nonce_str : f10f571ad9e346ab80795aec00f528b8
             * out_trade_no : 20190102173849000000292
             * appid : wx681bb7c8ec4c6754
             * total_fee : 100
             * sign : 380EB8AD8F8F01741F5E856D21D48D7B
             * trade_type : APP
             * body : 用户[枫叶余香844257]充值小说网阅读币
             * mch_id : 1498301862
             * notify_url : http://www.levis.kim:8083/weixinPayCenter/notifyPay
             * sign_type : MD5
             * spbill_create_ip : 172.16.200.141
             */

            private String nonce_str;
            private String out_trade_no;
            private String appid;
            private String total_fee;
            private String sign;
            private String trade_type;
            private String body;
            private String mch_id;
            private String notify_url;
            private String sign_type;
            private String spbill_create_ip;

            public static PayCodeBean objectFromData(String str) {

                return new Gson().fromJson(str, PayCodeBean.class);
            }

            public static List<PayCodeBean> arrayPayCodeBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<PayCodeBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public String getNonce_str() {
                return nonce_str;
            }

            public void setNonce_str(String nonce_str) {
                this.nonce_str = nonce_str;
            }

            public String getOut_trade_no() {
                return out_trade_no;
            }

            public void setOut_trade_no(String out_trade_no) {
                this.out_trade_no = out_trade_no;
            }

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getTotal_fee() {
                return total_fee;
            }

            public void setTotal_fee(String total_fee) {
                this.total_fee = total_fee;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getTrade_type() {
                return trade_type;
            }

            public void setTrade_type(String trade_type) {
                this.trade_type = trade_type;
            }

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
            }

            public String getMch_id() {
                return mch_id;
            }

            public void setMch_id(String mch_id) {
                this.mch_id = mch_id;
            }

            public String getNotify_url() {
                return notify_url;
            }

            public void setNotify_url(String notify_url) {
                this.notify_url = notify_url;
            }

            public String getSign_type() {
                return sign_type;
            }

            public void setSign_type(String sign_type) {
                this.sign_type = sign_type;
            }

            public String getSpbill_create_ip() {
                return spbill_create_ip;
            }

            public void setSpbill_create_ip(String spbill_create_ip) {
                this.spbill_create_ip = spbill_create_ip;
            }
        }
    }
}
